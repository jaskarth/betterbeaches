package supercoder79.betterbeaches;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.betterbeaches.world.feature.BetterBeachesFeatures;
import supercoder79.betterbeaches.world.surface.BetterBeachesSurfaceBuilders;

public class BetterBeaches implements ModInitializer {
    // Horrifically cursed storage of the biome registry to access it from biome layers
    public static Registry<Biome> BIOME_REGISTRY = null;

    @Override
    public void onInitialize() {
        BetterBeachesFeatures.init();
        BetterBeachesSurfaceBuilders.init();
    }
}
