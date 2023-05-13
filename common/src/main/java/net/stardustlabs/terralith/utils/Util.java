package net.stardustlabs.terralith.utils;

import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.config.ConfigUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;


public class Util {


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