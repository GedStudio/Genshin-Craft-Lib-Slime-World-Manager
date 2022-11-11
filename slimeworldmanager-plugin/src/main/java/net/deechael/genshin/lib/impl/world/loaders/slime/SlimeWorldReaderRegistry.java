package net.deechael.genshin.lib.impl.world.loaders.slime;

import net.deechael.genshin.lib.impl.world.loaders.slime.impl.v1_9SlimeWorldFormat;
import net.deechael.genshin.lib.impl.world.nms.world.SlimeLoadedWorld;
import net.deechael.genshin.lib.open.world.exceptions.CorruptedWorldException;
import net.deechael.genshin.lib.open.world.exceptions.NewerFormatException;
import net.deechael.genshin.lib.open.world.loaders.SlimeLoader;
import net.deechael.genshin.lib.open.world.properties.SlimePropertyMap;
import net.deechael.genshin.lib.open.world.utils.SlimeFormat;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SlimeWorldReaderRegistry {

    private static final Map<Byte, SlimeWorldReader> FORMATS = new HashMap<>();

    static {
        register(new v1_9SlimeWorldFormat(), 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }


    private static void register(SlimeWorldReader format, int... bytes) {
        for (int value : bytes) {
            FORMATS.put((byte) value, format);
        }
    }

    public static SlimeLoadedWorld readWorld(SlimeLoader loader, String worldName, byte[] serializedWorld, SlimePropertyMap propertyMap, boolean readOnly) throws IOException, CorruptedWorldException, NewerFormatException {
        DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(serializedWorld));
        byte[] fileHeader = new byte[SlimeFormat.SLIME_HEADER.length];
        dataStream.read(fileHeader);

        if (!Arrays.equals(SlimeFormat.SLIME_HEADER, fileHeader)) {
            throw new CorruptedWorldException(worldName);
        }

        // File version
        byte version = dataStream.readByte();

        if (version > SlimeFormat.SLIME_VERSION) {
            throw new NewerFormatException(version);
        }

        SlimeWorldReader reader = FORMATS.get(version);
        return reader.deserializeWorld(version, loader, worldName, dataStream, propertyMap, readOnly);
    }

}
