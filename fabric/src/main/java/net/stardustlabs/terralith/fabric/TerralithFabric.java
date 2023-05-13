package net.stardustlabs.terralith.fabric;

import net.fabricmc.api.ModInitializer;
import net.stardustlabs.terralith.Terralith;

public class TerralithFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Terralith.init();

        /*
        if(Terralith.MODE.equals(Terralith.Mode.TERRABLENDER)) {
            for (String biome : PreLoadTerralithBiomes.getBiomeFiles()) {
                ResourceLocation location = new TerralithRL(biome);
                if (BuiltinRegistries.BIOME.containsKey(location)) continue;
                Registry.register(BuiltinRegistries.BIOME, location, OverworldBiomes.theVoid());
            }
        }
         */

        Terralith.LOGGER.info("Terralith has been initialized");
    }


}
