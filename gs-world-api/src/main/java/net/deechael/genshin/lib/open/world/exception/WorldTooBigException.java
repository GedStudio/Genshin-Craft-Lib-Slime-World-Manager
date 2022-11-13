package net.deechael.genshin.lib.open.world.exception;

public class WorldTooBigException extends SlimeException {

    public WorldTooBigException(String worldName) {
        super("World " + worldName + " is too big to be converted into the SRF!");
    }
}
