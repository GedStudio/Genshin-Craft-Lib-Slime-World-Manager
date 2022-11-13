package net.deechael.genshin.lib.open.world;

import com.flowpowered.nbt.CompoundTag;
import net.deechael.genshin.lib.open.world.exception.WorldAlreadyExistsException;
import net.deechael.genshin.lib.open.world.loader.SlimeLoader;
import net.deechael.genshin.lib.open.world.property.SlimeProperties;
import net.deechael.genshin.lib.open.world.property.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
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

    SlimePropertyMap getPropertyMap();

    default void setSpawn(BlockPosition position) {
        getPropertyMap().setValue(SlimeProperties.SPAWN_X, position.x());
        getPropertyMap().setValue(SlimeProperties.SPAWN_Y, position.y());
        getPropertyMap().setValue(SlimeProperties.SPAWN_Z, position.z());
    }

    default BlockPosition getSpawn() {
        SlimePropertyMap propertyMap = getPropertyMap();
        return new BlockPosition(propertyMap.getValue(SlimeProperties.SPAWN_X), propertyMap.getValue(SlimeProperties.SPAWN_Y), propertyMap.getValue(SlimeProperties.SPAWN_Z));
    }

    default Location getSpawnLocation() {
        BlockPosition position = getSpawn();
        return new Location(this.asBukkit(), position.x(), position.y(), position.z());
    }

    default void setDifficulty(Difficulty difficulty) {
        getPropertyMap().setValue(SlimeProperties.DIFFICULTY, difficulty.name().toLowerCase());
    }

    default Difficulty getDifficulty() {
        return Difficulty.valueOf(getPropertyMap().getValue(SlimeProperties.DIFFICULTY).toUpperCase());
    }

    default void setAllowMonsters(boolean isAllowed) {
        getPropertyMap().setValue(SlimeProperties.ALLOW_MONSTERS, isAllowed);
    }

    default boolean isAllowMonsters() {
        return getPropertyMap().getValue(SlimeProperties.ALLOW_MONSTERS);
    }

    default void setAllowAnimals(boolean isAllowed) {
        getPropertyMap().setValue(SlimeProperties.ALLOW_ANIMALS, isAllowed);
    }

    default boolean isAllowAnimals() {
        return getPropertyMap().getValue(SlimeProperties.ALLOW_ANIMALS);
    }

    default void setDragonBattle(boolean isAllowed) {
        getPropertyMap().setValue(SlimeProperties.DRAGON_BATTLE, isAllowed);
    }

    default boolean isDragonBattle() {
        return getPropertyMap().getValue(SlimeProperties.DRAGON_BATTLE);
    }

    default void setPvp(boolean isAllowed) {
        getPropertyMap().setValue(SlimeProperties.PVP, isAllowed);
    }

    default boolean isPvp() {
        return getPropertyMap().getValue(SlimeProperties.PVP);
    }

    boolean isReadOnly();

    SlimeWorld clone(String worldName);

    SlimeWorld clone(String worldName, SlimeLoader loader) throws WorldAlreadyExistsException, IOException;

    SlimeWorld clone(String worldName, SlimeLoader loader, boolean lock) throws WorldAlreadyExistsException, IOException;

    boolean isLocked();

    long getSeed();

    default World asBukkit() {
        return Bukkit.getWorld(this.getName());
    }

}
