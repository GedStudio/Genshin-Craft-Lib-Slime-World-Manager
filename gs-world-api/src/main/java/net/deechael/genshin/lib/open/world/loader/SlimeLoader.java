package net.deechael.genshin.lib.open.world.loader;

import net.deechael.genshin.lib.open.world.exception.UnknownWorldException;
import net.deechael.genshin.lib.open.world.exception.WorldInUseException;

import java.io.IOException;
import java.util.List;

public interface SlimeLoader {

    byte[] loadWorld(String worldName, boolean readOnly) throws UnknownWorldException, WorldInUseException, IOException;

    boolean worldExists(String worldName) throws IOException;

    List<String> listWorlds() throws IOException;

    void saveWorld(String worldName, byte[] serializedWorld, boolean lock) throws IOException;

    void unlockWorld(String worldName) throws UnknownWorldException, IOException;

    boolean isWorldLocked(String worldName) throws UnknownWorldException, IOException;

    void deleteWorld(String worldName) throws UnknownWorldException, IOException;

}
