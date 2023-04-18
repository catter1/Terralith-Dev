package net.stardustlabs.terralith.fabric.terrablender;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.stardustlabs.terralith.fabric.TerralithFabric;
import net.stardustlabs.terralith.fabric.utils.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.stardustlabs.terralith.fabric.TerralithFabric.Mode.TERRABLENDER;
import static net.stardustlabs.terralith.fabric.TerralithFabric.currentMode;
import static net.stardustlabs.terralith.fabric.config.r.ConfigUtil.MODID;


public class TerrablenderInit implements TerraBlenderApi {

    private static final String OVERWORLD = "data/minecraft/dimension/overworld.json";
    @Override
    public void onTerraBlenderInitialized() {
        if(currentMode.equals(TERRABLENDER)){
            registerRegions();
            readOverworldSurfaceRules();
        }
    }

    public static <T> T readConfig(String pathInRes, Codec<T> codec, DynamicOps<JsonElement> ops) {
        InputStream im;
        try {
            Path path = Util.getResourceDirectory(TerralithFabric.MODID, pathInRes);
            if(path == null) throw new RuntimeException();
            im = Files.newInputStream(path);
        } catch (IOException e) {
            TerralithFabric.LOGGER.error("["+MODID+"] Couldn't read " + pathInRes + ", crashing instead");
            throw new RuntimeException(e);
        }
        try(InputStreamReader reader = new InputStreamReader(im)) {
            JsonElement load = JsonParser.parseReader(reader);

            DataResult<Pair<T, JsonElement>> decode = codec.decode(ops, load);
            Optional<DataResult.PartialResult<Pair<T, JsonElement>>> error = decode.error();
            if (error.isPresent()) {
                throw new IllegalArgumentException("["+MODID+"] Couldn't read " + pathInRes + ", crashing instead.");
            }
            return decode.result().orElseThrow().getFirst();
        } catch (Exception errorMsg) {
            throw new IllegalArgumentException("["+MODID+"] Couldn't read " + pathInRes + ", crashing instead.");
        }
    }


    public static List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> readParameterPoints() {
        InputStream im;
        try {
            Path path = Util.getResourceDirectory(TerralithFabric.MODID, "/resources/" + OVERWORLD);
            if(path == null) throw new RuntimeException();
            im = Files.newInputStream(path);
        } catch (IOException e) {
            TerralithFabric.LOGGER.error("["+MODID+"] Couldn't read " + OVERWORLD + ", crashing instead");
            throw new RuntimeException(e);
        }

        try (InputStreamReader reader = new InputStreamReader(im)) {
            JsonElement el = JsonParser.parseReader(reader);
            if (!el.isJsonObject()) return null;
            List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> list = new ArrayList<>();
            JsonObject o = el.getAsJsonObject();
            JsonArray jsonArray = o.get("generator").getAsJsonObject().get("biome_source").getAsJsonObject().get("biomes").getAsJsonArray();
            for(int i = 0; i < jsonArray.size(); i++){
                JsonObject e = jsonArray.get(i).getAsJsonObject();
                String b = e.get("biome").getAsString();
                ResourceKey<Biome> r = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(b));
                JsonObject jo = e.get("parameters").getAsJsonObject();

                Climate.ParameterPoint point = new Gson().fromJson(jo, Climate.ParameterPoint.class);

                Pair<Climate.ParameterPoint, ResourceKey<Biome>> pair = new Pair<>(point, r);
                list.add(pair);
            }

            return list;
        } catch (FileNotFoundException e) {
            TerralithFabric.LOGGER.error("["+MODID+"] Couldn't read " + OVERWORLD + ", crashing instead");
            throw new RuntimeException(e);
        } catch (IOException | JsonSyntaxException e) {
            TerralithFabric.LOGGER.error("["+MODID+"] Couldn't read " + OVERWORLD + ", crashing instead");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static void readOverworldSurfaceRules() {
        SurfaceRules.RuleSource s = readConfig("resources/surface_rules.json", SurfaceRules.RuleSource.CODEC, JsonOps.INSTANCE);
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, "terralith", s);
    }

    public static void terraEnableDisable(){
        if(currentMode.equals(TERRABLENDER)){
            registerRegions();
            readOverworldSurfaceRules();
        }
        else {
            removeRegions();
            SurfaceRuleManager.removeSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, "terralith");
        }
    }

    public static void registerRegions(){
        Regions.register(new TerralithRegion(Util.terralithRes("overworld"), 4));

    }

    private static void removeRegions(){
        Regions.remove(RegionType.OVERWORLD, Util.terralithRes("overworld"));
    }
}
