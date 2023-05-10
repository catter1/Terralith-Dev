package net.stardustlabs.terralith.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.config.ConfigUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;


public class Util {


	public static Path getResourceDirectory(String modid, String subPath) {
		ModContainer container = FabricLoader.getInstance().getModContainer(modid).orElse(null);
		if(container != null){
			Path path = container.findPath(subPath).orElse(null);
			if(path == null) Terralith.LOGGER.error("Path for subPath: " + subPath + " in modId: " + modid + " is null");
			return path;
		}
		Terralith.LOGGER.error("Mod container for modId:" + modid + " is null");
		return null;
	}

	public static Terralith.Mode getMode(){
		ConfigUtil.createConfig();
		if(!Terralith.isTerrablenderLoaded()){
			return Terralith.Mode.DEFAULT;
		}
		boolean mode = ConfigUtil.readConfig();
		try {
			if (mode) {
				return Terralith.Mode.valueOf("TERRABLENDER");
			} else {
				return Terralith.Mode.valueOf("DEFAULT");
			}
		}
		catch (IllegalArgumentException e){
			Terralith.LOGGER.warn("Invalid Mode '{}' for option '{}'", mode, "mode");
			return Terralith.Mode.DEFAULT;
		}
	}

	public static void createDirectoriesSafe(Path path) throws IOException {
		Files.createDirectories(Files.exists(path, new LinkOption[0]) ? path.toRealPath() : path);
	}
}