package net.deechael.genshin.lib.impl.world.loader;

import net.deechael.genshin.lib.impl.world.SlimeManagerImpl;
import net.deechael.genshin.lib.open.world.DataSource;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LoaderUtils {

    public static final long MAX_LOCK_TIME = 300000L; // Max time difference between current time millis and world lock
    public static final long LOCK_INTERVAL = 60000L;

    private static final Map<DataSource, SlimeLoader> loaderMap = new HashMap<>();

    public static List<DataSource> getAvailableLoaders() {
        return new LinkedList<>(loaderMap.keySet());
    }


    public static SlimeLoader getLoader(DataSource dataSource) {
        return loaderMap.get(dataSource);
    }

    public static void registerLoader(DataSource dataSource, SlimeLoader loader) {
        if (loaderMap.containsKey(dataSource)) {
            throw new IllegalArgumentException("Data source " + dataSource + " already has a declared loader!");
        }

        if (loader instanceof UpdatableLoader) {
            try {
                ((UpdatableLoader) loader).update();
            } catch (UpdatableLoader.NewerDatabaseException e) {
                SlimeManagerImpl.getPlugin().getSLF4JLogger().error("Data source " + dataSource + " version is " + e.getDatabaseVersion() + ", while" +
                        " this SWM version only supports up to version " + e.getCurrentVersion() + ".");
                return;
            } catch (IOException ex) {
                SlimeManagerImpl.getPlugin().getSLF4JLogger().error("Failed to check if data source " + dataSource + " is updated:");
                ex.printStackTrace();
                return;
            }
        }

        loaderMap.put(dataSource, loader);
    }

}
