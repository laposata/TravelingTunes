package com.dreamtea.music.mixin.items;

import net.minecraft.item.DiscFragmentItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

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
