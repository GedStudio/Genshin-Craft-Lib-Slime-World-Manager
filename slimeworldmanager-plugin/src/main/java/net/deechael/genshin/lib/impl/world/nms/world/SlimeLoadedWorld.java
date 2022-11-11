package net.deechael.genshin.lib.impl.world.nms.world;

import net.deechael.genshin.lib.open.world.SlimeChunk;
import net.deechael.genshin.lib.open.world.SlimeWorld;
import net.deechael.genshin.lib.open.world.loaders.SlimeLoader;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface SlimeLoadedWorld extends SlimeWorld {

    byte getVersion();

    void updateVersion(byte version);

    void updateChunk(SlimeChunk chunk);

    CompletableFuture<byte[]> serialize() throws IOException;

    void setLoader(SlimeLoader newLoader);
}
