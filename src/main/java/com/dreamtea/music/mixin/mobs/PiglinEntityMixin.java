package com.dreamtea.music.mixin.mobs;

import com.dreamtea.music.imixin.IPickDiskDrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinEntity.class)
public class PiglinEntityMixin implements IPickDiskDrops {

  @Inject(method = "interactMob", at = @At("RETURN"))
  public void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
    if(cir.getReturnValue().isAccepted()){
      canGiveDisk(player);
    }
  }

  Entity lastPlayerTraded = null;
  int tradedWithLastPlayer = 0;
  boolean friendly = false;
  public boolean canGiveDisk(PlayerEntity player) {
    if (player != null) {
      if (player.equals(lastPlayerTraded)) {
        tradedWithLastPlayer++;
        if (tradedWithLastPlayer > 3) {
          friendly = true;
          return true;
        }
        friendly = false;
        return false;
      }
      friendly = false;
      lastPlayerTraded = player;
      tradedWithLastPlayer = 0;
      return false;
    }
    friendly = false;
    return false;
  }

  public boolean  friendly(){
    boolean wasFriendly = friendly;
    friendly = false;
    return wasFriendly;
  }
}
