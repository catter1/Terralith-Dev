package net.stardustlabs.terralith.forge;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.utils.TerrablenderUtil;

import java.util.Map;

@Mod(Terralith.MOD_ID)
public class TerralithForge {

    //public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Terralith.MOD_ID);

    public TerralithForge(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Map<ResourceLocation, Biome> b = TerrablenderUtil.biomes();
        /*
        for(ResourceLocation location : b.keySet()){
            if(BuiltinRegistries.BIOME.containsKey(location)) continue;
            BIOMES.register(location.getPath(), () -> b.get(location));
        }
        BIOMES.register(bus);

         */
        Terralith.init();
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