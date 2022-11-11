package net.deechael.genshin.lib.impl.world.loaders.slime;

import net.deechael.genshin.lib.impl.world.nms.world.SlimeLoadedWorld;
import net.deechael.genshin.lib.open.world.exceptions.CorruptedWorldException;
import net.deechael.genshin.lib.open.world.exceptions.NewerFormatException;
import net.deechael.genshin.lib.open.world.loaders.SlimeLoader;
import net.deechael.genshin.lib.open.world.properties.SlimePropertyMap;

import java.io.DataInputStream;
import java.io.IOException;

public interface SlimeWorldReader {

    SlimeLoadedWorld deserializeWorld(byte version, SlimeLoader loader, String worldName, DataInputStream dataStream, SlimePropertyMap propertyMap, boolean readOnly) throws IOException, CorruptedWorldException, NewerFormatException;
}
