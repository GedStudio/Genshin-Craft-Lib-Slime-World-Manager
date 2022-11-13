package net.deechael.genshin.lib.open.world.exception;

public class WorldInUseException extends SlimeException {

    public WorldInUseException(String world) {
        super(world);
    }
}
