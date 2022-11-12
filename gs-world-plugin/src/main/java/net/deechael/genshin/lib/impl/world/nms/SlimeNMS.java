package net.deechael.genshin.lib.impl.world.nms;

import net.deechael.genshin.lib.open.world.SlimeWorld;
import org.bukkit.World;

import java.io.IOException;

public interface SlimeNMS extends SlimeWorldSource {

    Object injectDefaultWorlds();

    void setDefaultWorlds(SlimeWorld normalWorld, SlimeWorld netherWorld, SlimeWorld endWorld) throws IOException;

    void generateWorld(SlimeWorld world);

    SlimeWorld getSlimeWorld(World world);

    byte getWorldVersion();

}
