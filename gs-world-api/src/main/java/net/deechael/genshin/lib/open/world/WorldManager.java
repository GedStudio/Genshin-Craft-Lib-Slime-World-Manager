package net.deechael.genshin.lib.open.world;

import net.deechael.genshin.lib.open.world.exception.WorldAlreadyExistsException;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class WorldManager {

    private static WorldManager manager;

    public static WorldManager getManager() {
        return manager;
    }

    public static void setManager(WorldManager manager) {
        if (WorldManager.manager != null)
            return;
        WorldManager.manager = manager;
    }

    public abstract SlimeWorld create(Plugin plugin, DataSource dataSource, String worldName) throws IOException, WorldAlreadyExistsException;

    public abstract SlimeWorld clone(Plugin plugin, SlimeWorld original, DataSource dataSource, String worldName) throws IOException, WorldAlreadyExistsException;

    public abstract void delete(Plugin plugin, SlimeWorld world);

    public abstract void delete(Plugin plugin, SlimeLoader loader, String worldName);

    public abstract List<SlimeWorld> list(Plugin plugin, DataSource dataSource) throws IOException;

    public abstract List<SlimeWorld> list(Plugin plugin);

    public abstract SlimeWorld get(Plugin plugin, String name);

    public abstract void moveTo(Plugin plugin, Player target, SlimeWorld destination);

    public abstract void moveTo(Plugin plugin, Player target, SlimeWorld destination, double x, double y, double z);

    public abstract CompletableFuture<SlimeWorld> importWorld(Plugin plugin, File worldDir, DataSource destination);

    public abstract void migrate(Plugin plugin, SlimeWorld oldWorld, DataSource dataSource);

    public abstract SlimeWorld createOrLoad(Plugin plugin, DataSource dataSource, String worldName);

    public abstract SlimeWorld load(Plugin plugin, DataSource dataSource, String worldName);

    public abstract void unload(Plugin plugin, SlimeWorld world);

    public abstract boolean exists(Plugin plugin, DataSource dataSource, String worldName);

}
