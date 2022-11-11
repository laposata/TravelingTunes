package com.dreamtea.mixin.mobs;

import com.dreamtea.imixin.IWasAZombie;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

  @Inject(method = "onKilledOther", at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/mob/ZombieVillagerEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;",
      shift = At.Shift.AFTER
    ),
    locals = LocalCapture.CAPTURE_FAILHARD
  )
  public void wasConvertedToZombie(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir, boolean bl, VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
    if(villagerEntity instanceof IWasAZombie iwaz){
      if(zombieVillagerEntity instanceof IWasAZombie converted){
        converted.setSpawnedAsZombie(iwaz.spawnedAsZombie());
      }
    }
  }
}
