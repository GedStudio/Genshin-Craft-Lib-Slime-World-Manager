package net.deechael.genshin.lib.open.world.exceptions;

public class WorldInUseException extends SlimeException {

    public WorldInUseException(String world) {
        super(world);
    }
}
