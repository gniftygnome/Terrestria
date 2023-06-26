package com.terraformersmc.terrestria.block.sapling;

import com.terraformersmc.terrestria.init.TerrestriaBlocks;
import com.terraformersmc.terrestria.init.TerrestriaItems;
import net.minecraft.block.*;
import net.minecraft.block.enums.BambooLeaves;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BlackBambooSaplingBlock extends BambooSaplingBlock {
	public BlackBambooSaplingBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!state.canPlaceAt(world, pos)) {
			return Blocks.AIR.getDefaultState();
		}

		assert TerrestriaBlocks.BLACK_BAMBOO.bamboo != null;
		if (direction == Direction.UP && neighborState.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo)) {
			world.setBlockState(pos, TerrestriaBlocks.BLACK_BAMBOO.bamboo.getDefaultState(), 2);
		}

		// We skip over super.getStateForNeighborUpdate() which is specific to vanilla bamboo.
		return state;
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		assert TerrestriaItems.BLACK_BAMBOO.bamboo != null;
		return new ItemStack(TerrestriaItems.BLACK_BAMBOO.bamboo);
	}

	@Override
	protected void grow(World world, BlockPos pos) {
		assert TerrestriaBlocks.BLACK_BAMBOO.bamboo != null;
		world.setBlockState(pos.up(), TerrestriaBlocks.BLACK_BAMBOO.bamboo.getDefaultState().with(BambooBlock.LEAVES, BambooLeaves.SMALL), 3);
	}
}
