package net.deechael.genshin.lib.impl.world;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lombok.Getter;
import net.deechael.genshin.lib.impl.world.loader.*;
import net.deechael.genshin.lib.impl.world.nms.SlimeNMS;
import net.deechael.genshin.lib.impl.world.nms.v1192.v1192SlimeNMS;
import net.deechael.genshin.lib.impl.world.operator.WorldImporter;
import net.deechael.genshin.lib.impl.world.operator.WorldUnlocker;
import net.deechael.genshin.lib.impl.world.reader.SlimeWorldReaderRegistry;
import net.deechael.genshin.lib.open.world.DataSource;
import net.deechael.genshin.lib.open.world.SlimeLoadedWorld;
import net.deechael.genshin.lib.open.world.SlimeWorld;
import net.deechael.genshin.lib.open.world.WorldManager;
import net.deechael.genshin.lib.open.world.config.*;
import net.deechael.genshin.lib.open.world.exception.*;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;
import net.deechael.genshin.lib.open.world.property.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class SlimeManagerImpl implements net.deechael.genshin.lib.open.world.SlimeManager, Listener {

    private static Plugin plugin;

    @Getter
    private SlimeNMS nms;

    private final Map<String, SlimeWorld> loadedWorlds = new ConcurrentHashMap<>();

    private static boolean isPaperMC = false;

    private static boolean checkIsPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public void loading(Plugin plugin) {
        SlimeManagerImpl.plugin = plugin;
        isPaperMC = checkIsPaper();

        nms = getNMSBridge();
        WorldManager.setManager(WorldManagerImpl.INSTANCE);
    }

    public void enabling() {
        plugin.getServer().getPluginManager().registerEvents(new WorldUnlocker(), plugin);

        loadedWorlds.values().stream()
                .filter(slimeWorld -> Objects.isNull(slimeWorld.asBukkit()))
                .forEach(this::generateWorld);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        //loadedWorlds.clear // - Commented out because not sure why this would be cleared. Needs checking
    }

    public void disabling() {
        WorldManager.getManager().list(plugin).stream()
                .filter(Objects::nonNull)
                .filter((slimeWorld -> !slimeWorld.isReadOnly()))
                .map(w -> (SlimeLoadedWorld) w)
                .forEach(world -> {
                    try {
                        SlimeLoader loader = world.getLoader();
                        String name = world.getName();

                        loader.saveWorld(
                                name,
                                world.serialize().join(),
                                world.isLocked()
                        );

                        if (loader.isWorldLocked(name)) {
                            loader.unlockWorld(name);
                        }
                    } catch (IOException | UnknownWorldException e) {
                        e.printStackTrace();
                    }
                });
    }

    private SlimeNMS getNMSBridge() {
        return new v1192SlimeNMS(isPaperMC);
    }

    @Override
    public SlimeWorld loadWorld(SlimeLoader loader, String worldName, boolean readOnly) throws UnknownWorldException, IOException,
            CorruptedWorldException, NewerFormatException, OlderFormatException, WorldInUseException {
        Objects.requireNonNull(loader, "Loader cannot be null");
        Objects.requireNonNull(worldName, "World name cannot be null");

        long start = System.currentTimeMillis();

        plugin.getSLF4JLogger().info("Loading world " + worldName + ".");
        byte[] serializedWorld = loader.loadWorld(worldName, readOnly);
        SlimeLoadedWorld world;

        try {
            world = SlimeWorldReaderRegistry.readWorld(loader, worldName, serializedWorld, readOnly);

            if (world.getVersion() > nms.getWorldVersion()) {
                throw new NewerFormatException(world.getVersion());
            } else if (world.getVersion() < nms.getWorldVersion()) {
                throw new OlderFormatException(world.getVersion());
            }
        } catch (Exception ex) {
            if (!readOnly) { // Unlock the world as we're not using it
                loader.unlockWorld(worldName);
            }
            throw ex;
        }

        plugin.getSLF4JLogger().info("World " + worldName + " loaded in " + (System.currentTimeMillis() - start) + "ms.");

        registerWorld(world);
        this.loadedWorlds.put(worldName, world);
        return world;
    }

    @Override
    public SlimeWorld getWorld(String worldName) {
        return loadedWorlds.get(worldName);
    }

    public List<SlimeWorld> getLoadedWorlds() {
        return ImmutableList.copyOf(loadedWorlds.values());
    }

    @Override
    public SlimeWorld createEmptyWorld(SlimeLoader loader, String worldName, boolean readOnly, SlimePropertyMap propertyMap) throws WorldAlreadyExistsException, IOException {
        Objects.requireNonNull(loader, "Loader cannot be null");
        Objects.requireNonNull(worldName, "World name cannot be null");
        Objects.requireNonNull(propertyMap, "Properties cannot be null");

        if (loader.worldExists(worldName)) {
            throw new WorldAlreadyExistsException(worldName);
        }

        plugin.getSLF4JLogger().info("Creating empty world " + worldName + ".");
        long start = System.currentTimeMillis();
        SlimeLoadedWorld world = SlimeManagerImpl.getInstance().getNms().createSlimeWorld(loader, worldName, new Long2ObjectOpenHashMap<>(), new CompoundTag("", new CompoundMap()), new ArrayList<>(), nms.getWorldVersion(), propertyMap, readOnly, !readOnly, new Long2ObjectOpenHashMap<>());
        loader.saveWorld(worldName, world.serialize().join(), !readOnly);

        plugin.getSLF4JLogger().info("World " + worldName + " created in " + (System.currentTimeMillis() - start) + "ms.");

        registerWorld(world);
        this.loadedWorlds.put(worldName, world);
        return world;
    }

    /**
     * Utility method to register a <b>loaded</b> {@link SlimeWorld} with the internal map (for {@link #getWorld} calls)
     *
     * @param world the world to register
     */
    private void registerWorld(SlimeWorld world) {
        this.loadedWorlds.put(world.getName(), world);
    }


    /**
     * Ensure worlds are removed from the loadedWorlds map when {@link Bukkit#unloadWorld} is called.
     */
    @EventHandler
    public void onBukkitWorldUnload(WorldUnloadEvent worldUnloadEvent) {
        loadedWorlds.remove(worldUnloadEvent.getWorld().getName());
    }

    @Override
    public void generateWorld(SlimeWorld slimeWorld) {
        Objects.requireNonNull(slimeWorld, "SlimeWorld cannot be null");

        if (!slimeWorld.isReadOnly() && !slimeWorld.isLocked()) {
            throw new IllegalArgumentException("This world cannot be loaded, as it has not been locked.");
        }

        nms.generateWorld(slimeWorld);
    }

    @Override
    public void migrateWorld(String worldName, SlimeLoader currentLoader, SlimeLoader newLoader) throws IOException,
            WorldInUseException, WorldAlreadyExistsException, UnknownWorldException {
        Objects.requireNonNull(worldName, "World name cannot be null");
        Objects.requireNonNull(currentLoader, "Current loader cannot be null");
        Objects.requireNonNull(newLoader, "New loader cannot be null");

        if (newLoader.worldExists(worldName)) {
            throw new WorldAlreadyExistsException(worldName);
        }

        World bukkitWorld = Bukkit.getWorld(worldName);

        boolean leaveLock = false;

        if (bukkitWorld != null) {
            // Make sure the loaded world really is a SlimeWorld and not a normal Bukkit world
            SlimeLoadedWorld slimeWorld = (SlimeLoadedWorld) SlimeManagerImpl.getInstance().getNms().getSlimeWorld(bukkitWorld);

            if (slimeWorld != null && currentLoader.equals(slimeWorld.getLoader())) {
                slimeWorld.setLoader(newLoader);

                if (!slimeWorld.isReadOnly()) { // We have to manually unlock the world so no WorldInUseException is thrown
                    currentLoader.unlockWorld(worldName);
                    leaveLock = true;
                }
            }
        }

        byte[] serializedWorld = currentLoader.loadWorld(worldName, false);

        newLoader.saveWorld(worldName, serializedWorld, leaveLock);
        currentLoader.deleteWorld(worldName);
    }

    @Override
    public SlimeLoader getLoader(DataSource dataSource) {
        Objects.requireNonNull(dataSource, "Data source cannot be null");
        return LoaderUtils.getLoader(dataSource);
    }

    @Override
    public void registerLoader(DataSourceConfig config) throws SQLException {
        Objects.requireNonNull(config, "Config cannot be null");
        SlimeLoader slimeLoader = switch (config.getType()) {
            case FILE -> new FileLoader((FileConfig) config);
            case MYSQL -> new MysqlLoader((MysqlConfig) config);
            case MONGO -> new MongoLoader((MongoDBConfig) config);
            case REDIS -> new RedisLoader((RedisConfig) config);
            case SQLITE -> new SqliteLoader((SqliteConfig) config);
        };
        LoaderUtils.registerLoader(config.getType(), slimeLoader);
    }

    @Override
    public SlimeWorld importWorld(File worldDir, String worldName, SlimeLoader loader) throws WorldAlreadyExistsException,
            InvalidWorldException, WorldLoadedException, WorldTooBigException, IOException {
        Objects.requireNonNull(worldDir, "World directory cannot be null");
        Objects.requireNonNull(worldName, "World name cannot be null");
        Objects.requireNonNull(loader, "Loader cannot be null");

        if (loader.worldExists(worldName)) {
            throw new WorldAlreadyExistsException(worldName);
        }

        World bukkitWorld = Bukkit.getWorld(worldDir.getName());

        if (bukkitWorld != null && nms.getSlimeWorld(bukkitWorld) == null) {
            throw new WorldLoadedException(worldDir.getName());
        }

        SlimeLoadedWorld world = WorldImporter.readFromDirectory(worldDir);

        byte[] serializedWorld;

        try {
            serializedWorld = world.serialize().join();
        } catch (IndexOutOfBoundsException ex) {
            throw new WorldTooBigException(worldDir.getName());
        }

        loader.saveWorld(worldName, serializedWorld, false);
        return world;
    }


    @Override
    public CompletableFuture<Optional<SlimeWorld>> asyncLoadWorld(SlimeLoader slimeLoader, String worldName, boolean readOnly) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                var world = loadWorld(slimeLoader, worldName, readOnly);
                return Optional.ofNullable(world);
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                     WorldInUseException | OlderFormatException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Optional<SlimeWorld>> asyncGetWorld(String worldName) {
        return CompletableFuture.supplyAsync(() -> {
            var world = getWorld(worldName);
            return Optional.ofNullable(world);
        });
    }

    @Override
    public CompletableFuture<Optional<SlimeWorld>> asyncCreateEmptyWorld(SlimeLoader slimeLoader, String worldName, boolean readOnly, SlimePropertyMap slimePropertyMap) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                var world = createEmptyWorld(slimeLoader, worldName, readOnly, slimePropertyMap);
                return Optional.ofNullable(world);
            } catch (WorldAlreadyExistsException | IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> asyncMigrateWorld(String worldName, SlimeLoader currentLoader, SlimeLoader newLoader) {
        return CompletableFuture.runAsync(() -> {
            try {
                migrateWorld(worldName, currentLoader, newLoader);
            } catch (IOException | WorldInUseException | WorldAlreadyExistsException | UnknownWorldException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    @Override
    public CompletableFuture<SlimeWorld> asyncImportWorld(File worldDir, String worldName, SlimeLoader slimeLoader) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return importWorld(worldDir, worldName, slimeLoader);
            } catch (WorldAlreadyExistsException | InvalidWorldException | WorldLoadedException | WorldTooBigException |
                     IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public static boolean isPaperMC() {
        return isPaperMC;
    }

    public static SlimeManagerImpl getInstance() {
        return INSTANCE;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private final static SlimeManagerImpl INSTANCE = new SlimeManagerImpl();
}
