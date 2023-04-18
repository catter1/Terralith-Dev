package net.stardustlabs.terralith.forge;

import net.stardustlabs.terralith.TerralithExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class TerralithExpectPlatformImpl {
    /**
     * This is our actual method to {@link TerralithExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
