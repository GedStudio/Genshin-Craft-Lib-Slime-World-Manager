package net.deechael.genshin.lib.open.world.exceptions;

public class WorldAlreadyExistsException extends SlimeException {

    public WorldAlreadyExistsException(String world) {
        super("World " + world + " already exists!");
    }
}
