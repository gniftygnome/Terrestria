package com.terraformersmc.terrestria.feature;

import com.mojang.serialization.Codec;
import com.terraformersmc.terrestria.block.BlackBambooBlock;
import com.terraformersmc.terrestria.init.TerrestriaBlocks;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BlackBambooFeature extends Feature<ProbabilityConfig> {
	private static final BlockState BAMBOO = TerrestriaBlocks.BLACK_BAMBOO.bamboo.getDefaultState().with(BambooBlock.AGE, 1).with(BambooBlock.LEAVES, BambooLeaves.NONE).with(BambooBlock.STAGE, 0);
	private static final BlockState BAMBOO_TOP_1 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.LARGE).with(BambooBlock.STAGE, 1);
	private static final BlockState BAMBOO_TOP_2 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.LARGE);
	private static final BlockState BAMBOO_TOP_3 = BAMBOO.with(BambooBlock.LEAVES, BambooLeaves.SMALL);

	private static final int HEIGHT_VARIATION = BlackBambooBlock.HIGH_MAX_HEIGHT - BlackBambooBlock.LOW_MAX_HEIGHT;
	private static final int PODZOL_MAX_RADIUS = 4;

	public BlackBambooFeature(Codec<ProbabilityConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(FeatureContext<ProbabilityConfig> context) {
		assert TerrestriaBlocks.BLACK_BAMBOO.bamboo != null;
		BlockPos origin = context.getOrigin();
		StructureWorldAccess world = context.getWorld();
		Random random = context.getRandom();
		ProbabilityConfig config = context.getConfig();
		BlockPos.Mutable workingPos = new BlockPos.Mutable();

		if (world.isAir(origin)) {
			if (TerrestriaBlocks.BLACK_BAMBOO.bamboo.getDefaultState().canPlaceAt(world, origin)) {
				// podzol
				if (random.nextFloat() < config.probability) {
					int radius = random.nextInt(PODZOL_MAX_RADIUS) + 1;
					for (int x = origin.getX() - radius; x <= origin.getX() + radius; ++x) {
						for (int z = origin.getZ() - radius; z <= origin.getZ() + radius; ++z) {
							if (MathHelper.square(x - origin.getX()) + MathHelper.square(z - origin.getZ()) <= MathHelper.square(radius)) {
								workingPos.set(x, world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z) - 1, z);
								BlockState state = world.getBlockState(workingPos);
								if (state.isOf(TerrestriaBlocks.ANDISOL.getDirt()) || state.isOf(TerrestriaBlocks.ANDISOL.getGrassBlock())) {
									world.setBlockState(workingPos, TerrestriaBlocks.ANDISOL.getPodzol().getDefaultState(), 2);
								} else if (BlackBambooFeature.isSoil(world.getBlockState(workingPos))) {
									world.setBlockState(workingPos, Blocks.PODZOL.getDefaultState(), 2);
								}
							}
						}
					}
				}

				// bamboo
				workingPos.set(origin);
				int height = random.nextInt(BlackBambooBlock.LOW_MAX_HEIGHT) + HEIGHT_VARIATION + 1;
				for (int y = 0; y < height && world.isAir(workingPos); ++y) {
					world.setBlockState(workingPos, BAMBOO, 2);
					workingPos.move(Direction.UP, 1);
				}
				if (workingPos.getY() - origin.getY() >= 3) {
					world.setBlockState(workingPos, BAMBOO_TOP_1, 2);
					world.setBlockState(workingPos.move(Direction.DOWN, 1), BAMBOO_TOP_2, 2);
					world.setBlockState(workingPos.move(Direction.DOWN, 2), BAMBOO_TOP_3, 2);
				}
			}

			return true;
		}

		return false;
	}
}
