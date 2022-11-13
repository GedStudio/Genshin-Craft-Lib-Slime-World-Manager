package net.deechael.genshin.lib.open.world.exception;

public class WorldLoadedException extends SlimeException {

    public WorldLoadedException(String worldName) {
        super("World " + worldName + " is loaded! Unload it before importing it.");
    }
}
