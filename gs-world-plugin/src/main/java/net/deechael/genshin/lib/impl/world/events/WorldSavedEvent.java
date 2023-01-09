package net.deechael.genshin.lib.impl.world.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WorldSavedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String name;

    public WorldSavedEvent(String name) {
        this.name = name;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getWorldName() {
        return name;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
