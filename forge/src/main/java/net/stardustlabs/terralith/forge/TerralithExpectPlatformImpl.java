package net.stardustlabs.terralith.forge;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.TerralithExpectPlatform;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.nio.file.Path;

public class TerralithExpectPlatformImpl {
    public static boolean isTerrablenderLoaded() {
        if(ModList.get().isLoaded("terrablender")){
            ArtifactVersion version = ModList.get().getModContainerById("terrablender").get().getModInfo().getVersion();
            ArtifactVersion min;
            min = new DefaultArtifactVersion(Terralith.minTerraBlenderVersion);
            return version.compareTo(min) >= 0;
        }
        return false;
    }

    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public static boolean isModLoadedWithVersion(String modid, String minVersion) {
        if(isModLoaded(modid)){
            ArtifactVersion version = ModList.get().getModContainerById(modid).get().getModInfo().getVersion();
            ArtifactVersion min;
            min = new DefaultArtifactVersion(minVersion);
            return version.compareTo(min) >= 0;
        }
        return false;
    }

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static TerralithExpectPlatform.Platform getPlatform() {
        return TerralithExpectPlatform.Platform.FORGE;
    }
}
