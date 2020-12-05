package supercoder79.betterbeaches.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class StonePileFeature extends Feature<DefaultFeatureConfig> {
    public StonePileFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (!world.getFluidState(pos).isEmpty()) {
            return false;
        }

        int count = 7 + random.nextInt(4);

        for (int i = 0; i < count; i++) {
            int x = (int) (pos.getX() + (random.nextGaussian() * 0.75));
            int z = (int) (pos.getZ() + (random.nextGaussian()) * 0.75);
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

            world.setBlockState(new BlockPos(x, y, z), Blocks.COBBLESTONE.getDefaultState(), 3);
        }

        return true;
    }
}
