package com.terraformersmc.terrestria.item;

import com.terraformersmc.terraform.dirt.DirtBlocks;
import com.terraformersmc.terrestria.Terrestria;
import com.terraformersmc.terrestria.init.TerrestriaBlocks;
import com.terraformersmc.terrestria.init.TerrestriaItems;
import com.terraformersmc.terrestria.init.helpers.StoneItems;
import com.terraformersmc.terrestria.init.helpers.WoodItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TerrestriaItemGroups {
	private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(Terrestria.MOD_ID, "items"));
	private static final HashMap<RegistryKey<ItemGroup>, HashMap<ItemConvertible, ItemGroupEntries>> ITEM_GROUP_ENTRY_MAPS;

	/*
	 * These items are the last Vanilla item of a "similar" type to items we add to Vanilla groups.
	 * Each is used to build a collection of items which will be inserted below the Vanilla item.
	 */
	private static final Item BUILDING_BAMBOO_ITEMS = Items.BAMBOO_BUTTON;
	private static final Item BUILDING_STONE_ITEMS = Items.MOSSY_STONE_BRICK_WALL;
	private static final Item BUILDING_WOOD_ITEMS = Items.CHERRY_BUTTON;
	private static final Item FUNCTIONAL_BAMBOO_SIGN = Items.BAMBOO_HANGING_SIGN;
	private static final Item FUNCTIONAL_SIGN = Items.CHERRY_HANGING_SIGN;
	private static final Item NATURAL_BAMBOO = Items.BAMBOO;
	private static final Item NATURAL_CACTUS = Items.CACTUS;
	private static final Item NATURAL_DIRT_ITEMS = Items.FARMLAND;
	private static final Item NATURAL_LEAVES = Items.CHERRY_LEAVES;
	private static final Item NATURAL_LOG = Items.CHERRY_LOG;
	private static final Item NATURAL_SAPLING = Items.CHERRY_SAPLING;
	private static final Item NATURAL_SAND = Items.RED_SANDSTONE;
	private static final Item NATURAL_STONE = Items.STONE;
	private static final Item NATURAL_TALL_VEGETATION = Items.LARGE_FERN;
	private static final Item NATURAL_VEGETATION = Items.FERN;
	private static final Item TOOLS_BOAT = Items.CHERRY_CHEST_BOAT;
	private static final Item TOOLS_RAFT = Items.BAMBOO_CHEST_RAFT;

	static {
		ITEM_GROUP_ENTRY_MAPS = new HashMap<>(8);

		/*
		 * For each Vanilla item group, add the same kinds of items Vanilla adds.
		 * Since Minecraft 1.19.3, items are often in multiple item groups...
		 */

		// BUILDING BLOCKS

		// Wood Items
		addGroupEntry(TerrestriaBlocks.SMALL_OAK_LOG, ItemGroups.BUILDING_BLOCKS, Items.OAK_WOOD);
		addGroupEntry(TerrestriaBlocks.STRIPPED_SMALL_OAK_LOG, ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_OAK_WOOD);


		// NATURAL

		// Wood Items
		addGroupEntry(TerrestriaBlocks.SMALL_OAK_LOG, ItemGroups.NATURAL, Items.OAK_LOG);

		// Sand and Sandstone
		addGroupEntry(TerrestriaBlocks.BLACK_SAND, ItemGroups.NATURAL, NATURAL_SAND);

		// Leaves
		addGroupEntry(TerrestriaBlocks.DARK_JAPANESE_MAPLE_LEAVES, ItemGroups.NATURAL, NATURAL_LEAVES);
		addGroupEntry(TerrestriaBlocks.JAPANESE_MAPLE_SHRUB_LEAVES, ItemGroups.NATURAL, NATURAL_LEAVES);
		addGroupEntry(TerrestriaBlocks.JUNGLE_PALM_LEAVES, ItemGroups.NATURAL, NATURAL_LEAVES);

		// Saplings
		addGroupEntry(TerrestriaBlocks.BRYCE_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.CYPRESS_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.DARK_JAPANESE_MAPLE_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.HEMLOCK_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.JAPANESE_MAPLE_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.JAPANESE_MAPLE_SHRUB_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.JUNGLE_PALM_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.RAINBOW_EUCALYPTUS_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.REDWOOD_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.RUBBER_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.SAGUARO_CACTUS_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.SAKURA_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.WILLOW_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);
		addGroupEntry(TerrestriaBlocks.YUCCA_PALM_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);

		// Cactuses
		addGroupEntry(TerrestriaBlocks.SAGUARO_CACTUS, ItemGroups.NATURAL, NATURAL_CACTUS);
		addGroupEntry(TerrestriaBlocks.TINY_CACTUS, ItemGroups.NATURAL, NATURAL_CACTUS);
		addGroupEntry(TerrestriaBlocks.AGAVE, ItemGroups.NATURAL, NATURAL_CACTUS);
		addGroupEntry(TerrestriaBlocks.ALOE_VERA, ItemGroups.NATURAL, NATURAL_CACTUS);
		addGroupEntry(TerrestriaBlocks.DEAD_GRASS, ItemGroups.NATURAL, Items.GRASS);

		// Vegetation
		addGroupEntry(TerrestriaBlocks.INDIAN_PAINTBRUSH, ItemGroups.NATURAL, NATURAL_VEGETATION);
		addGroupEntry(TerrestriaBlocks.MONSTERAS, ItemGroups.NATURAL, NATURAL_VEGETATION);

		// Tall Plants
		addGroupEntry(TerrestriaBlocks.CATTAIL, ItemGroups.NATURAL, NATURAL_TALL_VEGETATION);


		// FUNCTIONAL


		// REDSTONE


		// HOTBAR


		// SEARCH


		// TOOLS

		// Misc. Hand Tools
		addGroupEntry(TerrestriaItems.LOG_TURNER, ItemGroups.TOOLS, Items.FISHING_ROD);


		// COMBAT


		// CONSUMABLES


		// CRAFTING


		// SPAWN EGGS


		// INVENTORY


		// Add DirtBlocks
		addDirtEntries(TerrestriaBlocks.ANDISOL);

		// Add StoneItems
		addStoneEntries(TerrestriaItems.VOLCANIC_ROCK);

		// Add WoodItems
		addWoodEntries(TerrestriaItems.CYPRESS);
		addWoodEntries(TerrestriaItems.HEMLOCK);
		addWoodEntries(TerrestriaItems.JAPANESE_MAPLE);
		addWoodEntries(TerrestriaItems.RAINBOW_EUCALYPTUS);
		addWoodEntries(TerrestriaItems.REDWOOD);
		addWoodEntries(TerrestriaItems.RUBBER);
		addWoodEntries(TerrestriaItems.SAKURA);
		addWoodEntries(TerrestriaItems.WILLOW);
		addWoodEntries(TerrestriaItems.YUCCA_PALM);

		addWoodEntries(TerrestriaItems.BLACK_BAMBOO);


		/*
		 * Add the items configured above to the Vanilla item groups.
		 */
		for (RegistryKey<ItemGroup> group : ITEM_GROUP_ENTRY_MAPS.keySet()) {
			ItemGroupEvents.modifyEntriesEvent(group).register((content) -> {
				FeatureSet featureSet = content.getEnabledFeatures();
				HashMap<ItemConvertible, ItemGroupEntries> entryMap = ITEM_GROUP_ENTRY_MAPS.get(group);

				for (ItemConvertible relative : entryMap.keySet()) {
					ItemGroupEntries entries = entryMap.get(relative);

					// FAPI does not give us a way to add at a feature-flag-disabled location.
					// So, below we have to adjust for any items which may be disabled.
					if (relative == null) {
						// Target the end of the Item Group
						content.addAll(entries.getCollection());
					} else {
						//Terrestria.LOGGER.warn("About to add to Vanilla Item Group '{}' after Item '{}': '{}'", group.getId(), relative, entries.getCollection().stream().map(ItemStack::getItem).collect(Collectors.toList()));
						content.addAfter(relative, entries.getCollection());
					}
				}
			});
		}


		/*
		 * Also add all the items to Terrestria's own item group.
		 */
		Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
				.displayName(Text.literal("Terrestria"))
				.icon(() -> TerrestriaBlocks.RUBBER_SAPLING.asItem().getDefaultStack())
				.entries((context, entries) -> {
					ITEM_GROUP_ENTRY_MAPS.values().stream()
							.map(HashMap::values).flatMap(Collection::stream)
							.map(ItemGroupEntries::getCollection).flatMap(Collection::stream)
							.collect(Collectors.groupingByConcurrent(ItemStack::getItem)).keySet().stream()
							.sorted(Comparator.comparing((item) -> item.getName().getString())).forEach(entries::add);
				}).build()
		);
	}

	private static void addDirtEntries(DirtBlocks blocks) {
		// NATURAL

		// Dirt Items
		addGroupEntry(blocks.getGrassBlock(), ItemGroups.NATURAL, NATURAL_DIRT_ITEMS);
		addGroupEntry(blocks.getPodzol(), ItemGroups.NATURAL, NATURAL_DIRT_ITEMS);
		addGroupEntry(blocks.getDirtPath(), ItemGroups.NATURAL, NATURAL_DIRT_ITEMS);
		addGroupEntry(blocks.getDirt(), ItemGroups.NATURAL, NATURAL_DIRT_ITEMS);
		addGroupEntry(blocks.getFarmland(), ItemGroups.NATURAL, NATURAL_DIRT_ITEMS);
	}

	private static void addStoneEntries(StoneItems items) {
		// BUILDING BLOCKS

		// Stone Items
		addGroupEntry(items.plain.full, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.plain.stairs, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.plain.slab, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.plain.wall, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.pressurePlate, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.button, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.cobblestone.full, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.cobblestone.stairs, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.cobblestone.slab, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.cobblestone.wall, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.mossyCobblestone.full, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.mossyCobblestone.stairs, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.mossyCobblestone.slab, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.mossyCobblestone.wall, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.smooth.full, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.smooth.stairs, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.smooth.slab, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.smooth.wall, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.bricks.full, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.crackedBricks, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.bricks.stairs, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.bricks.slab, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.bricks.wall, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.chiseledBricks, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.mossyBricks.full, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.mossyBricks.stairs, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.mossyBricks.slab, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);
		addGroupEntry(items.mossyBricks.wall, ItemGroups.BUILDING_BLOCKS, BUILDING_STONE_ITEMS);


		// NATURAL

		// Stone Items
		addGroupEntry(items.plain.full, ItemGroups.NATURAL, NATURAL_STONE);
	}

	private static void addWoodEntries(WoodItems items) {
		Item buildingWoodItems = items.getConfig().isBamboo() ? BUILDING_BAMBOO_ITEMS : BUILDING_WOOD_ITEMS;
		Item functionalSign = items.getConfig().isBamboo() ? FUNCTIONAL_BAMBOO_SIGN : FUNCTIONAL_SIGN;
		Item toolsBoat = items.getConfig().isBamboo() ? TOOLS_RAFT : TOOLS_BOAT;

		// BUILDING BLOCKS

		// Wood Items
		addGroupEntry(items.log, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		if (items.getConfig().hasQuarterLog()) {
			addGroupEntry(items.quarterLog, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		}
		if (items.getConfig().hasWood()) {
			addGroupEntry(items.wood, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		}
		addGroupEntry(items.strippedLog, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		if (items.getConfig().hasQuarterLog()) {
			addGroupEntry(items.strippedQuarterLog, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		}
		if (items.getConfig().hasWood()) {
			addGroupEntry(items.strippedWood, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		}
		addGroupEntry(items.planks, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		if (items.getConfig().hasMosaic()) {
			addGroupEntry(items.mosaic, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		}
		addGroupEntry(items.stairs, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		if (items.getConfig().hasMosaic()) {
			addGroupEntry(items.mosaicStairs, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		}
		addGroupEntry(items.slab, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		if (items.getConfig().hasMosaic()) {
			addGroupEntry(items.mosaicSlab, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		}
		addGroupEntry(items.fence, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		addGroupEntry(items.fenceGate, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		addGroupEntry(items.door, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		addGroupEntry(items.trapdoor, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		addGroupEntry(items.pressurePlate, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);
		addGroupEntry(items.button, ItemGroups.BUILDING_BLOCKS, buildingWoodItems);


		// NATURAL

		// Wood Items
		if (items.getConfig().isBamboo()) {
			addGroupEntry(items.bamboo, ItemGroups.NATURAL, NATURAL_BAMBOO);
		} else {
			addGroupEntry(items.log, ItemGroups.NATURAL, NATURAL_LOG);
			if (items.getConfig().hasQuarterLog()) {
				addGroupEntry(items.quarterLog, ItemGroups.NATURAL, NATURAL_LOG);
				if (items.getConfig().hasWood()) {
					// At the moment, wood generates naturally on all quartered trees...
					addGroupEntry(items.wood, ItemGroups.NATURAL, NATURAL_LOG);
				}
			}
		}

		// Leaves
		if (items.getConfig().hasLeaves()) {
			addGroupEntry(items.leaves, ItemGroups.NATURAL, NATURAL_LEAVES);
		}
		if (items.getConfig().hasLeafPile()) {
			addGroupEntry(items.leafPile, ItemGroups.NATURAL, NATURAL_LEAVES);
		}


		// FUNCTIONAL

		// Wood Items
		addGroupEntry(items.sign, ItemGroups.FUNCTIONAL, functionalSign);
		addGroupEntry(items.hangingSign, ItemGroups.FUNCTIONAL, functionalSign);


		// TOOLS

		// Boats
		if (items.getConfig().hasBoat()) {
			addGroupEntry(items.boat, ItemGroups.TOOLS, toolsBoat);
			addGroupEntry(items.chestBoat, ItemGroups.TOOLS, toolsBoat);
		}
	}

	public static void addGroupEntry(ItemConvertible item, RegistryKey<ItemGroup> group) {
		// Appends the item to the bottom of the group.
		addGroupEntry(item, group, null);
	}

	public static void addGroupEntry(ItemConvertible item, RegistryKey<ItemGroup> group, @Nullable ItemConvertible relative) {
		HashMap<ItemConvertible, ItemGroupEntries> entryMap = ITEM_GROUP_ENTRY_MAPS.computeIfAbsent(group, (key) -> new HashMap<>(32));
		ItemGroupEntries entries = entryMap.computeIfAbsent(relative, ItemGroupEntries::empty);
		entries.addItem(item);
	}

	public static void init() { }
}
