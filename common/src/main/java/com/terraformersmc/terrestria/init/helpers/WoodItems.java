package com.terraformersmc.terrestria.init.helpers;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.*;

public class WoodItems {
	private final WoodConfig config;

	public final BlockItem log;
	public final BlockItem quarterLog;
	public final BlockItem wood;
	public final BlockItem leaves;
	public final BlockItem leafPile;
	public final BlockItem planks;
	public final BlockItem slab;
	public final BlockItem stairs;
	public final BlockItem fence;
	public final BlockItem fenceGate;
	public final BlockItem door;
	public final BlockItem button;
	public final BlockItem pressurePlate;
	public final BlockItem mosaic;
	public final BlockItem mosaicSlab;
	public final BlockItem mosaicStairs;
	public final SignItem sign;
	public final HangingSignItem hangingSign;
	public final BlockItem trapdoor;
	public final BlockItem strippedLog;
	public final BlockItem strippedQuarterLog;
	public final BlockItem strippedWood;
	public final TerraformBoatItem boat;
	public final TerraformBoatItem chestBoat;
	public final BlockItem bamboo;

	private WoodItems(WoodBlocks blocks) {
		this.config = blocks.getConfig();

		// convenience
		String name = config.name();

		// register manufactured block items

		planks = TerrestriaRegistry.registerBlockItem(name + "_planks", blocks.planks);
		slab = TerrestriaRegistry.registerBlockItem(name + "_slab", blocks.slab);
		stairs = TerrestriaRegistry.registerBlockItem(name + "_stairs", blocks.stairs);
		fence = TerrestriaRegistry.registerBlockItem(name + "_fence", blocks.fence);
		fenceGate = TerrestriaRegistry.registerBlockItem(name + "_fence_gate", blocks.fenceGate);
		door = TerrestriaRegistry.registerBlockItem(name + "_door", blocks.door);
		button = TerrestriaRegistry.registerBlockItem(name + "_button", blocks.button);
		pressurePlate = TerrestriaRegistry.registerBlockItem(name + "_pressure_plate", blocks.pressurePlate);
		trapdoor = TerrestriaRegistry.registerBlockItem(name + "_trapdoor", blocks.trapdoor);

		if (config.hasMosaic()) {
			mosaic = TerrestriaRegistry.registerBlockItem(name + "_mosaic", blocks.mosaic);
			mosaicSlab = TerrestriaRegistry.registerBlockItem(name + "_mosaic_slab", blocks.mosaicSlab);
			mosaicStairs = TerrestriaRegistry.registerBlockItem(name + "_mosaic_stairs", blocks.mosaicStairs);
		} else {
			mosaic = null;
			mosaicSlab = null;
			mosaicStairs = null;
		}

		sign = TerrestriaRegistry.register(name + "_sign", new SignItem(new Item.Settings().maxCount(16), blocks.sign, blocks.wallSign));

		hangingSign = TerrestriaRegistry.register(name + "_hanging_sign", new HangingSignItem(blocks.hangingSign, blocks.wallHangingSign, new Item.Settings().maxCount(16)));

		// register natural and stripped blocks

		if (config.hasLeaves()) {
			leaves = TerrestriaRegistry.registerBlockItem(name + "_leaves", blocks.leaves);
		} else {
			leaves = null;
		}

		if (config.hasLeafPile()) {
			leafPile = TerrestriaRegistry.registerBlockItem(name + "_leaf_pile", blocks.leafPile);
		} else {
			leafPile = null;
		}

		if (config.isBamboo()) {
			log = TerrestriaRegistry.registerBlockItem(name + "_block", blocks.log);
			strippedLog = TerrestriaRegistry.registerBlockItem("stripped_" + name + "_block", blocks.strippedLog);
		} else {
			log = TerrestriaRegistry.registerBlockItem(name + "_log", blocks.log);
			strippedLog = TerrestriaRegistry.registerBlockItem("stripped_" + name + "_log", blocks.strippedLog);
		}

		if (config.hasWood()) {
			wood = TerrestriaRegistry.registerBlockItem(name + "_wood", blocks.wood);
			strippedWood = TerrestriaRegistry.registerBlockItem("stripped_" + name + "_wood", blocks.strippedWood);
		} else {
			wood = null;
			strippedWood = null;
		}

		if (config.hasQuarterLog()) {
			quarterLog = TerrestriaRegistry.registerBlockItem(name + "_quarter_log", blocks.quarterLog);
			strippedQuarterLog = TerrestriaRegistry.registerBlockItem("stripped_" + name + "_quarter_log", blocks.strippedQuarterLog);
		} else {
			quarterLog = null;
			strippedQuarterLog = null;
		}

		// boat items and associated data registration

		if (config.hasBoat()) {
			TerraformBoatType boatType = config.isBamboo() ?
					TerrestriaBoats.registerRaft(name, planks) :
					TerrestriaBoats.register(name, planks);

			if (boatType == null) {
				throw new NullPointerException("Failed to register boat type: " + name + (config.isBamboo() ? "raft": "boat"));
			} else {
				boat = (TerraformBoatItem) boatType.getItem();
				chestBoat = (TerraformBoatItem) boatType.getChestItem();
			}
		} else {
			boat = null;
			chestBoat = null;
		}

		if (config.isBamboo()) {
			bamboo = TerrestriaRegistry.registerBlockItem("black_bamboo", blocks.bamboo);
		} else {
			bamboo = null;
		}

		this.addCompostables();
		this.addFuels();
	}

	public static WoodItems register(WoodBlocks blocks) {
		return new WoodItems(blocks);
	}


	protected void addCompostables() {
		CompostingChanceRegistry compostingRegistry = CompostingChanceRegistry.INSTANCE;
		float LEAVES_CHANCE = compostingRegistry.get(Items.OAK_LEAVES);

		if (config.hasLeaves()) {
			compostingRegistry.add(leaves, LEAVES_CHANCE);
		}
		if (config.hasLeafPile()) {
			compostingRegistry.add(leafPile, LEAVES_CHANCE);
		}
	}

	protected void addFuels() {
		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;

		fuelRegistry.add(fence, 300);
		fuelRegistry.add(fenceGate, 300);
	}

	// TODO: kill
	public WoodConfig getConfig() {
		return config;
	}
}
