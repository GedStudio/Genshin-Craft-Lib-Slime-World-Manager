package net.deechael.genshin.lib.open.world.exceptions;

public class SlimeException extends Exception {

    public SlimeException(String message) {
        super(message);
    }

    public SlimeException(String message, Exception ex) {
        super(message, ex);
    }
}
