package net.stardustlabs.terralith.forge;

import dev.architectury.platform.forge.EventBuses;
import net.stardustlabs.terralith.Terralith;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Terralith.MOD_ID)
public class TerralithForge {
    public TerralithForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Terralith.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Terralith.init();
    }
}
