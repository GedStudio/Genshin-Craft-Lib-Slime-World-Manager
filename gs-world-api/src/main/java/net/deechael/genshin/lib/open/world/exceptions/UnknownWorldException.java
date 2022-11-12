package net.deechael.genshin.lib.open.world.exceptions;

public class UnknownWorldException extends SlimeException {

    public UnknownWorldException(String world) {
        super("Unknown world " + world);
    }
}
