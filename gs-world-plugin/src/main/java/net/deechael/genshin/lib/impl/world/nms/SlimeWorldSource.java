package net.deechael.genshin.lib.impl.world.nms;

import com.flowpowered.nbt.CompoundTag;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.deechael.genshin.lib.open.world.SlimeChunk;
import net.deechael.genshin.lib.open.world.SlimeLoadedWorld;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;
import net.deechael.genshin.lib.open.world.property.SlimePropertyMap;

import java.util.List;

public interface SlimeWorldSource {

    SlimeLoadedWorld createSlimeWorld(SlimeLoader loader, String worldName, Long2ObjectOpenHashMap<SlimeChunk> chunks,
                                      CompoundTag extraCompound, List<CompoundTag> mapList, byte worldVersion,
                                      SlimePropertyMap worldPropertyMap, boolean readOnly, boolean lock,
                                      Long2ObjectOpenHashMap<List<CompoundTag>> entities);

}
