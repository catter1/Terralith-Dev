package net.stardustlabs.terralith;

import net.cristellib.builtinpacks.BuiltInDataPacks;
import net.stardustlabs.terralith.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Terralith {
    public static final String MOD_ID = "terralith";

    public static final Logger LOGGER = LogManager.getLogger("Terralith");

    public static Mode MODE = Util.getMode();
    public static final String minTerraBlenderVersion = "2.2.0.154";

    public static void init() {
        BuiltInDataPacks.registerPack("Terralith Default", "resources/terralith_default", MOD_ID, () -> MODE.equals(Mode.DEFAULT));
    }

    public enum Mode {
        TERRABLENDER,
        DEFAULT
    }

    public static boolean isTerrablenderLoaded(){
        return TerralithExpectPlatform.isModLoadedWithVersion("terrablender", Terralith.minTerraBlenderVersion);
    }
}
