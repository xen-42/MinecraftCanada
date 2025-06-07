package xen42.canadamod;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.ParameterUtils.Continentalness;
import terrablender.api.ParameterUtils.Depth;
import terrablender.api.ParameterUtils.Erosion;
import terrablender.api.ParameterUtils.Humidity;
import terrablender.api.ParameterUtils.ParameterPointListBuilder;
import terrablender.api.ParameterUtils.Temperature;
import terrablender.api.ParameterUtils.Weirdness;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class CanadaModTerraBlenderAPI implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() 
    {
        Regions.register(new MapleForestRegion(Identifier.of(CanadaMod.MOD_ID, "maple_forest"), 2));
    }

    public class MapleForestRegion extends Region
    {
        public MapleForestRegion(Identifier id, int weight)
        {
            super(id, RegionType.OVERWORLD, weight);
        }

        @Override
        public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {        
            VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

            new ParameterPointListBuilder()
                .temperature(Temperature.span(Temperature.COOL, Temperature.ICY))
                .humidity(Humidity.span(Humidity.NEUTRAL, Humidity.HUMID))
                .continentalness(Continentalness.FULL_RANGE)
                .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
                .depth(Depth.SURFACE, Depth.FLOOR)
                .weirdness(Weirdness.PEAK_NORMAL)
                .build().forEach(point -> builder.add(point, CanadaMod.MAPLE_BIOME_KEY));

            builder.build().forEach(mapper);
        }
    }
}
