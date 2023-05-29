package net.stardustlabs.terralith.forge;

import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.utils.PreLoadTerralithBiomes;
import net.stardustlabs.terralith.utils.TerrablenderUtil;
import net.cristellib.builtinpacks.BuiltInDataPacks;

@Mod(Terralith.MOD_ID)
public class TerralithForge {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Terralith.MOD_ID);

    public TerralithForge(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        for(String biome : PreLoadTerralithBiomes.getBiomeFiles()){
            BIOMES.register(biome, OverworldBiomes::theVoid);
        }
        BIOMES.register(bus);


        if(Terralith.isTerrablenderLoaded()) bus.addListener(this::terraBlenderSetup);

        Terralith.LOGGER.info("Terralith has been initialized");
    }

    private void terraBlenderSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if(Terralith.MODE.equals(Terralith.Mode.TERRABLENDER)){
                TerrablenderUtil.registerRegions();
                TerrablenderUtil.readOverworldSurfaceRules();
            } else {
                BuiltInDataPacks.registerPack("Terralith Default", "resources/terralith_default", Terralith.HIGHEST_MOD_ID, () -> Terralith.MODE.equals(Terralith.Mode.DEFAULT));
            }
        });
    }
}