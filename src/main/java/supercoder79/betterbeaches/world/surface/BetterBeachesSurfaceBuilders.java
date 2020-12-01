package supercoder79.betterbeaches.world.surface;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class BetterBeachesSurfaceBuilders {
    public static SurfaceBuilder<TernarySurfaceConfig> BEACH = new BeachSurfaceBuilder(TernarySurfaceConfig.CODEC);

    public static void init() {
        Registry.register(Registry.SURFACE_BUILDER, new Identifier("betterbeaches", "beach"), BEACH);
    }
}
