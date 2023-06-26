package com.terraformersmc.terrestria.init.helpers;

import com.terraformersmc.terraform.leaves.block.LeafPileBlock;
import com.terraformersmc.terraform.leaves.block.TransparentLeavesBlock;
import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.wood.block.*;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terrestria.Terrestria;
import com.terraformersmc.terrestria.block.TerrestriaOptiLeavesBlock;
import com.terraformersmc.terrestria.init.TerrestriaBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.util.Identifier;

public class WoodBlocks {
	private final WoodConfig config;

	public final Block log;
	public final Block quarterLog;
	public final Block wood;
	public final Block leaves;
	public final LeafPileBlock leafPile;
	public final Block planks;
	public final SlabBlock slab;
	public final StairsBlock stairs;
	public final FenceBlock fence;
	public final FenceGateBlock fenceGate;
	public final DoorBlock door;
	public final ButtonBlock button;
	public final PressurePlateBlock pressurePlate;
	public final Block mosaic;
	public final SlabBlock mosaicSlab;
	public final StairsBlock mosaicStairs;
	public final TerraformSignBlock sign;
	public final TerraformWallSignBlock wallSign;
	public final TerraformHangingSignBlock hangingSign;
	public final TerraformWallHangingSignBlock wallHangingSign;
	public final TrapdoorBlock trapdoor;
	public final Block strippedLog;
	public final Block strippedQuarterLog;
	public final Block strippedWood;
	public final BambooBlock bamboo;

	private WoodBlocks(WoodConfig config) {
		this.config = config;

		// convenience
		String name = config.name();
		WoodColors colors = config.colors();
		WoodConfig.LogSize size = config.size();

		// register manufactured blocks

		planks = TerrestriaRegistry.register(name + "_planks", new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).mapColor(colors.planks)));
		slab = TerrestriaRegistry.register(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).mapColor(colors.planks)));
		stairs = TerrestriaRegistry.register(name + "_stairs", new StairsBlock(planks.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).mapColor(colors.planks)));
		fence = TerrestriaRegistry.register(name + "_fence", new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).mapColor(colors.planks)));
		fenceGate = TerrestriaRegistry.register(name + "_fence_gate", new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE).mapColor(colors.planks), WoodType.OAK));
		door = TerrestriaRegistry.register(name + "_door", new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR).mapColor(colors.planks), BlockSetType.OAK));
		button = TerrestriaRegistry.register(name + "_button", new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).mapColor(colors.planks), BlockSetType.OAK, 30, true));
		pressurePlate = TerrestriaRegistry.register(name + "_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE).mapColor(colors.planks), BlockSetType.OAK));
		trapdoor = TerrestriaRegistry.register(name + "_trapdoor", new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).mapColor(colors.planks), BlockSetType.OAK));

		if (config.hasMosaic()) {
			mosaic = TerrestriaRegistry.register(name + "_mosaic", new Block(FabricBlockSettings.copyOf(Blocks.BAMBOO_MOSAIC).mapColor(colors.planks)));
			mosaicSlab = TerrestriaRegistry.register(name + "_mosaic_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_MOSAIC_SLAB).mapColor(colors.planks)));
			mosaicStairs = TerrestriaRegistry.register(name + "_mosaic_stairs", new StairsBlock(mosaic.getDefaultState(), FabricBlockSettings.copyOf(Blocks.BAMBOO_MOSAIC_STAIRS).mapColor(colors.planks)));
		} else {
			mosaic = null;
			mosaicSlab = null;
			mosaicStairs = null;
		}

		Identifier signTexture = Identifier.of(Terrestria.MOD_ID, "entity/signs/" + name);
		sign = TerrestriaRegistry.register(name + "_sign", new TerraformSignBlock(signTexture, FabricBlockSettings.copyOf(Blocks.OAK_SIGN).mapColor(colors.planks)));
		wallSign = TerrestriaRegistry.register(name + "_wall_sign", new TerraformWallSignBlock(signTexture, FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).mapColor(colors.planks).dropsLike(sign)));

		Identifier hangingSignTexture = Identifier.of(Terrestria.MOD_ID, "entity/signs/hanging/" + name);
		Identifier hangingSignGuiTexture = Identifier.of(Terrestria.MOD_ID, "textures/gui/hanging_signs/" + name);
		hangingSign = TerrestriaRegistry.register(name + "_hanging_sign", new TerraformHangingSignBlock(hangingSignTexture, hangingSignGuiTexture, FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).mapColor(colors.planks)));
		wallHangingSign = TerrestriaRegistry.register(name + "_wall_hanging_sign", new TerraformWallHangingSignBlock(hangingSignTexture, hangingSignGuiTexture, FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).mapColor(colors.planks).dropsLike(hangingSign)));

		// register natural and stripped blocks

		if (config.hasLeaves()) {
			if (config.usesExtendedLeaves()) {
				if (size.equals(WoodConfig.LogSize.SMALL)) {
					throw new IllegalArgumentException("Small log trees are not compatible with extended leaves, I'm not sure how you even did this...");
				}
				leaves = TerrestriaRegistry.register(name + "_leaves", new TerrestriaOptiLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).mapColor(colors.leaves).allowsSpawning(TerrestriaBlocks::canSpawnOnLeaves).suffocates(TerrestriaBlocks::never).blockVision(TerrestriaBlocks::never)));
			} else {
				if (size.equals(WoodConfig.LogSize.SMALL)) {
					leaves = TerrestriaRegistry.register(name + "_leaves", new TransparentLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).mapColor(colors.leaves).allowsSpawning(TerrestriaBlocks::canSpawnOnLeaves).suffocates(TerrestriaBlocks::never).blockVision(TerrestriaBlocks::never)));
				} else {
					leaves = TerrestriaRegistry.register(name + "_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).mapColor(colors.leaves).allowsSpawning(TerrestriaBlocks::canSpawnOnLeaves).suffocates(TerrestriaBlocks::never).blockVision(TerrestriaBlocks::never)));
				}
			}
		} else {
			leaves = null;
		}

		if (config.hasLeafPile()) {
			leafPile = TerrestriaRegistry.register(name + "_leaf_pile", new LeafPileBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS).mapColor(colors.leaves)));
		} else {
			leafPile = null;
		}

		if (size.equals(WoodConfig.LogSize.SMALL)) {
			// Small logs have neither wood nor quarter logs.
			log = TerrestriaRegistry.register(name + "_log", SmallLogBlock.of(leaves, colors.planks, colors.bark));
			strippedLog = TerrestriaRegistry.register("stripped_" + name + "_log", SmallLogBlock.of(leaves, colors.planks));

			if (config.hasWood()) {
				wood = TerrestriaRegistry.register(name + "_wood", SmallLogBlock.of(leaves, colors.bark));
				strippedWood = TerrestriaRegistry.register("stripped_" + name + "_wood", SmallLogBlock.of(leaves, colors.planks));
			} else {
				wood = null;
				strippedWood = null;
			}
		} else {
			if (config.isBamboo()) {
				log = TerrestriaRegistry.register(name + "_block", PillarLogHelper.of(colors.planks, colors.bark));
				strippedLog = TerrestriaRegistry.register("stripped_" + name + "_block", PillarLogHelper.of(colors.planks));
			} else {
				log = TerrestriaRegistry.register(name + "_log", PillarLogHelper.of(colors.planks, colors.bark));
				strippedLog = TerrestriaRegistry.register("stripped_" + name + "_log", PillarLogHelper.of(colors.planks));
			}

			if (config.hasWood()) {
				wood = TerrestriaRegistry.register(name + "_wood", PillarLogHelper.of(colors.bark));
				strippedWood = TerrestriaRegistry.register("stripped_" + name + "_wood", PillarLogHelper.of(colors.planks));
			} else {
				wood = null;
				strippedWood = null;
			}
		}

		if (config.hasQuarterLog()) {
			quarterLog = TerrestriaRegistry.register(name + "_quarter_log", QuarterLogBlock.of(colors.planks, colors.bark));
			strippedQuarterLog = TerrestriaRegistry.register("stripped_" + name + "_quarter_log", QuarterLogBlock.of(colors.planks));
		} else {
			quarterLog = null;
			strippedQuarterLog = null;
		}

		if (config.isBamboo()) {
			bamboo = TerrestriaRegistry.register(name, config.bamboo());
		} else {
			bamboo = null;
		}

		this.addFlammables();
		this.addStrippables();
	}

	public static WoodBlocks register(WoodConfig config) {
		return new WoodBlocks(config);
	}


	private void addFlammables() {
		FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();

		// manufactured
		flammableRegistry.add(fence, 5, 20);
		flammableRegistry.add(fenceGate, 5, 20);
		flammableRegistry.add(planks, 5, 20);
		flammableRegistry.add(slab, 5, 20);
		flammableRegistry.add(stairs, 5, 20);

		if (config.hasMosaic()) {
			flammableRegistry.add(mosaic, 5, 20);
			flammableRegistry.add(mosaicSlab, 5, 20);
			flammableRegistry.add(mosaicStairs, 5, 20);
		}

		// tree
		flammableRegistry.add(log, 5, 5);
		flammableRegistry.add(strippedLog, 5, 5);
		if (config.hasWood()) {
			flammableRegistry.add(wood, 5, 5);
			flammableRegistry.add(strippedWood, 5, 5);
		}
		if (config.hasQuarterLog()) {
			flammableRegistry.add(quarterLog, 5, 5);
			flammableRegistry.add(strippedQuarterLog, 5, 5);
		}

		flammableRegistry.add(leaves, 30, 60);
		if (config.hasLeafPile()) {
			flammableRegistry.add(leafPile, 30, 60);
		}
	}

	private void addStrippables() {
		if (log != null && strippedLog != null) {
			StrippableBlockRegistry.register(log, strippedLog);
		}
		if (wood != null && strippedWood != null) {
			StrippableBlockRegistry.register(wood, strippedWood);
		}
		if (quarterLog != null && strippedQuarterLog != null) {
			StrippableBlockRegistry.register(quarterLog, strippedQuarterLog);
		}
	}

	public WoodConfig getConfig() {
		return config;
	}
}
