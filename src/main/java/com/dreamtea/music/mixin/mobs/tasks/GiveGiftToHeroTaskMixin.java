package com.dreamtea.music.mixin.mobs.tasks;

import com.dreamtea.music.consts;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.brain.task.GiveGiftsToHeroTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(GiveGiftsToHeroTask.class)
public class GiveGiftToHeroTaskMixin {

  @Inject(method = "getGifts", at = @At("RETURN"), cancellable = true)
  public void babiesGiveTunes(VillagerEntity villager, CallbackInfoReturnable<List<ItemStack>> cir){
    if(cir.getReturnValue().size() == 1){
      if(cir.getReturnValue().get(0).isOf(Items.POPPY)){
        cir.setReturnValue(ImmutableList.of(consts.GIFT_DISC_SHARD.get().toStack()));
      }
    }
  }
}
