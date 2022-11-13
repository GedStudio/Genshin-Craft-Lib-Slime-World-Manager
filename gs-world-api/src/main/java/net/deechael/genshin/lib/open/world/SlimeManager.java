package net.deechael.genshin.lib.open.world;

import net.deechael.genshin.lib.open.world.config.DataSourceConfig;
import net.deechael.genshin.lib.open.world.exception.*;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;
import net.deechael.genshin.lib.open.world.property.SlimePropertyMap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface SlimeManager {

    SlimeWorld loadWorld(SlimeLoader loader, String worldName, boolean readOnly) throws
            UnknownWorldException, IOException, CorruptedWorldException, NewerFormatException, OlderFormatException, WorldInUseException;

    SlimeWorld getWorld(String worldName);

    List<SlimeWorld> getLoadedWorlds();

    SlimeWorld createEmptyWorld(SlimeLoader loader, String worldName, boolean readOnly, SlimePropertyMap propertyMap) throws WorldAlreadyExistsException, IOException;

    void generateWorld(SlimeWorld world);

    void migrateWorld(String worldName, SlimeLoader currentLoader, SlimeLoader newLoader) throws IOException, WorldInUseException, WorldAlreadyExistsException, UnknownWorldException;

    SlimeLoader getLoader(DataSource dataSource);

    void registerLoader(DataSourceConfig config) throws SQLException;

    SlimeWorld importWorld(File worldDir, String worldName, SlimeLoader loader) throws WorldAlreadyExistsException,
            InvalidWorldException, WorldLoadedException, WorldTooBigException, IOException;

    CompletableFuture<Optional<SlimeWorld>> asyncLoadWorld(SlimeLoader loader, String worldName, boolean readOnly);

    CompletableFuture<Optional<SlimeWorld>> asyncGetWorld(String worldName);

    CompletableFuture<Optional<SlimeWorld>> asyncCreateEmptyWorld(SlimeLoader loader, String worldName, boolean readOnly, SlimePropertyMap propertyMap);

    CompletableFuture<Void> asyncMigrateWorld(String worldName, SlimeLoader currentLoader, SlimeLoader newLoader);

    CompletableFuture<SlimeWorld> asyncImportWorld(File worldDir, String worldName, SlimeLoader loader);

    static SlimeManager getManager() {
        try {
            return (SlimeManager) Class
                    .forName("net.deechael.genshin.lib.impl.world.SlimeManagerImpl")
                    .getMethod("getInstance")
                    .invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
