package com.terraformersmc.terrestria.mixin;

import com.terraformersmc.terrestria.init.TerrestriaBlocks;
import com.terraformersmc.terrestria.init.TerrestriaItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(PandaEntity.class)
public class MixinPandaEntity {
	@Inject(method = "method_6504", at = @At("RETURN"), cancellable = true)
	private static void terrestria$pandaFood(ItemEntity item, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			ItemStack stack = item.getStack();
			if (stack.isOf(TerrestriaItems.BLACK_BAMBOO.bamboo) && item.isAlive() && !item.cannotPickup()) {
				cir.setReturnValue(true);
			}
		}
	}

	@ModifyArg(method = "initGoals", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;"
	))
	@SuppressWarnings("InvalidInjectorMethodSignature")
	private ItemConvertible[] terrestria$pandaTemptGoal(ItemConvertible[] items) {
		ItemConvertible[] newItems = Arrays.copyOf(items, items.length + 1);
		newItems[items.length] = TerrestriaItems.BLACK_BAMBOO.bamboo;

		return newItems;
	}

	@Inject(method = "isBreedingItem", at = @At("RETURN"), cancellable = true)
	private void terrestria$pandaBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue() && stack.isOf(TerrestriaItems.BLACK_BAMBOO.bamboo)) {
			cir.setReturnValue(true);
		}
	}

	// TODO: this would be much better as @WrapOperation
	@Mixin(PandaEntity.PandaMateGoal.class)
	static class MixinPandaMateGoal {
		@Redirect(method = "isBambooClose", at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
		))
		private boolean terrestria$pandaBambooClose(BlockState instance, Block block) {
			return instance.isOf(block) || instance.isOf(TerrestriaBlocks.BLACK_BAMBOO.bamboo);
		}
	}
}
