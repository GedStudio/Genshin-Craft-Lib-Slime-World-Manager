package net.deechael.genshin.lib.open.world.properties.type;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.IntTag;
import com.flowpowered.nbt.Tag;
import net.deechael.genshin.lib.open.world.properties.SlimeProperty;

import java.util.function.Function;

public class SlimePropertyInt extends SlimeProperty<Integer> {

    public SlimePropertyInt(String nbtName, Integer defaultValue) {
        super(nbtName, defaultValue);
    }

    public SlimePropertyInt(String nbtName, Integer defaultValue, Function<Integer, Boolean> validator) {
        super(nbtName, defaultValue, validator);
    }

    @Override
    protected void writeValue(CompoundMap compound, Integer value) {
        compound.put(getNbtName(), new IntTag(getNbtName(), value));
    }

    @Override
    protected Integer readValue(Tag<?> compoundTag) {
        return compoundTag.getAsIntTag()
                .map(Tag::getValue)
                .orElse(getDefaultValue());
    }
}
