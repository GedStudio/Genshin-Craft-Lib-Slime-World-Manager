package net.deechael.genshin.lib.impl.world.loaders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.deechael.genshin.lib.open.world.loaders.SlimeLoader;

import java.io.IOException;

public abstract class UpdatableLoader implements SlimeLoader {

    public abstract void update() throws NewerDatabaseException, IOException;

    @Getter
    @RequiredArgsConstructor
    public class NewerDatabaseException extends Exception {

        private final int currentVersion;
        private final int databaseVersion;

    }
}
