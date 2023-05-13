package net.stardustlabs.terralith.utils;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.cristellib.CristelLibExpectPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.stardustlabs.terralith.Terralith;
import net.stardustlabs.terralith.TerralithRL;
import net.stardustlabs.terralith.terrablender.TerralithRegion;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TerrablenderUtil {

    private static final String OVERWORLD = "resources/terralith_default/data/minecraft/dimension/overworld.json";

    private static final String NOISE = "resources/terralith_default/data/minecraft/worldgen/noise_settings/overworld.json";


    public static SurfaceRules.RuleSource readSurfaceRulesFromNoise() {
        InputStream im;
        try {
            Path path = CristelLibExpectPlatform.getResourceDirectory(Terralith.HIGHEST_MOD_ID, NOISE);
            if(path == null) throw new RuntimeException();
            im = Files.newInputStream(path);
        } catch (IOException e) {
            Terralith.LOGGER.error("Couldn't read " + NOISE + ", crashing instead");
            throw new RuntimeException(e);
        }
        try(InputStreamReader reader = new InputStreamReader(im)) {
            JsonElement load = JsonParser.parseReader(reader);
            JsonElement element = load.getAsJsonObject().get("surface_rule");

            return readConfig(element, SurfaceRules.RuleSource.CODEC, JsonOps.INSTANCE);
        } catch (Exception errorMsg) {
            throw new IllegalArgumentException("Couldn't read " + NOISE + ", crashing instead.");
        }
    }

    public static <T> T readConfig(JsonElement load, Codec<T> codec, DynamicOps<JsonElement> ops) {
        DataResult<Pair<T, JsonElement>> decode = codec.decode(ops, load);
        Optional<DataResult.PartialResult<Pair<T, JsonElement>>> error = decode.error();
        if (error.isPresent()) {
            throw new IllegalArgumentException("Couldn't read " + load.toString() + ", crashing instead.");
        }
        return decode.result().orElseThrow().getFirst();
    }


    public static List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> readParameterPoints() {
        InputStream im;
        try {
            Path path = CristelLibExpectPlatform.getResourceDirectory(Terralith.HIGHEST_MOD_ID, OVERWORLD);
            if(path == null) throw new RuntimeException();
            im = Files.newInputStream(path);
        } catch (IOException e) {
            Terralith.LOGGER.error("Couldn't read " + OVERWORLD + ", crashing instead");
            throw new RuntimeException(e);
        }

        try (InputStreamReader reader = new InputStreamReader(im)) {
            JsonElement el = JsonParser.parseReader(reader);
            if (!el.isJsonObject()) throw new RuntimeException("Input stream is no JsonObject");
            List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> list = new ArrayList<>();
            JsonObject o = el.getAsJsonObject();
            JsonArray jsonArray = o.get("generator").getAsJsonObject().get("biome_source").getAsJsonObject().get("biomes").getAsJsonArray();
            for(int i = 0; i < jsonArray.size(); i++){
                JsonObject e = jsonArray.get(i).getAsJsonObject();
                String b = e.get("biome").getAsString();
                ResourceKey<Biome> r = ResourceKey.create(Registries.BIOME, new ResourceLocation(b));
                JsonObject jo = e.get("parameters").getAsJsonObject();


                Climate.ParameterPoint p = readConfig(jo, Climate.ParameterPoint.CODEC, JsonOps.INSTANCE);


                Pair<Climate.ParameterPoint, ResourceKey<Biome>> pair = new Pair<>(p, r);
                list.add(pair);
            }

            return list;
        } catch (FileNotFoundException e) {
            Terralith.LOGGER.error("Couldn't read " + OVERWORLD + ", crashing instead");
            throw new RuntimeException(e);
        } catch (IOException | JsonSyntaxException e) {
            Terralith.LOGGER.error("Couldn't read " + OVERWORLD + ", crashing instead");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }




    public static void readOverworldSurfaceRules() {
        SurfaceRules.RuleSource s = readSurfaceRulesFromNoise();
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, "terralith", s);
    }


    public static void registerRegions(){
        Regions.register(new TerralithRegion(new TerralithRL("overworld"), 4));
        Terralith.LOGGER.info("Terralith region created!");
    }



}
