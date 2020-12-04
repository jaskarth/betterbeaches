package supercoder79.betterbeaches.world.feature;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.ChunkRandom;
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
        if (pos.getY() > 64) {
            return false;
        }

        if (!world.getFluidState(pos).isEmpty()) {
            return false;
        }

        int worldX = pos.getX();
        int worldZ = pos.getZ();

        pos = pos.down();

        ChunkRandom chunkRandom = new ChunkRandom(world.getSeed());
        DoublePerlinNoiseSampler noise = DoublePerlinNoiseSampler.method_30846(chunkRandom, -4, new DoubleArrayList(new double[]{1.0}));

        int radius = 8;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double rX = Math.abs(x / (double) radius);
                double rZ = Math.abs(z / (double) radius);

                double rad = 1.0 - Math.abs(noise.sample(worldX + x, 0, worldZ + z) * 1.25);
                if (rX * rX + rZ * rZ <= rad * rad) {
                    BlockPos local = pos.add(x, 0, z);
                    if (shouldSpawn(world, local)) {
                        world.setBlockState(local, Blocks.WATER.getDefaultState(), 3);

                        if (random.nextInt(12) == 0) {
                            TropicalFishEntity fish = new TropicalFishEntity(EntityType.TROPICAL_FISH, world.toServerWorld());

                            // Magic fish numbers
                            int n = random.nextInt(2);
                            int o = random.nextInt(6);
                            int p = random.nextInt(15);
                            int q = random.nextInt(15);
                            
                            fish.setVariant(n | o << 8 | p << 16 | q << 24);

                            fish.refreshPositionAndAngles(local, 0, 0);
                            world.spawnEntity(fish);
                        }

                        if (world.getBlockState(local.down()).isOpaque()) {
                            if (random.nextBoolean()) {
                                world.setBlockState(local, Blocks.SEAGRASS.getDefaultState(), 3);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 12; i++) {
            int x = random.nextInt(16) - random.nextInt(16);
            int z = random.nextInt(16) - random.nextInt(16);
            int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, worldX + x, worldZ + z);

            BlockPos local = new BlockPos(worldX + x, y, worldZ + z);

            if (world.getFluidState(local).isEmpty() && world.getBlockState(local.down()).isOpaque()) {
                world.setBlockState(local.down(), Blocks.GRASS_BLOCK.getDefaultState(), 3);
            }
        }

        return true;
    }

    private boolean shouldSpawn(WorldView world, BlockPos pos) {
        // Check 4 sides and top
        return  isValid(world, pos.north()) &&
                isValid(world, pos.south()) &&
                isValid(world, pos.west()) &&
                isValid(world, pos.east()) &&
                world.getBlockState(pos.up()).getMaterial().isReplaceable();
    }

    private boolean isValid(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOpaque() || world.getBlockState(pos).getFluidState().isIn(FluidTags.WATER);
    }
}
