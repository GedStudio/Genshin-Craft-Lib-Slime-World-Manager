package net.deechael.genshin.lib.open.world.properties;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
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
}
