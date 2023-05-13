package net.stardustlabs.terralith.fabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceLocation;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.TerralithRL;
import net.stardustlabs.terralith.utils.PreLoadTerralithBiomes;

public class TerralithFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Terralith.init();

        if(Terralith.MODE.equals(Terralith.Mode.TERRABLENDER)) {
            for (String biome : PreLoadTerralithBiomes.getBiomeFiles()) {
                ResourceLocation location = new TerralithRL(biome);
                if (BuiltinRegistries.BIOME.containsKey(location)) continue;
                Registry.register(BuiltinRegistries.BIOME, location, OverworldBiomes.theVoid());
            }
        }

        Terralith.LOGGER.info("Terralith has been initialized");
    }


}
