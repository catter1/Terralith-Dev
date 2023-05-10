package net.stardustlabs.terralith.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.utils.TerrablenderUtil;

@Mod(Terralith.MOD_ID)
public class TerralithForge {

    public TerralithForge(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        if(TerralithExpectPlatformImpl.isTerrablenderLoaded()) bus.addListener(this::terraBlenderSetup);
    }

    private void terraBlenderSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if(Terralith.MODE.equals(Terralith.Mode.TERRABLENDER)){
                TerrablenderUtil.registerRegions();
                TerrablenderUtil.readOverworldSurfaceRules();
            }
        });
    }
}