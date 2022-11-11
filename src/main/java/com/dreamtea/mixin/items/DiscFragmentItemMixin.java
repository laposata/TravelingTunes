package com.dreamtea.mixin.items;

import com.dreamtea.items.DiscShard;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.DiscFragmentItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(DiscFragmentItem.class)
public class DiscFragmentItemMixin {
  @ModifyArg(method = "<init>",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/item/Item;<init>(Lnet/minecraft/item/Item$Settings;)V"
    ),
    index = 0
  )
  private static Item.Settings addFireproof(Item.Settings settings){
    return settings.fireproof();
  }

}
