package net.deechael.genshin.lib.impl.world.loader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;

import java.io.IOException;

public abstract class UpdatableLoader implements SlimeLoader {

    public abstract void update() throws NewerDatabaseException, IOException;

    @Getter
    @RequiredArgsConstructor
    public static class NewerDatabaseException extends Exception {

        private final int currentVersion;
        private final int databaseVersion;

    }
}
