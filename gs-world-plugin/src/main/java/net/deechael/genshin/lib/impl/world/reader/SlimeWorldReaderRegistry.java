package net.deechael.genshin.lib.impl.world.reader;

import com.google.gson.JsonParser;
import net.deechael.genshin.lib.open.world.SlimeLoadedWorld;
import net.deechael.genshin.lib.open.world.exception.CorruptedWorldException;
import net.deechael.genshin.lib.open.world.exception.NewerFormatException;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;
import net.deechael.genshin.lib.open.world.property.SlimePropertyMap;
import net.deechael.genshin.lib.open.world.reader.SlimeWorldReader;
import net.deechael.genshin.lib.open.world.util.SlimeFormat;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    public static SlimeLoadedWorld readWorld(SlimeLoader loader, String worldName, byte[] serializedWorld, boolean readOnly) throws IOException, CorruptedWorldException, NewerFormatException {
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

        int length = dataStream.readInt();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = dataStream.readByte();
        }
        SlimePropertyMap propertyMap = SlimePropertyMap.fromJson(JsonParser.parseString(new String(bytes, StandardCharsets.UTF_8)).getAsJsonObject());

        SlimeWorldReader reader = FORMATS.get(version);
        return reader.deserializeWorld(version, loader, worldName, dataStream, propertyMap, readOnly);
    }

}
