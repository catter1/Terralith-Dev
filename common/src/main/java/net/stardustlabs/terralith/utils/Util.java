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

	public static Terralith.Disabled getDisabled(){
		ConfigUtil.createConfig();
		if(!Terralith.isTerrablenderLoaded()){
			return Terralith.Disabled.NONE;
		}
		boolean disabled = ConfigUtil.readConfig(ConfigUtil.DISABLED_NAME);
		try {
			if (disabled) {
				return Terralith.Disabled.valueOf("SKYLANDS");
			} else {
				return Terralith.Disabled.valueOf("NONE");
			}
		}
		catch (IllegalArgumentException e){
			Terralith.LOGGER.warn("Invalid Mode '{}' for option '{}'", disabled, "disabled");
			return Terralith.Disabled.NONE;
		}
	}

	public static void createDirectoriesSafe(Path path) throws IOException {
		Files.createDirectories(Files.exists(path) ? path.toRealPath() : path);
	}
}