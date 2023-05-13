package net.stardustlabs.terralith.fabric;

import net.fabricmc.api.ModInitializer;
import net.stardustlabs.terralith.Terralith;

public class TerralithFabric implements ModInitializer {


    @Override
    public void onInitialize() {
        Terralith.init();
        /*
        Map<ResourceLocation, Biome> b = TerrablenderUtil.biomes();
        for(ResourceLocation location : b.keySet()){
            if(BuiltinRegistries.BIOME.containsKey(location)) continue;
            Registry.register(BuiltinRegistries.BIOME, location, b.get(location));
        }

         */
        Terralith.LOGGER.info("Terralith has been initialized");
    }


}
