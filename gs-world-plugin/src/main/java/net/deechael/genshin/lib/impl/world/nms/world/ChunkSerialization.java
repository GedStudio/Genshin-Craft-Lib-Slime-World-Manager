package net.deechael.genshin.lib.impl.world.nms.world;

import com.flowpowered.nbt.CompoundTag;

import java.util.List;

public record ChunkSerialization(byte[] chunks, List<CompoundTag> tileEntities, List<CompoundTag> entities) {
}
