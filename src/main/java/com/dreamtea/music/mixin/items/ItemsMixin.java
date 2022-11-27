package com.dreamtea.music.mixin.items;

import com.dreamtea.music.items.TotemOfUndying;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public class ItemsMixin {

  @Redirect(
    slice = @Slice(
      from = @At(
        value = "CONSTANT",
        args= {
          "stringValue=totem_of_undying"
        },
        ordinal = 0
      )
    ),

    at = @At(
      value = "NEW",
      target = "Lnet/minecraft/item/Item;*",
      ordinal = 0
    ),
    method = "<clinit>")
  private static Item totemOfUndying(Item.Settings settings) {
    return new TotemOfUndying(settings);
  }
}
