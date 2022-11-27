package com.dreamtea.music.mixin.mobs;

import com.dreamtea.music.imixin.IWasAZombie;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ZombieVillagerEntity.class)
//@Debug(export = true)
public class ZombieVillagerEntityMixin implements IWasAZombie {

  private boolean wasZombie = true;

  @Override
  public boolean spawnedAsZombie() {
    return wasZombie;
  }

  @Override
  public void setSpawnedAsZombie(boolean wasZombie) {
    this.wasZombie = wasZombie;
  }

  @Inject(method = "finishConversion", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
  public void onConvertToVillager(ServerWorld world, CallbackInfo ci, VillagerEntity villagerEntity, Object var3){
    if(villagerEntity instanceof IWasAZombie iwaz){
      iwaz.setSpawnedAsZombie(this.spawnedAsZombie());
    }
  }

  @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
  public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
    nbt.putBoolean(WAS_A_ZOMBIE_KEY, spawnedAsZombie());
  }

  @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
  public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
    this.wasZombie = !nbt.contains(WAS_A_ZOMBIE_KEY) || nbt.getBoolean(WAS_A_ZOMBIE_KEY);
  }
}
