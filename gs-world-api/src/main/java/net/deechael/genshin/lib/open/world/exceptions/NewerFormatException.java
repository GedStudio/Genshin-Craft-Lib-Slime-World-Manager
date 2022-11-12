package net.deechael.genshin.lib.open.world.exceptions;

public class NewerFormatException extends SlimeException {

    public NewerFormatException(byte version) {
        super("v" + version);
    }
}
