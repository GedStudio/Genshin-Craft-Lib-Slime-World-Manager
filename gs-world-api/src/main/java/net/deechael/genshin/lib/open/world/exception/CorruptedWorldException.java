package net.deechael.genshin.lib.open.world.exception;

public class CorruptedWorldException extends SlimeException {

    public CorruptedWorldException(String world) {
        this(world, null);
    }

    public CorruptedWorldException(String world, Exception ex) {
        super("World " + world + " seems to be corrupted", ex);
    }
}
