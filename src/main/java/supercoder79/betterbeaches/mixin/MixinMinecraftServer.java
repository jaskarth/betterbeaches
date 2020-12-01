package supercoder79.betterbeaches.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.betterbeaches.BetterBeaches;
import supercoder79.betterbeaches.world.feature.BetterBeachesFeatures;
import supercoder79.betterbeaches.world.surface.BetterBeachesSurfaceBuilders;

import java.net.Proxy;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void hookConstructor(Thread thread, DynamicRegistryManager.Impl registry, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        Registry<Biome> biomeRegistry = registry.get(Registry.BIOME_KEY);
        BetterBeaches.BIOME_REGISTRY = biomeRegistry;

        Biome beach = biomeRegistry.get(BiomeKeys.BEACH);

        ((GenerationSettingsAccessor)beach.getGenerationSettings()).setSurfaceBuilder(() -> BetterBeachesSurfaceBuilders.BEACH.withConfig(SurfaceBuilder.SAND_CONFIG));

        BetterBeachesFeatures.addFeature(beach, GenerationStep.Feature.VEGETAL_DECORATION, BetterBeachesFeatures.FALLEN_LOGS);
        BetterBeachesFeatures.addFeature(beach, GenerationStep.Feature.LAKES, BetterBeachesFeatures.TIDE_POOLS);
    }
}
