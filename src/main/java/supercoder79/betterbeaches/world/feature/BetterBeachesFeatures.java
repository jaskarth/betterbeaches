package supercoder79.betterbeaches.world.feature;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.betterbeaches.mixin.GenerationSettingsAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BetterBeachesFeatures {
    private static Feature<DefaultFeatureConfig> FALLEN_LOG = new FallenLogFeature(DefaultFeatureConfig.CODEC);
    private static Feature<DefaultFeatureConfig> TIDE_POOL = new TidePoolFeature(DefaultFeatureConfig.CODEC);
    private static Feature<DefaultFeatureConfig> STONE_PILE = new StonePileFeature(DefaultFeatureConfig.CODEC);

    public static ConfiguredFeature<?, ?> FALLEN_LOGS;
    public static ConfiguredFeature<?, ?> TIDE_POOLS;
    public static ConfiguredFeature<?, ?> STONE_PILES;

    public static void init() {
        Registry.register(Registry.FEATURE, new Identifier("betterbeaches", "fallen_log"), FALLEN_LOG);
        Registry.register(Registry.FEATURE, new Identifier("betterbeaches", "tide_pool"), TIDE_POOL);
        Registry.register(Registry.FEATURE, new Identifier("betterbeaches", "stone_pile"), STONE_PILE);

        FALLEN_LOGS = FALLEN_LOG.configure(DefaultFeatureConfig.INSTANCE).spreadHorizontally().applyChance(2).decorate(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT));
        TIDE_POOLS = TIDE_POOL.configure(DefaultFeatureConfig.INSTANCE).spreadHorizontally().applyChance(4).decorate(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT));
        STONE_PILES = STONE_PILE.configure(DefaultFeatureConfig.INSTANCE).spreadHorizontally().applyChance(9).decorate(Decorator.TOP_SOLID_HEIGHTMAP.configure(DecoratorConfig.DEFAULT));

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("betterbeaches", "fallen_log"), FALLEN_LOGS);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("betterbeaches", "tide_pool"), TIDE_POOLS);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("betterbeaches", "stone_pile"), STONE_PILES);
    }

    public static void addFeature(Biome biome, GenerationStep.Feature step, ConfiguredFeature<?, ?> feature) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> featureSteps = biome.getGenerationSettings().getFeatures();

        // Mutable List
        List<List<Supplier<ConfiguredFeature<?, ?>>>> newFeatures = new ArrayList<>();

        for (GenerationStep.Feature featureStep : GenerationStep.Feature.values()) {
            int index = featureStep.ordinal();

            // create a mutable list
            List<Supplier<ConfiguredFeature<?, ?>>> features = index < featureSteps.size() ? new ArrayList<>(featureSteps.get(index)) : new ArrayList<>();

            if (step == featureStep) {
                // Add our feature if we're in the correct step
                features.add(() -> feature);
            }

            // Add entry
            newFeatures.add(features);
        }

        // Replace list
        ((GenerationSettingsAccessor) biome.getGenerationSettings()).setFeatures(newFeatures);
    }
}
