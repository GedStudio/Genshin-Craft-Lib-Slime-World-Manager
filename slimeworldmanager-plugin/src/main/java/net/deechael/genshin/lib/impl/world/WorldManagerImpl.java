package net.deechael.genshin.lib.impl.world;

import net.deechael.genshin.lib.open.world.DataSource;
import net.deechael.genshin.lib.open.world.SlimeManager;
import net.deechael.genshin.lib.open.world.SlimeWorld;
import net.deechael.genshin.lib.open.world.WorldManager;
import net.deechael.genshin.lib.open.world.config.WorldData;
import net.deechael.genshin.lib.open.world.exceptions.*;
import net.deechael.genshin.lib.open.world.loaders.SlimeLoader;
import net.deechael.genshin.lib.open.world.properties.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class WorldManagerImpl extends WorldManager {

    public SlimeWorld create(Plugin plugin, DataSource dataSource, String worldName) throws IOException, WorldAlreadyExistsException {
        SlimeLoader loader = SlimeManagerImpl.getInstance().getLoader(dataSource);

        WorldData worldData = new WorldData();
        worldData.setSpawn("0, 64, 0");
        worldData.setDataSource(dataSource.name().toLowerCase());

        SlimePropertyMap propertyMap = worldData.toPropertyMap();
        SlimeWorld slimeWorld = SlimeManagerImpl.getInstance().createEmptyWorld(loader, worldName, false, propertyMap);

        try {
            SlimeManagerImpl.getInstance().generateWorld(slimeWorld);

            // Bedrock block
            Location location = new Location(Bukkit.getWorld(worldName), 0, 61, 0);
            location.getBlock().setType(Material.BEDROCK);
        } catch (IllegalArgumentException ex) {
            plugin.getSLF4JLogger().error("Failed to create world", ex);
        }
        return slimeWorld;
    }

    @Override
    public SlimeWorld clone(Plugin plugin, SlimeWorld original, DataSource dataSource, String worldName) throws IOException, WorldAlreadyExistsException {
        SlimeLoader loader = SlimeManagerImpl.getInstance().getLoader(dataSource);

        SlimeWorld slimeWorld = original.clone(worldName, loader);
        try {
            SlimeManagerImpl.getInstance().generateWorld(slimeWorld);

        } catch (IllegalArgumentException ex) {
            plugin.getSLF4JLogger().error("Failed to clone world", ex);
        }
        return slimeWorld;
    }

    @Override
    public void delete(Plugin plugin, SlimeWorld world) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                if (world.isLocked()) {
                    plugin.getSLF4JLogger().error("World " + world.getName() + " is being used on another server.");
                    return;
                }
                world.getLoader().deleteWorld(world.getName());
            } catch (IOException ex) {
                SlimeManagerImpl.getPlugin().getSLF4JLogger().error("Failed to delete world " + world.getName() + ". Stack trace:", ex);
            } catch (UnknownWorldException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public List<SlimeWorld> list(Plugin plugin, DataSource dataSource) throws IOException {
        SlimeLoader loader = SlimeManagerImpl.getInstance().getLoader(dataSource);
        return loader.listWorlds().stream().map(name -> get(plugin, name)).toList();
    }

    @Override
    public List<SlimeWorld> list(Plugin plugin) {
        List<SlimeWorld> slimeWorlds = new ArrayList<>();
        for (DataSource dataSource : DataSource.values()) {
            SlimeLoader loader = SlimeManagerImpl.getInstance().getLoader(dataSource);
            if (loader == null)
                continue;
            try {
                slimeWorlds.addAll(list(plugin, dataSource));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return slimeWorlds;
    }

    @Override
    public SlimeWorld get(Plugin plugin, String name) {
        return SlimeManagerImpl.getInstance().getWorld(name);
    }

    @Override
    public void moveTo(Plugin plugin, Player target, SlimeWorld destination) {
        target.teleport(destination.asBukkit().getSpawnLocation());
    }

    @Override
    public void moveTo(Plugin plugin, Player target, SlimeWorld destination, double x, double y, double z) {
        target.teleport(new Location(destination.asBukkit(), x, y, z));
    }

    @Override
    public CompletableFuture<SlimeWorld> importWorld(Plugin plugin, File worldDir, DataSource destination) {
        CompletableFuture<SlimeWorld> completableFuture = new CompletableFuture<>();
        String worldName = worldDir.getName();
        if (worldName.contains("/")) {
            String[] split = worldName.split("/");
            worldName = split[split.length - 1];
        }
        SlimeLoader loader = SlimeManagerImpl.getInstance().getLoader(destination);
        String finalWorldName = worldName;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                SlimeWorld imported = SlimeManagerImpl.getInstance().importWorld(worldDir, finalWorldName, loader);

                completableFuture.complete(imported);
            } catch (WorldAlreadyExistsException | InvalidWorldException | WorldLoadedException | WorldTooBigException |
                     IOException ex) {
                SlimeManagerImpl.getPlugin().getSLF4JLogger().error("Failed to import world " + finalWorldName + ". Stack trace:", ex);
            }
        });
        return completableFuture;
    }

    @Override
    public void migrate(Plugin plugin, SlimeWorld oldWorld, DataSource dataSource) {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) SlimeManagerImpl.getInstance(), () -> {
            SlimeLoader loader = SlimeManagerImpl.getInstance().getLoader(dataSource);
            try {
                SlimeManagerImpl.getInstance().migrateWorld(oldWorld.getName(), oldWorld.getLoader(), loader);
            } catch (IOException | WorldAlreadyExistsException | WorldInUseException | UnknownWorldException ex) {
                SlimeManagerImpl.getPlugin().getSLF4JLogger().error("Failed to load world " + oldWorld.getName() + " (using data source " + dataSource + "):", ex);
            }

        });
    }

    @Override
    public SlimeWorld createOrLoad(Plugin plugin, DataSource dataSource, String worldName) {
        if (exists(plugin, dataSource, worldName)) {
            return load(plugin, dataSource, worldName);
        } else {
            try {
                return create(plugin, dataSource, worldName);
            } catch (IOException | WorldAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public SlimeWorld load(Plugin plugin, DataSource dataSource, String worldName) {
        SlimeLoader loader = SlimeManagerImpl.getInstance().getLoader(dataSource);
        try {
            return SlimeManager.getManager().loadWorld(loader, "exp", false);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 OlderFormatException | WorldInUseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unload(Plugin plugin, SlimeWorld world) {
        Bukkit.unloadWorld(world.asBukkit(), true);
    }

    @Override
    public boolean exists(Plugin plugin, DataSource dataSource, String worldName) {
        SlimeLoader loader = SlimeManagerImpl.getInstance().getLoader(dataSource);
        try {
            return loader.worldExists(worldName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private WorldManagerImpl() {
    }

    public final static WorldManagerImpl INSTANCE = new WorldManagerImpl();

}
