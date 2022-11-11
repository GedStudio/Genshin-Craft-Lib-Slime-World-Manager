package net.deechael.genshin.lib.impl.world.importer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChunkEntry {

    private final int offset;
    private final int paddedSize;

}
