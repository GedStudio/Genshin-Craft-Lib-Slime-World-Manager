package net.deechael.genshin.lib.impl.world.operator;

import net.deechael.genshin.lib.impl.world.SlimeManagerImpl;
import net.deechael.genshin.lib.open.world.SlimeWorld;
import net.deechael.genshin.lib.open.world.exceptions.UnknownWorldException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;

import java.io.IOException;

public class WorldUnlocker implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldUnload(WorldUnloadEvent event) {
        SlimeWorld world = SlimeManagerImpl.getInstance().getNms().getSlimeWorld(event.getWorld());

        if (world != null) {
            Bukkit.getScheduler().runTaskAsynchronously(SlimeManagerImpl.getPlugin(), () -> unlockWorld(world));
        }
    }

    private void unlockWorld(SlimeWorld world) {
        try {
            world.getLoader().unlockWorld(world.getName());
        } catch (IOException ex) {
            SlimeManagerImpl.getPlugin().getSLF4JLogger().error("Failed to unlock world " + world.getName() + ". Retrying in 5 seconds. Stack trace:");
            ex.printStackTrace();

            Bukkit.getScheduler().runTaskLaterAsynchronously(SlimeManagerImpl.getPlugin(), () -> unlockWorld(world), 100);
        } catch (UnknownWorldException ignored) {

        }
    }
}
