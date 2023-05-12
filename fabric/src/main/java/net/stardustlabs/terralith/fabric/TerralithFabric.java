package net.stardustlabs.terralith.fabric;

import net.fabricmc.api.ModInitializer;
import net.stardustlabs.terralith.Terralith;

public class TerralithFabric implements ModInitializer {


    @Override
    public void onInitialize() {
        Terralith.init();
        Terralith.LOGGER.info("Terralith has been initialized");
    }


}
