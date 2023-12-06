package net.stardustlabs.terralith.config;

import com.google.gson.*;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.TerralithExpectPlatform;
import net.stardustlabs.terralith.utils.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtil {

    public static final Path TERRALITH_CONFIG_DIR = TerralithExpectPlatform.getConfigDirectory().resolve(Terralith.MOD_ID);

    public static final Path OLD_FILE_PATH = TERRALITH_CONFIG_DIR.resolve("config.json");
    public static final Path FILE_PATH = TERRALITH_CONFIG_DIR.resolve("terralith.toml");
    public static final Path README_PATH = TERRALITH_CONFIG_DIR.resolve("README.txt");
    public static final String MODE_NAME = "terrablender_compatibility";
    public static final String CURSED_NAME = "cursed_skylands";
    public static TerralithConfig CONFIG;

    public static void createConfig(){
        try {
            Util.createDirectoriesSafe(TERRALITH_CONFIG_DIR);
        } catch (IOException e) {
            Terralith.LOGGER.error("Couldn't create directories", e);
        }

        if (README_PATH.toFile().exists()) README_PATH.toFile().delete();

        boolean mode_value = true;
        String cursed_value = "none";

        if (OLD_FILE_PATH.toFile().exists()) {
            mode_value = readConfigMode(OLD_FILE_PATH);
            cursed_value = readConfigCursed(OLD_FILE_PATH);
            OLD_FILE_PATH.toFile().delete();
            Terralith.LOGGER.info("Detected old json config file. Migrating to toml...");
        } else {
            Terralith.LOGGER.info("No config file detected. Creating one...");
        }

        CONFIG = new TerralithConfig(FILE_PATH, mode_value, cursed_value);
    }

    public static boolean readConfigMode(){
        if (CONFIG != null) {
            return CONFIG.terrablenderCompatibility;
        }

        return readConfigMode(FILE_PATH);
    }

    public static boolean readConfigMode(Path filepath){
        try (InputStream in = Files.newInputStream(filepath);
             InputStreamReader reader = new InputStreamReader(in)) {
            if(JsonParser.parseReader(reader) instanceof JsonObject o){
                JsonElement element = o.get(MODE_NAME);
                if (element instanceof JsonPrimitive primitive && primitive.isBoolean()) {
                    return element.getAsBoolean();
                }
            }
        } catch (IOException e) {
            Terralith.LOGGER.error("Couldn't read " + filepath, e);
        }
        return false;
    }

    public static String readConfigCursed(){
        if (CONFIG != null) {
            return CONFIG.cursedSkylands;
        }

        return readConfigCursed(FILE_PATH);
    }

    public static String readConfigCursed(Path filepath){
        try (InputStream in = Files.newInputStream(filepath);
             InputStreamReader reader = new InputStreamReader(in)) {
            if(JsonParser.parseReader(reader) instanceof JsonObject o){
                JsonElement element = o.get(CURSED_NAME);
                if (element instanceof JsonPrimitive primitive && primitive.isString()) {
                    return element.getAsString();
                }
            }
        } catch (IOException e) {
            Terralith.LOGGER.error("Couldn't read " + filepath, e);
        }
        return "none";
    }

}
