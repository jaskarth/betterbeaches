package supercoder79.betterbeaches.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class TidePoolFeature extends Feature<DefaultFeatureConfig> {
    public TidePoolFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        int radius = 3 + random.nextInt(4);

        double threshold = random.nextDouble() * 0.25 + 0.6;

        double aX = random.nextDouble() * 0.2 + 0.8;
        double aY = random.nextDouble() * 0.2 + 0.8;
        double aZ = random.nextDouble() * 0.2 + 0.8;

        for(int x = -radius; x <= radius; x++) {
            for(int z = -radius; z <= radius; z++) {
                for(int y = -radius; y <= radius; y++) {
                    double rX = Math.abs(x / (double) radius);
                    double rZ = Math.abs(z / (double) radius);
                    double rY = Math.abs(y / (double) radius);

                    rX *= aX;
                    rY *= aY;
                    rZ *= aZ;

                    if (rX * rX + rY * rY + rZ * rZ < threshold) {
                        BlockState state = Blocks.AIR.getDefaultState();
                        if (pos.getY() + y < chunkGenerator.getSeaLevel()) {
                            state = Blocks.WATER.getDefaultState();
                        }

                        world.setBlockState(pos.add(x, y, z), state, 3);
                    }
                }
            }
        }

        return false;
    }
}
