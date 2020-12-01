package supercoder79.betterbeaches.mixin;

import net.minecraft.world.biome.layer.AddEdgeBiomesLayer;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supercoder79.betterbeaches.world.layer.ExpandBeachLayer;

import java.util.function.LongFunction;

@Mixin(BiomeLayers.class)
public class MixinBiomeLayers {
    private static LongFunction biomeContext;

    @Inject(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;",
            at = @At("HEAD"))
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> void captureContext(boolean old, int biomeSize, int riverSize, LongFunction<C> contextProvider, CallbackInfoReturnable<LayerFactory<T>> cir) {
        biomeContext = contextProvider;
    }

    @Redirect(method = "build(ZIILjava/util/function/LongFunction;)Lnet/minecraft/world/biome/layer/util/LayerFactory;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/layer/AddEdgeBiomesLayer;create(Lnet/minecraft/world/biome/layer/util/LayerSampleContext;Lnet/minecraft/world/biome/layer/util/LayerFactory;)Lnet/minecraft/world/biome/layer/util/LayerFactory;"))
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> biggerRiver(AddEdgeBiomesLayer layer, LayerSampleContext<T> context, LayerFactory<T> parent) {
        parent = layer.create(context, parent);
        parent = ExpandBeachLayer.INSTANCE.create((LayerSampleContext<T>) biomeContext.apply(120L), parent);
        return parent;
    }
}
