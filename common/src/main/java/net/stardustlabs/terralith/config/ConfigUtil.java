package net.stardustlabs.terralith.config;

import com.google.gson.*;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.TerralithExpectPlatform;
import net.stardustlabs.terralith.utils.Util;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtil {

    public static final Path TERRALITH_CONFIG_DIR = TerralithExpectPlatform.getConfigDirectory().resolve(Terralith.MOD_ID);

    public static final Path OLD_FILE_PATH = TERRALITH_CONFIG_DIR.resolve("config.json");
    public static final Path FILE_PATH = TERRALITH_CONFIG_DIR.resolve("config.json5");
    public static final Path README_PATH = TERRALITH_CONFIG_DIR.resolve("README.txt");
    public static final String MODE_NAME = "terrablender-compatibility";
    public static final String CURSED_NAME = "cursed-skylands";

    public static void createConfig(){
        try {
            Util.createDirectoriesSafe(TERRALITH_CONFIG_DIR);
        } catch (IOException e) {
            Terralith.LOGGER.error("Couldn't create directories", e);
        }

        if (README_PATH.toFile().exists()) README_PATH.toFile().delete();
        if (FILE_PATH.toFile().exists()) return;

        Boolean mode_value = true;
        String cursed_value = "none";

        if (OLD_FILE_PATH.toFile().exists()) {
            mode_value = readConfigMode(OLD_FILE_PATH);
            cursed_value = readConfigCursed(OLD_FILE_PATH);
            README_PATH.toFile().delete();
        }

        JSONObject json = new JSONObject();
        json.put("//", "Terralith Config");
        json.put("//", "");
        json.put("//", "This option determines whether Terralith should create a Terrablender region.");
        json.put("//", "Enabling will allow for better biome rarity and compatibility with other mods.");
        json.put("//", "Disabling will allow Terralith to function as it would a datapack.");
        json.put("//", "Note: If compatibility is disabled, but BYG or BoP are installed, compatibility will be forced.");
        json.put(MODE_NAME, mode_value);
        json.put("//", "");
        json.put("//", "This option determines whether Skylands will be cursed - ONLY takes effect when Terrablender compatibility is enabled!");
        json.put("//", "The options consist of \\\"none\\\", \\\"some\\\", and \\\"very\\\". DO NOT use \\\"some\\\" or \\\"very\\\" in a real world, unless you know what you're doing!\"");
        json.put("//", "None - Skylands generate as normally as they can");
        json.put("//", "Some - Skylands can generate on top of each other, be very large, and sometimes \"cancel\" out ocean-based biomes such as Mirage Isles.");
        json.put("//", "Very - Skylands can generate anywhere and everywhere. This includes on each other, above land, and *in* land!");
        json.put("//", "It should be noted that if Terrablender compatibility is disabled, the cursed generation will not occur, no matter what.");
        json.put("//", "   *Cursed option provided in loving memory of catter's pain and suffering*");
        json.put(CURSED_NAME, cursed_value);

        try (FileWriter file = new FileWriter(String.valueOf(FILE_PATH))) {
            file.write(json.toString(4));
        } catch (IOException ex) {
            Terralith.LOGGER.error("Couldn't create config file", ex);
        }
    }

    public static boolean readConfigMode(){
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
