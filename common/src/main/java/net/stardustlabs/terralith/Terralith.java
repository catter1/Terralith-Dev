package net.stardustlabs.terralith;

import net.cristellib.builtinpacks.BuiltInDataPacks;
import net.minecraft.network.chat.Component;
import net.stardustlabs.terralith.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Terralith {
    public static final String MOD_ID = "terralith";

    public static final String HIGHEST_MOD_ID = TerralithExpectPlatform.getPlatform().equals(TerralithExpectPlatform.Platform.FORGE) ? MOD_ID : MOD_ID + "_loader";

    public static final Logger LOGGER = LogManager.getLogger("Terralith");

    public static Mode MODE = Util.getMode();
    public static final String minTerraBlenderVersion = "2.2.0.156";

    public static void init() {
        BuiltInDataPacks.registerPack(new TerralithRL("resources/terralith_default") , HIGHEST_MOD_ID, Component.literal("Terralith Default"), () -> MODE.equals(Mode.DEFAULT));
    }

    public enum Mode {
        TERRABLENDER,
        DEFAULT
    }

    public static boolean isTerrablenderLoaded(){
        return TerralithExpectPlatform.isModLoadedWithVersion("terrablender", Terralith.minTerraBlenderVersion);
    }
}
