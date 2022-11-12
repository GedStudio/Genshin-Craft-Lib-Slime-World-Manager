package net.deechael.genshin.lib.open.world.properties.type;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.LongTag;
import com.flowpowered.nbt.Tag;
import net.deechael.genshin.lib.open.world.properties.SlimeProperty;

import java.util.function.Function;

public class SlimePropertyLong extends SlimeProperty<Long> {

    public SlimePropertyLong(String nbtName, Long defaultValue) {
        super(nbtName, defaultValue);
    }

    public SlimePropertyLong(String nbtName, Long defaultValue, Function<Long, Boolean> validator) {
        super(nbtName, defaultValue, validator);
    }

    @Override
    protected void writeValue(CompoundMap compound, Long value) {
        compound.put(getNbtName(), new LongTag(getNbtName(), value));
    }

    @Override
    protected Long readValue(Tag<?> compoundTag) {
        return compoundTag.getAsLongTag()
                .map(Tag::getValue)
                .orElse(getDefaultValue());
    }

}
