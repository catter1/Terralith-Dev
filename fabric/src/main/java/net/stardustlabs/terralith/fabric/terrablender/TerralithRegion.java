package net.stardustlabs.terralith.fabric.terrablender;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.List;
import java.util.function.Consumer;

public class TerralithRegion extends Region {
    public TerralithRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pairList = TerrablenderInit.readParameterPoints();
        for(Pair<Climate.ParameterPoint, ResourceKey<Biome>> p : pairList){
            mapper.accept(p);
        }
    }
}