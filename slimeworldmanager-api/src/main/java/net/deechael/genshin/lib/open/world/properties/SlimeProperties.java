package net.deechael.genshin.lib.open.world.properties;

import net.deechael.genshin.lib.open.world.properties.type.SlimePropertyBoolean;
import net.deechael.genshin.lib.open.world.properties.type.SlimePropertyInt;
import net.deechael.genshin.lib.open.world.properties.type.SlimePropertyLong;
import net.deechael.genshin.lib.open.world.properties.type.SlimePropertyString;
import org.jetbrains.annotations.ApiStatus;

public class SlimeProperties {

    public static final SlimeProperty<Integer> SPAWN_X = new SlimePropertyInt("spawnX", 0);

    public static final SlimeProperty<Integer> SPAWN_Y = new SlimePropertyInt("spawnY", 255);

    public static final SlimeProperty<Integer> SPAWN_Z = new SlimePropertyInt("spawnZ", 0);

    public static final SlimeProperty<String> DIFFICULTY = new SlimePropertyString("difficulty", "peaceful", (value) ->
            value.equalsIgnoreCase("peaceful") || value.equalsIgnoreCase("easy")
                    || value.equalsIgnoreCase("normal") || value.equalsIgnoreCase("hard")
    );

    public static final SlimeProperty<Boolean> ALLOW_MONSTERS = new SlimePropertyBoolean("allowMonsters", true);

    public static final SlimeProperty<Boolean> ALLOW_ANIMALS = new SlimePropertyBoolean("allowAnimals", true);

    public static final SlimeProperty<Boolean> DRAGON_BATTLE = new SlimePropertyBoolean("dragonBattle", false);

    public static final SlimeProperty<Boolean> PVP = new SlimePropertyBoolean("pvp", true);

    public static final SlimeProperty<String> ENVIRONMENT = new SlimePropertyString("environment", "normal", (value) ->
            value.equalsIgnoreCase("normal") || value.equalsIgnoreCase("nether") || value.equalsIgnoreCase("the_end")
    );

    public static final SlimeProperty<String> WORLD_TYPE = new SlimePropertyString("worldtype", "default", (value) ->
            value.equalsIgnoreCase("default") || value.equalsIgnoreCase("flat") || value.equalsIgnoreCase("large_biomes")
                    || value.equalsIgnoreCase("amplified") || value.equalsIgnoreCase("customized")
                    || value.equalsIgnoreCase("debug_all_block_states") || value.equalsIgnoreCase("default_1_1")
    );

    public static final SlimeProperty<String> DEFAULT_BIOME = new SlimePropertyString("defaultBiome", "minecraft:plains");

    @ApiStatus.Experimental
    public static final SlimeProperty<Boolean> SHOULD_LIMIT_SAVE = new SlimePropertyBoolean("hasSaveBounds", false);

    @ApiStatus.Experimental
    public static final SlimeProperty<Integer> SAVE_MIN_X = new SlimePropertyInt("saveMinX", 0);

    @ApiStatus.Experimental
    public static final SlimeProperty<Integer> SAVE_MIN_Z = new SlimePropertyInt("saveMinZ", 0);

    @ApiStatus.Experimental
    public static final SlimeProperty<Integer> SAVE_MAX_X = new SlimePropertyInt("saveMaxX", 0);

    @ApiStatus.Experimental
    public static final SlimeProperty<Integer> SAVE_MAX_Z = new SlimePropertyInt("saveMaxZ", 0);

    public static final SlimeProperty<Long> SEED = new SlimePropertyLong("seed", 114514L);

}
