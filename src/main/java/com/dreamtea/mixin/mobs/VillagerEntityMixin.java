package com.dreamtea.mixin.mobs;

import com.dreamtea.imixin.IAmSisyphus;
import com.dreamtea.imixin.IWasAZombie;
import com.dreamtea.mobs.Sisyphus;
import com.dreamtea.mobs.brain.SisyphusHostilesSensor;
import com.dreamtea.trade.ShardTrades;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements IWasAZombie, IAmSisyphus {

  private Sisyphus sisyphus;
  private boolean hasSisBrain = false;
  public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
    super(entityType, world);
  }

  @Shadow public abstract VillagerData getVillagerData();

  @Shadow @Final private static ImmutableList<SensorType<? extends Sensor<? super VillagerEntity>>> SENSORS;
  @Shadow @Final private static ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

  @Shadow public abstract void reinitializeBrain(ServerWorld world);

  @Shadow protected abstract Brain.Profile<VillagerEntity> createBrainProfile();

  @Shadow protected abstract void initBrain(Brain<VillagerEntity> brain);

  @Shadow protected abstract Brain<?> deserializeBrain(Dynamic<?> dynamic);

  private static ImmutableList<SensorType<? extends Sensor<? super VillagerEntity>>> SIS_SENSORS;

  private boolean wasZombie = false;

  @Override
  public boolean spawnedAsZombie() {
    return wasZombie;
  }

  @Override
  public void setSpawnedAsZombie(boolean wasZombie) {
    this.wasZombie = wasZombie;
  }

  @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
  public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
    nbt.putBoolean(WAS_A_ZOMBIE_KEY, spawnedAsZombie());
    if(isSisyphus()){
      sisyphus.writeCustomDataToNbt(nbt);
    }
  }

  @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
  public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
    this.wasZombie = nbt.contains(WAS_A_ZOMBIE_KEY) && nbt.getBoolean(WAS_A_ZOMBIE_KEY);
    if(this.sisyphus == null){
      this.sisyphus = Sisyphus.readCustomDataFromNbt(nbt, (VillagerEntity) (Object) this);
    } else {
      this.sisyphus.readCustomDataFromNbt(nbt);
    }
  }

  @Inject(method = "levelUp", at = @At("TAIL"))
  public void onLevelUp(CallbackInfo ci){
    if(getVillagerData().getLevel() == 5){
      if(wasZombie){
        getOffers().add(ShardTrades.getTrade(getVillagerData().getProfession()));
      }
    }
  }

  @Inject(method = "createBrainProfile", at = @At("HEAD"), cancellable = true)
  private void createSisyphusBrain(CallbackInfoReturnable<Brain.Profile<VillagerEntity>> cir){
    if(isSisyphus()){
      cir.setReturnValue(Brain.createProfile(MEMORY_MODULES, SIS_SENSORS));
    }
  }

  @Inject(method = "<clinit>", at = @At("TAIL"))
  private static void memories(CallbackInfo ci){
    ArrayList<SensorType<? extends Sensor<? super VillagerEntity>>> sensors = new ArrayList<>(SENSORS);
    sensors.remove(SensorType.VILLAGER_HOSTILES);
    sensors.add(SisyphusHostilesSensor.SISYPHUS_HOSTILES);
    SIS_SENSORS = sensors.stream().collect(ImmutableList.toImmutableList());
  }


  @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
  public void tick(CallbackInfo ci){
    if(isSisyphus() && world instanceof ServerWorld server){
      super.tick();
      sisyphus.tick(server);
      ci.cancel();
    }
  }


  protected Brain<?> deserializeSisBrain(Dynamic<?> dynamic) {
    Brain<VillagerEntity> brain = createBrainProfile().deserialize(dynamic);
    initBrain(brain);
    return brain;
  }


  public boolean isSisyphus(){
    return sisyphus != null;
  }
  public Sisyphus getSisyphus(){
    return sisyphus;
  }
  public Sisyphus makeSisyphus(Sisyphus sisyphus){
    if(!isSisyphus()){
      this.sisyphus = sisyphus;
      IAmSisyphus.makeSisyphus((VillagerEntity) (Object)this);
      reinitializeBrain(world.getServer().getOverworld());
      NbtOps nbtOps = NbtOps.INSTANCE;
      this.brain = deserializeBrain(new Dynamic<NbtElement>(nbtOps, nbtOps.createMap(ImmutableMap.of(nbtOps.createString("memories"), (NbtElement)nbtOps.emptyMap()))));
    }

    return this.sisyphus;
  }
}
