package net.deechael.genshin.lib.open.world.reader;

import net.deechael.genshin.lib.open.world.SlimeLoadedWorld;
import net.deechael.genshin.lib.open.world.exception.CorruptedWorldException;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;
import net.deechael.genshin.lib.open.world.property.SlimePropertyMap;

import java.io.DataInputStream;
import java.io.IOException;

public interface SlimeWorldReader {

    SlimeLoadedWorld deserializeWorld(byte version, SlimeLoader loader, String worldName, DataInputStream dataStream, SlimePropertyMap propertyMap, boolean readOnly) throws IOException, CorruptedWorldException;
}
