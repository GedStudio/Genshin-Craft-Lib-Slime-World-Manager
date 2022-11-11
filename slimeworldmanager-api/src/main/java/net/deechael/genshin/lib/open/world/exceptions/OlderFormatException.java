package net.deechael.genshin.lib.open.world.exceptions;

public class OlderFormatException extends SlimeException {

    public OlderFormatException(byte version) {
        super("v" + version);
    }
}