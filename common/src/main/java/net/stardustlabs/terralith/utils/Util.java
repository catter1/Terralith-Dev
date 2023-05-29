package net.stardustlabs.terralith.utils;

import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.config.ConfigUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Util {


	public static Terralith.Mode getMode(){
		ConfigUtil.createConfig();
		if(!Terralith.isTerrablenderLoaded()){
			return Terralith.Mode.DEFAULT;
		}
		boolean mode = ConfigUtil.readConfig(ConfigUtil.MODE_NAME);
		try {
			if (mode) {
				return Terralith.Mode.valueOf("TERRABLENDER");
			} else {
				return Terralith.Mode.valueOf("DEFAULT");
			}
		}
		catch (IllegalArgumentException e){
			Terralith.LOGGER.warn("Invalid Mode '{}' for option '{}'", mode, "mode");
			return Terralith.Mode.TERRABLENDER;
		}
	}

	public static Terralith.Cursed getCursed(){
		ConfigUtil.createConfig();
		if(!Terralith.isTerrablenderLoaded()){
			return Terralith.Cursed.NONE;
		}
		boolean cursed = ConfigUtil.readConfig(ConfigUtil.CURSED_NAME);
		try {
			if (cursed) {
				return Terralith.Cursed.valueOf("SKYLANDS");
			} else {
				return Terralith.Cursed.valueOf("NONE");
			}
		}
		catch (IllegalArgumentException e){
			Terralith.LOGGER.warn("Invalid Mode '{}' for option '{}'", cursed, "cursed");
			return Terralith.Cursed.NONE;
		}
	}

	public static void createDirectoriesSafe(Path path) throws IOException {
		Files.createDirectories(Files.exists(path) ? path.toRealPath() : path);
	}
}