package net.deechael.genshin.lib.open.world.config;

import lombok.Getter;
import lombok.Setter;
import net.deechael.genshin.lib.open.world.properties.SlimeProperties;
import net.deechael.genshin.lib.open.world.properties.SlimePropertyMap;
import org.bukkit.Difficulty;
import org.bukkit.World;

import java.util.Objects;
import java.util.Random;

public class WorldData {

    private final static Random RANDOM = new Random();

    @Getter
    @Setter
    private String dataSource = "file";
    @Getter
    @Setter
    private String spawn = "0.5, 255, 0.5";
    @Getter
    @Setter
    private String difficulty = "peaceful";
    @Getter
    @Setter
    private boolean allowMonsters = true;
    @Getter
    @Setter
    private boolean allowAnimals = true;
    @Getter
    @Setter
    private boolean dragonBattle = false;
    @Getter
    @Setter
    private boolean pvp = true;
    @Getter
    @Setter
    private String environment = "NORMAL";
    @Getter
    @Setter
    private String worldType = "DEFAULT";
    @Getter
    @Setter
    private String defaultBiome = "minecraft:plains";
    @Getter
    @Setter
    private boolean loadOnStartup = true;
    @Getter
    @Setter
    private boolean readOnly = false;
    @Getter
    @Setter
    private long seed = RANDOM.nextLong(999999999999999L);

    public SlimePropertyMap toPropertyMap() {
        try {
            Enum.valueOf(Difficulty.class, this.difficulty.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("unknown difficulty '" + this.difficulty + "'");
        }

        String[] spawnLocationSplit = spawn.split(", ");

        double spawnX, spawnY, spawnZ;

        try {
            spawnX = Double.parseDouble(spawnLocationSplit[0]);
            spawnY = Double.parseDouble(spawnLocationSplit[1]);
            spawnZ = Double.parseDouble(spawnLocationSplit[2]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("invalid spawn location '" + this.spawn + "'");
        }

        String environment = this.environment;

        try {
            Enum.valueOf(World.Environment.class, environment.toUpperCase());
        } catch (IllegalArgumentException ex) {
            try {
                int envId = Integer.parseInt(environment);

                if (envId < -1 || envId > 1) {
                    throw new NumberFormatException(environment);
                }

                environment = Objects.requireNonNull(World.Environment.getEnvironment(envId)).name();
            } catch (NumberFormatException ex2) {
                throw new IllegalArgumentException("unknown environment '" + this.environment + "'");
            }
        }

        SlimePropertyMap propertyMap = new SlimePropertyMap();

        propertyMap.setValue(SlimeProperties.SPAWN_X, (int) spawnX);
        propertyMap.setValue(SlimeProperties.SPAWN_Y, (int) spawnY);
        propertyMap.setValue(SlimeProperties.SPAWN_Z, (int) spawnZ);

        propertyMap.setValue(SlimeProperties.DIFFICULTY, difficulty);
        propertyMap.setValue(SlimeProperties.ALLOW_MONSTERS, allowMonsters);
        propertyMap.setValue(SlimeProperties.ALLOW_ANIMALS, allowAnimals);
        propertyMap.setValue(SlimeProperties.DRAGON_BATTLE, dragonBattle);
        propertyMap.setValue(SlimeProperties.PVP, pvp);
        propertyMap.setValue(SlimeProperties.ENVIRONMENT, environment);
        propertyMap.setValue(SlimeProperties.WORLD_TYPE, worldType);
        propertyMap.setValue(SlimeProperties.DEFAULT_BIOME, defaultBiome);
        propertyMap.setValue(SlimeProperties.SEED, seed);

        return propertyMap;
    }

    public static WorldData fromPropertyMap(SlimePropertyMap propertyMap) {
        WorldData worldData = new WorldData();
        worldData.spawn = propertyMap.getValue(SlimeProperties.SPAWN_X)
                + ", "
                + propertyMap.getValue(SlimeProperties.SPAWN_Y)
                + ", "
                + propertyMap.getValue(SlimeProperties.SPAWN_Z);
        worldData.difficulty = propertyMap.getValue(SlimeProperties.DIFFICULTY);
        worldData.allowMonsters = propertyMap.getValue(SlimeProperties.ALLOW_MONSTERS);
        worldData.allowAnimals = propertyMap.getValue(SlimeProperties.ALLOW_ANIMALS);
        worldData.dragonBattle = propertyMap.getValue(SlimeProperties.DRAGON_BATTLE);
        worldData.pvp = propertyMap.getValue(SlimeProperties.PVP);
        worldData.environment = propertyMap.getValue(SlimeProperties.ENVIRONMENT);
        worldData.worldType = propertyMap.getValue(SlimeProperties.WORLD_TYPE);
        worldData.defaultBiome = propertyMap.getValue(SlimeProperties.DEFAULT_BIOME);
        worldData.seed = propertyMap.getValue(SlimeProperties.SEED);
        return worldData;
    }

}
