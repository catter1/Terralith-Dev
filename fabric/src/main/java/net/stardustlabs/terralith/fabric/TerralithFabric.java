package net.stardustlabs.terralith.fabric;

import net.stardustlabs.terralith.fabric.config.configs.CommentedConfig;
import net.stardustlabs.terralith.fabric.config.r.ConfigUtil;
import net.stardustlabs.terralith.fabric.utils.Util;

import net.stardustlabs.terralith.Terralith;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TerralithFabric implements ModInitializer {

    public static final String MODID = "terralith";
    public static final Logger LOGGER = LogManager.getLogger(ConfigUtil.MODID);
    public static final Version TerralithVersion = FabricLoader.getInstance().getModContainer(MODID).get().getMetadata().getVersion();
    public static Mode currentMode = Util.getMode(CommentedConfig.getConfig().terrablender());
    public static final String minTerraBlenderVersion = "2.2.0.154";

    @Override
    public void onInitialize() {
        Terralith.init();
        LOGGER.info("Loading Terralith");
    }

    public static boolean isTerraBlenderLoaded(){
        if(FabricLoader.getInstance().isModLoaded("terrablender")){
            Version version = FabricLoader.getInstance().getModContainer("terrablender").get().getMetadata().getVersion();
            Version min;
            try {
                min = Version.parse(minTerraBlenderVersion);
            } catch (VersionParsingException e) {
                throw new RuntimeException(e);
            }
            return version.compareTo(min) >= 0;
        }
        return false;
    }

    public enum Mode {
        TERRABLENDER,
        DEFAULT
    }
}
