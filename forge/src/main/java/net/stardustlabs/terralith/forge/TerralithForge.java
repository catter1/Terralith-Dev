package net.stardustlabs.terralith.forge;

import net.stardustlabs.terralith.forge.config.configs.CommentedConfig;
import net.stardustlabs.terralith.forge.config.r.ConfigUtil;
import net.stardustlabs.terralith.forge.utils.Util;

import net.stardustlabs.terralith.Terralith;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TerralithForge.MODID)
public class TerralithForge {

    public static final String MODID = "terralith";
    public static final String NAME = "Terralith";
    public static final String VERSION = "1.0.0"; // Replace with the appropriate version for your mod
    public static final Logger LOGGER = LogManager.getLogger(ConfigUtil.MODID);
    public static final String minTerraBlenderVersion = "2.2.0.154";
    
    @Instance(TerralithForge.MODID)
    public static TerralithForge instance;

    @Metadata(TerralithForge.MODID)
    public static ModContainer container;

    public static Mode currentMode = Util.getMode(CommentedConfig.getConfig().terrablender());

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Pre-initialization code
        Terralith.init();
        LOGGER.info("Loading Terralith");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Initialization code
    }

    public static boolean isTerraBlenderLoaded() {
        if (Loader.isModLoaded("terrablender")) {
            ModContainer modContainer = Loader.instance().getIndexedModList().get("terrablender");
            Version version = modContainer.getProcessedVersion();
            Version min;
            try {
                min = VersionParser.parseVersionReference(minTerraBlenderVersion);
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