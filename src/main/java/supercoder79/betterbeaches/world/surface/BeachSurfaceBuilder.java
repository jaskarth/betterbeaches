package supercoder79.betterbeaches.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class BeachSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public BeachSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        double scaledNoise = noise / 16; // Noise is [-8, 8] by default

        scaledNoise /= 3; // Scale the noise to make the patches less often

        if (random.nextDouble() < scaledNoise) {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.STONE_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.SAND_CONFIG);
        }
    }
}
