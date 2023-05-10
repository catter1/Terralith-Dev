package net.stardustlabs.terralith.config;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.TerralithExpectPlatform;
import net.stardustlabs.terralith.utils.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtil {

    public static final Path TERRALITH_CONFIG_DIR = TerralithExpectPlatform.getConfigDirectory().resolve(Terralith.MOD_ID);

    public static final Path FILE_PATH = TERRALITH_CONFIG_DIR.resolve("config.json");
    public static final String MODE_NAME = "mode";

    public static void createConfig(){
        if(FILE_PATH.toFile().exists()) return;

        JsonObject file = new JsonObject();
        file.addProperty(MODE_NAME, true);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(FILE_PATH.toFile()); JsonWriter jsonWriter = gson.newJsonWriter(fileWriter)) {
            Util.createDirectoriesSafe(TERRALITH_CONFIG_DIR);
            jsonWriter.jsonValue(gson.toJson(file));
        } catch (IOException ex) {
            Terralith.LOGGER.error("Couldn't create Directories or config file");
        }
    }

    public static boolean readConfig(){
        InputStream in;
        try {
            in = Files.newInputStream(FILE_PATH);
        } catch (IOException e) {
            Terralith.LOGGER.error("Couldn't read " + FILE_PATH);
            return false;
        }
        try(InputStreamReader reader = new InputStreamReader(in)) {
            if(JsonParser.parseReader(reader) instanceof JsonObject o){
                JsonElement element = o.get(MODE_NAME);
                if(element instanceof JsonPrimitive primitive && primitive.isBoolean()){
                    return element.getAsBoolean();
                }
            }
        } catch (Exception errorMsg) {
            Terralith.LOGGER.error("Couldn't read " + FILE_PATH);
        }
        return false;
    }

}
