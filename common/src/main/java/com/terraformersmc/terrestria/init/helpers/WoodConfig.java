package com.terraformersmc.terrestria.init.helpers;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.SaplingBlock;

@SuppressWarnings("unused")
public record WoodConfig(String name, WoodColors colors, LogSize size, SaplingBlock sapling, BambooBlock bamboo, boolean hasLeaves, boolean hasLeafPile, boolean hasWood, boolean hasQuarterLog, boolean hasMosaic, boolean usesExtendedLeaves, boolean hasBoat) {
	public boolean hasSapling() {
		return sapling != null;
	}

	public boolean isBamboo() {
		return bamboo != null;
	}

	public static final class Builder {
		private final String name;
		private final WoodColors colors;

		private LogSize size = LogSize.NORMAL;
		private SaplingBlock sapling = null;
		private BambooBlock bamboo = null;
		private boolean withoutLeaves = false;
		private boolean withLeafPile = false;
		private boolean withoutWood = false;
		private boolean withQuarterLog = false;
		private boolean withMosaic = false;
		private boolean usesExtendedLeaves = false;
		private boolean withoutBoat = false;

		private Builder(String name, WoodColors colors) {
			this.name = name;
			this.colors = colors;
		}

		public static WoodConfig.Builder of(String name, WoodColors colors) {
			return new WoodConfig.Builder(name, colors);
		}

		public WoodConfig.Builder setSize(LogSize size) {
			this.size = size;
			if (!size.equals(LogSize.NORMAL)) {
				this.withoutWood = true;
			}
			return this;
		}

		public WoodConfig.Builder setSapling(SaplingBlock sapling) {
			this.sapling = sapling;
			return this;
		}

		public WoodConfig.Builder setBamboo(BambooBlock bamboo) {
			this.bamboo = bamboo;
			this.size = LogSize.BAMBOO;
			this.withMosaic = true;
			this.withoutWood = true;
			this.withoutLeaves = true;
			return this;
		}

		public WoodConfig.Builder withoutLeaves() {
			this.withoutLeaves = true;
			return this;
		}

		public WoodConfig.Builder hasLeafPile() {
			this.withLeafPile = true;
			return this;
		}

		public WoodConfig.Builder withoutWood() {
			this.withoutWood = true;
			return this;
		}

		public WoodConfig.Builder hasQuarterLog() {
			this.withQuarterLog = true;
			return this;
		}

		public WoodConfig.Builder withMosaic() {
			this.withMosaic = true;
			return this;
		}

		public WoodConfig.Builder usesExtendedLeaves() {
			this.usesExtendedLeaves = true;
			return this;
		}

		public WoodConfig.Builder withoutBoat() {
			this.withoutBoat = true;
			return this;
		}

		public WoodConfig build() {
			return new WoodConfig(name, colors, size, sapling, bamboo, !withoutLeaves, withLeafPile, !withoutWood, withQuarterLog, withMosaic, usesExtendedLeaves, !withoutBoat);
		}
	}

	public enum LogSize {
		NORMAL("normal"),
		SMALL("small"),
		BAMBOO("bamboo");

		private final String name;

		LogSize(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}
}
