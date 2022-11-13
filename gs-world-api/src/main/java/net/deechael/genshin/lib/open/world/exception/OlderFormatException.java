package net.deechael.genshin.lib.open.world.exception;

public class OlderFormatException extends SlimeException {

    public OlderFormatException(byte version) {
        super("v" + version);
    }
}