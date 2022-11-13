package net.deechael.genshin.lib.open.world;

import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.ListTag;
import net.deechael.genshin.lib.open.world.util.NibbleArray;

public interface SlimeChunkSection {

    ListTag<CompoundTag> getPalette();

    long[] getBlockStates();

    CompoundTag getBlockStatesTag();

    CompoundTag getBiomeTag();

    NibbleArray getBlockLight();

    NibbleArray getSkyLight();

}
