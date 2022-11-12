package net.deechael.genshin.lib.impl.world.nms;

import com.flowpowered.nbt.CompoundTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.deechael.genshin.lib.open.world.SlimeChunk;
import net.deechael.genshin.lib.open.world.SlimeChunkSection;

import java.util.List;

@Getter
@AllArgsConstructor
public class CraftSlimeChunk implements SlimeChunk {

    private final String worldName;
    private final int x;
    private final int z;

    @Setter
    private SlimeChunkSection[] sections;
    private final CompoundTag heightMaps;
    private final int[] biomes;
    private final List<CompoundTag> tileEntities;
    private final List<CompoundTag> entities;

    @Setter
    private int minSection;
    @Setter
    private int maxSection;

}
