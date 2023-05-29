package net.stardustlabs.terralith.fabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceLocation;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.TerralithRL;
import net.stardustlabs.terralith.utils.PreLoadTerralithBiomes;
import net.stardustlabs.terralith.utils.TerrablenderUtil;

public class TerralithFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        if(Terralith.MODE.equals(Terralith.Mode.TERRABLENDER)){
            TerrablenderUtil.registerRegions();
            TerrablenderUtil.readOverworldSurfaceRules();
        } else {
            Terralith.init();
        }

        Terralith.LOGGER.info("Terralith has been initialized");
    }


}
