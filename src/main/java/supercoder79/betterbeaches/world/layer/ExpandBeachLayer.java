package supercoder79.betterbeaches.world.layer;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import supercoder79.betterbeaches.BetterBeaches;

public enum ExpandBeachLayer implements CrossSamplingLayer {
    INSTANCE;

    @Override
    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        // TODO: optimize

        // Only replace ocean pieces
        if (center == 0) {
            if (isBeach(n)) {
                return n;
            }

            if (isBeach(e)) {
                return e;
            }

            if (isBeach(s)) {
                return s;
            }

            if (isBeach(w)) {
                return w;
            }
        }

        return center;
    }

    private static boolean isBeach(int sample) {
        return BetterBeaches.BIOME_REGISTRY.get(sample).getCategory() == Biome.Category.BEACH;
    }
}
