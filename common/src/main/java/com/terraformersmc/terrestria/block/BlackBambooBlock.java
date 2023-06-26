package com.terraformersmc.terrestria.block;

import com.terraformersmc.terrestria.init.TerrestriaBlocks;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BlackBambooBlock extends BambooBlock {
	public static final int LOW_MAX_HEIGHT = 8;
	public static final int HIGH_MAX_HEIGHT = 10;

	public BlackBambooBlock(Settings settings) {
		super(settings);

		this.setDefaultState(this.stateManager.getDefaultState()
				.with(AGE, 0)
				.with(LEAVES, BambooLeaves.NONE)
				.with(STAGE, 0));
	}

	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		if (!fluidState.isEmpty()) {
			return null;
		}

		// Grow-by-place functionality
		BlockState stateBelow = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
		if (stateBelow.isOf(TerrestriaBlocks.BLACK_BAMBOO_SAPLING)) {
			return this.getDefaultState().with(AGE, 0);
		}
		if (stateBelow.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo)) {
			return this.getDefaultState().with(AGE, stateBelow.get(AGE) > 0 ? 1 : 0);
		}

		// Special-cased to prevent broken behavior (placing on vanilla bamboo)
		if (stateBelow.isIn(BlockTags.BAMBOO_PLANTABLE_ON) &&
				!stateBelow.isOf(Blocks.BAMBOO_SAPLING) &&
				!stateBelow.isOf(Blocks.BAMBOO)) {

			BlockState stateAbove = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
			if (stateAbove.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo)) {
				return this.getDefaultState().with(AGE, stateAbove.get(AGE));
			}

			return TerrestriaBlocks.BLACK_BAMBOO_SAPLING.getDefaultState();
		}
		return null;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(STAGE) != 0) {
			return;
		}

		int countBelow;
		if (random.nextInt(3) == 0 &&
				world.isAir(pos.up()) &&
				world.getBaseLightLevel(pos.up(), 0) >= 9 &&
				(countBelow = this.countBambooBelow(world, pos) + 1) < HIGH_MAX_HEIGHT) {
			this.updateLeaves(state, world, pos, random, countBelow);
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockState stateBelow = world.getBlockState(pos.down());
		return stateBelow.isIn(BlockTags.BAMBOO_PLANTABLE_ON) ||
				stateBelow.isOf(TerrestriaBlocks.BLACK_BAMBOO_SAPLING) ||
				stateBelow.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!state.canPlaceAt(world, pos)) {
			world.scheduleBlockTick(pos, this, 1);
		}
		if (direction == Direction.UP && neighborState.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo) && neighborState.get(AGE) > state.get(AGE)) {
			world.setBlockState(pos, state.cycle(AGE), 2);
		}

		// We skip over super.getStateForNeighborUpdate() which is specific to vanilla bamboo.
		return state;
	}

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
		int countAbove = this.countBambooAbove(world, pos);
		return countAbove + this.countBambooBelow(world, pos) + 1 < HIGH_MAX_HEIGHT &&
				world.getBlockState(pos.up(countAbove)).get(STAGE) != 1;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		int countAbove = this.countBambooAbove(world, pos);
		int countBelow = this.countBambooBelow(world, pos);
		int height = countAbove + countBelow + 1;
		int maxAttempts = 1 + random.nextInt(2);

		for (int attempt = 0; attempt < maxAttempts; ++attempt) {
			BlockPos topPos = pos.up(countAbove);
			BlockState topState = world.getBlockState(topPos);

			if (height >= HIGH_MAX_HEIGHT || topState.get(STAGE) == 1 || !world.isAir(topPos.up())) {
				return;
			}

			this.updateLeaves(topState, world, topPos, random, height);
			++countAbove;
			++height;
		}
	}

	@Override
	protected void updateLeaves(BlockState topState, World world, BlockPos topPos, Random random, int height) {
		BlockPos secondPos = topPos.down();
		BlockState secondState = world.getBlockState(secondPos);
		BlockPos thirdPos = topPos.down(2);
		BlockState thirdState = world.getBlockState(thirdPos);
		BambooLeaves newTopLeaves = BambooLeaves.NONE;

		if (height >= 1) {
			if (secondState.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo) && secondState.get(LEAVES) != BambooLeaves.NONE) {
				newTopLeaves = BambooLeaves.LARGE;
				if (thirdState.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo)) {
					world.setBlockState(secondPos, secondState.with(LEAVES, BambooLeaves.SMALL), 3);
					world.setBlockState(thirdPos, thirdState.with(LEAVES, BambooLeaves.NONE), 3);
				}
			} else {
				newTopLeaves = BambooLeaves.SMALL;
			}
		}

		BlockPos newTopPos = topPos.up();
		int newTopAge = topState.get(AGE) == 1 || thirdState.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo) ? 1 : 0;
		int newTopStage = height >= LOW_MAX_HEIGHT - 1 && random.nextFloat() < 0.33f || height == HIGH_MAX_HEIGHT - 1 ? 1 : 0;
		world.setBlockState(newTopPos, this.getDefaultState().with(AGE, newTopAge).with(LEAVES, newTopLeaves).with(STAGE, newTopStage), 3);
	}

	@Override
	@SuppressWarnings("StatementWithEmptyBody")
	protected int countBambooAbove(BlockView world, BlockPos pos) {
		int countAbove;
		for (countAbove = 0;
			 countAbove < HIGH_MAX_HEIGHT && world.getBlockState(pos.up(countAbove + 1)).isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo);
			 ++countAbove) {
		}
		return countAbove;
	}

	@Override
	@SuppressWarnings("StatementWithEmptyBody")
	protected int countBambooBelow(BlockView world, BlockPos pos) {
		int countBelow;
		for (countBelow = 0;
			 countBelow < HIGH_MAX_HEIGHT && world.getBlockState(pos.down(countBelow + 1)).isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo);
			 ++countBelow) {
		}
		return countBelow;
	}
}
