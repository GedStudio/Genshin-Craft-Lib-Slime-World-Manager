package net.deechael.genshin.lib.open.world;

import net.deechael.genshin.lib.open.world.loader.SlimeLoader;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface SlimeLoadedWorld extends SlimeWorld {

    byte getVersion();

    void updateVersion(byte version);

    void updateChunk(SlimeChunk chunk);

    CompletableFuture<byte[]> serialize() throws IOException;

    void setLoader(SlimeLoader newLoader);
}
