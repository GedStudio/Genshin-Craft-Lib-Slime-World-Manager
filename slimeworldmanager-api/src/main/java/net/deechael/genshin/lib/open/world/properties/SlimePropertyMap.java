package net.deechael.genshin.lib.open.world.properties;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
public class SlimePropertyMap {

    @Getter(value = AccessLevel.PRIVATE)
    private final CompoundMap properties;

    public SlimePropertyMap() {
        this(new CompoundMap());
    }

    public <T> T getValue(SlimeProperty<T> property) {
        if (properties.containsKey(property.getNbtName())) {
            return property.readValue(properties.get(property.getNbtName()));
        } else {
            return property.getDefaultValue();
        }
    }

    public <T> void setValue(SlimeProperty<T> property, T value) {
        if (property.getValidator() != null && !property.getValidator().apply(value)) {
            throw new IllegalArgumentException("'" + value + "' is not a valid property value.");
        }

        property.writeValue(properties, value);
    }

    @Deprecated
    public void setInt(SlimeProperty<Integer> property, int value) {
        setValue(property, value);
    }

    @Deprecated
    public void setBoolean(SlimeProperty<Boolean> property, boolean value) {
        setValue(property, value);
    }

    @Deprecated
    public void setString(SlimeProperty<String> property, String value) {
        setValue(property, value);
    }

    public void merge(SlimePropertyMap propertyMap) {
        properties.putAll(propertyMap.properties);
    }

    public CompoundTag toCompound() {
        return new CompoundTag("properties", properties);
    }

    public static SlimePropertyMap fromCompound(CompoundTag compound) {
        return new SlimePropertyMap(compound.getValue());
    }

    @Override
    public String toString() {
        return "SlimePropertyMap" + properties;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("spawnX", getValue(SlimeProperties.SPAWN_X));
        jsonObject.addProperty("spawnY", getValue(SlimeProperties.SPAWN_Y));
        jsonObject.addProperty("spawnZ", getValue(SlimeProperties.SPAWN_Z));
        jsonObject.addProperty("difficulty", getValue(SlimeProperties.DIFFICULTY));
        jsonObject.addProperty("allowMonsters", getValue(SlimeProperties.ALLOW_MONSTERS));
        jsonObject.addProperty("allowAnimals", getValue(SlimeProperties.ALLOW_ANIMALS));
        jsonObject.addProperty("dragonBattle", getValue(SlimeProperties.DRAGON_BATTLE));
        jsonObject.addProperty("pvp", getValue(SlimeProperties.PVP));
        jsonObject.addProperty("environment", getValue(SlimeProperties.ENVIRONMENT));
        jsonObject.addProperty("worldType", getValue(SlimeProperties.WORLD_TYPE));
        jsonObject.addProperty("defaultBiome", getValue(SlimeProperties.DEFAULT_BIOME));
        jsonObject.addProperty("hasSaveBounds", getValue(SlimeProperties.SHOULD_LIMIT_SAVE));
        jsonObject.addProperty("saveMinX", getValue(SlimeProperties.SAVE_MIN_X));
        jsonObject.addProperty("saveMinZ", getValue(SlimeProperties.SAVE_MIN_Z));
        jsonObject.addProperty("saveMaxX", getValue(SlimeProperties.SAVE_MAX_X));
        jsonObject.addProperty("saveMaxZ", getValue(SlimeProperties.SAVE_MAX_Z));
        jsonObject.addProperty("seed", getValue(SlimeProperties.SEED));
        return jsonObject;
    }

    public static SlimePropertyMap fromJson(JsonObject jsonObject) {
        SlimePropertyMap slimePropertyMap = new SlimePropertyMap();
        slimePropertyMap.setValue(SlimeProperties.SPAWN_X, jsonObject.get("spawnX").getAsInt());
        slimePropertyMap.setValue(SlimeProperties.SPAWN_Y, jsonObject.get("spawnY").getAsInt());
        slimePropertyMap.setValue(SlimeProperties.SPAWN_Z, jsonObject.get("spawnZ").getAsInt());
        slimePropertyMap.setValue(SlimeProperties.DIFFICULTY, jsonObject.get("difficulty").getAsString());
        slimePropertyMap.setValue(SlimeProperties.ALLOW_MONSTERS, jsonObject.get("allowMonsters").getAsBoolean());
        slimePropertyMap.setValue(SlimeProperties.ALLOW_ANIMALS, jsonObject.get("allowAnimals").getAsBoolean());
        slimePropertyMap.setValue(SlimeProperties.DRAGON_BATTLE, jsonObject.get("dragonBattle").getAsBoolean());
        slimePropertyMap.setValue(SlimeProperties.PVP, jsonObject.get("pvp").getAsBoolean());
        slimePropertyMap.setValue(SlimeProperties.ENVIRONMENT, jsonObject.get("environment").getAsString());
        slimePropertyMap.setValue(SlimeProperties.WORLD_TYPE, jsonObject.get("worldType").getAsString());
        slimePropertyMap.setValue(SlimeProperties.DEFAULT_BIOME, jsonObject.get("defaultBiome").getAsString());
        slimePropertyMap.setValue(SlimeProperties.SHOULD_LIMIT_SAVE, jsonObject.get("hasSaveBounds").getAsBoolean());
        slimePropertyMap.setValue(SlimeProperties.SAVE_MIN_X, jsonObject.get("saveMinX").getAsInt());
        slimePropertyMap.setValue(SlimeProperties.SAVE_MIN_Z, jsonObject.get("saveMinZ").getAsInt());
        slimePropertyMap.setValue(SlimeProperties.SAVE_MAX_X, jsonObject.get("saveMaxX").getAsInt());
        slimePropertyMap.setValue(SlimeProperties.SAVE_MAX_Z, jsonObject.get("saveMaxZ").getAsInt());
        slimePropertyMap.setValue(SlimeProperties.SAVE_MAX_Z, jsonObject.get("saveMaxZ").getAsInt());
        slimePropertyMap.setValue(SlimeProperties.SEED, jsonObject.get("seed").getAsLong());
        return slimePropertyMap;
    }

}
