package supercoder79.betterbeaches.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class FallenLogFeature extends Feature<DefaultFeatureConfig> {
    public FallenLogFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        Direction direction = Direction.Type.HORIZONTAL.random(random);

        BlockPos startPos = pos.offset(direction.getOpposite(), 2);
        int length = 2 + random.nextInt(2);

        boolean shouldGenerate = true;
        for (int i = 0; i < length; i++) {
            BlockPos local = startPos.offset(direction, i);
            // Current state must be air
            if (!world.getBlockState(local).isAir()) {
                shouldGenerate = false;
                break;
            }

            // Block below must be opaque
            if (!world.getBlockState(local.down()).isOpaque()) {
                shouldGenerate = false;
                break;
            }
        }

        if (shouldGenerate) {
            for (int i = 0; i < length; i++) {
                world.setBlockState(startPos.offset(direction, i), Blocks.OAK_LOG.getDefaultState().with(PillarBlock.AXIS, direction.getAxis()), 3);
            }
        }

        return false;
    }
}
