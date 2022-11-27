package com.dreamtea.music.mixin.items;

import com.dreamtea.music.util.TagUtils;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.dreamtea.music.datagen.Tags.FIREPROOF;

@Mixin(Item.class)
public class ItemMixin {

  @Inject(method = "isFireproof", at = @At("RETURN"), cancellable = true)
  public void fireproofByTag(CallbackInfoReturnable<Boolean> cir){
    cir.setReturnValue(TagUtils.itemIsIn((Item)(Object)this, FIREPROOF));
  }
}
