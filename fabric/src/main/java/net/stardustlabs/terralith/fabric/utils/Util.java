package net.stardustlabs.terralith.fabric.utils;

import net.stardustlabs.terralith.fabric.TerralithFabric;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;

import static net.stardustlabs.terralith.fabric.TerralithFabric.*;
import static net.stardustlabs.terralith.fabric.TerralithFabric.Mode.DEFAULT;

public class Util {


	public static ResourceLocation terralithRes(String name){
		return new ResourceLocation(MODID, name);
	}

	public static Path getResourceDirectory(String modid, String subPath) {
		ModContainer container = FabricLoader.getInstance().getModContainer(modid).orElse(null);
		if(container != null){
			Path path = container.findPath(subPath).orElse(null);
			if(path == null) TerralithFabric.LOGGER.error("Path for subPath: " + subPath + " in modId: " + modid + " is null");
			return path;
		}
		TerralithFabric.LOGGER.error("Mod container for modId:" + modid + " is null");
		return null;
	}

	public static Mode getMode(Boolean mode){
		if(!isTerraBlenderLoaded()){
			return DEFAULT;
		}
		try {
			if (mode == true) {
				return Mode.valueOf("TERRABLENDER");
			} else {
				return Mode.valueOf("DEFAULT");
			}
		}
		catch (IllegalArgumentException e){
			LOGGER.warn("["+MODID+"] Invalid Mode '{}' for option '{}'", mode, "mode");
			return DEFAULT;
		}
	}
}