package net.deechael.genshin.lib.impl.world.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AsyncWorldSavedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String name;

    public AsyncWorldSavedEvent(String name) {
        super(true);
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
