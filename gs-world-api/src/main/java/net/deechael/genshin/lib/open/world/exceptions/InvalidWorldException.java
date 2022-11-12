package net.deechael.genshin.lib.open.world.exceptions;

import java.io.File;

public class InvalidWorldException extends SlimeException {

    public InvalidWorldException(File worldDir, String reason) {
        super("Directory " + worldDir.getPath() + " does not contain a valid MC world! " + reason);
    }

    public InvalidWorldException(File worldDir) {
        super("Directory " + worldDir.getPath() + " does not contain a valid MC world!");
    }
}
