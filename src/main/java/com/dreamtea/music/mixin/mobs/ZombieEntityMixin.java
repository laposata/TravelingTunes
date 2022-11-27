package com.dreamtea.music.mixin.mobs;

import com.dreamtea.music.TravelingTunes;
import com.dreamtea.music.imixin.IWasAZombie;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ZombieEntity.class, priority = 900)
public class ZombieEntityMixin extends HostileEntity {

  protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "onKilledOther", at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/mob/ZombieVillagerEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;",
      shift = At.Shift.AFTER
    ),
    locals = LocalCapture.CAPTURE_FAILHARD
  )
  public void wasConvertedToZombie(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir, boolean bl, VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity){
    TravelingTunes.LOGGER.info("Transforming villager to ZVillager");
    if(villagerEntity instanceof IWasAZombie iwaz){
      if(zombieVillagerEntity instanceof IWasAZombie converted){
        converted.setSpawnedAsZombie(iwaz.spawnedAsZombie());
      }
    }
  }
}
