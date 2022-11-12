package net.deechael.genshin.lib.open.world;

import com.flowpowered.nbt.CompoundTag;

import java.util.List;

public interface SlimeChunk {

    String getWorldName();

    int getX();

    int getZ();

    SlimeChunkSection[] getSections();

    int getMinSection();

    int getMaxSection();

    CompoundTag getHeightMaps();

    int[] getBiomes();

    List<CompoundTag> getTileEntities();

    @Deprecated
    List<CompoundTag> getEntities();
}
