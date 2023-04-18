package net.stardustlabs.terralith.fabric;

import net.stardustlabs.terralith.TerralithExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class TerralithExpectPlatformImpl {
    /**
     * This is our actual method to {@link TerralithExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
