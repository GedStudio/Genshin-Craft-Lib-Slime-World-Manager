package net.deechael.genshin.lib.open.world;

import com.flowpowered.nbt.CompoundTag;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.experimental.Accessors;
import net.deechael.genshin.lib.open.world.exceptions.WorldAlreadyExistsException;
import net.deechael.genshin.lib.open.world.loaders.SlimeLoader;
import net.deechael.genshin.lib.open.world.properties.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface SlimeWorld {

    String getName();

    SlimeLoader getLoader();

    SlimeChunk getChunk(int x, int z);

    Map<Long, SlimeChunk> getChunks();

    CompoundTag getExtraData();

    Collection<CompoundTag> getWorldMaps();

    @Deprecated
    SlimeProperties getProperties();

    SlimePropertyMap getPropertyMap();

    boolean isReadOnly();

    SlimeWorld clone(String worldName);

    SlimeWorld clone(String worldName, SlimeLoader loader) throws WorldAlreadyExistsException, IOException;

    SlimeWorld clone(String worldName, SlimeLoader loader, boolean lock) throws WorldAlreadyExistsException, IOException;

    boolean isLocked();

    default World asBukkit() {
        return Bukkit.getWorld(this.getName());
    }

    @Getter
    @Builder(toBuilder = true)
    @Deprecated
    class SlimeProperties {

        private final double spawnX;
        @Builder.Default
        private final double spawnY = 255;
        private final double spawnZ;

        private final int difficulty;

        @Accessors(fluent = true)
        @Builder.Default
        private final boolean allowMonsters = true;
        @Accessors(fluent = true)
        @Builder.Default
        private final boolean allowAnimals = true;

        @With
        private final boolean readOnly;

        @Builder.Default
        private final boolean pvp = true;

        @Builder.Default
        private final String environment = "NORMAL";
    }

}
