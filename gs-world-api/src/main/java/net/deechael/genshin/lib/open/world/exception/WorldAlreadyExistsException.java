package net.deechael.genshin.lib.open.world.exception;

public class WorldAlreadyExistsException extends SlimeException {

    public WorldAlreadyExistsException(String world) {
        super("World " + world + " already exists!");
    }
}
