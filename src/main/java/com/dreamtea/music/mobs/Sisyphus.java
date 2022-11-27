package com.dreamtea.music.mobs;


import com.dreamtea.music.TravelingTunes;
import com.dreamtea.music.imixin.IAmSisyphus;
import com.dreamtea.music.mobs.spawner.SisyphusSpawner;
import com.dreamtea.music.util.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LargeEntitySpawnHelper;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureKeys;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import static com.dreamtea.music.consts.TRAPPED_DISC;

public class Sisyphus {
  public static final int TIME_OF_HUNT = 20 * 60 * 1;
  public static final int TIME_OF_AFRAID = 20 * 30;
  public static final int PANIC_COOLDOWN = 20 * 10;
  public static final int SKYS_LEVEL = 128;
  public static final String SISYPHUS_KEY = "sisyphus";
  public static final String UNTIL_FOUND_KEY = "until_found";
  public static final String FOUND_COUNT_KEY = "found_count";
  public static final String AFRAID_KEY = "afraid";
  public static final String PANIC_COOLDOWN_KEY = "panic";
  public static final String PRISON_KEY = "prison";
  @NonNull
  private final VillagerEntity villager;
  private int timeUntilFound = TIME_OF_HUNT;
  private int afraid = -1;
  private int panic = -1;
  private int found = 0;

  private BlockPos prison;

  public Sisyphus(@NotNull VillagerEntity villager){
    this.villager = villager;
    if(villager instanceof IAmSisyphus ias){
      ias.makeSisyphus(this);
    }
  }

  public void setHome(StructureStart structure){
    if(prison == null && structure.hasChildren()){
      this.prison = structure.getChildren().get(0).getCenter();
      SisyphusSpawner.instance.insertAlreadySpawned(this, this.villager.getServer().getOverworld());
    }

  }


  public BlockPos getHome(){
    return prison;
  }

  public void writeCustomDataToNbt(NbtCompound nbt) {
    NbtCompound sisyphusStuff = new NbtCompound();
    sisyphusStuff.putInt(UNTIL_FOUND_KEY, timeUntilFound);
    sisyphusStuff.putInt(FOUND_COUNT_KEY, found);
    sisyphusStuff.putInt(AFRAID_KEY, afraid);
    sisyphusStuff.putInt(PANIC_COOLDOWN_KEY, panic);
    if(prison != null){
      sisyphusStuff.putLong(PRISON_KEY, prison.asLong());
    }
    nbt.put(SISYPHUS_KEY, sisyphusStuff);
  }

  public static Sisyphus readCustomDataFromNbt(NbtCompound nbt, VillagerEntity villager) {
    if(nbt.contains(SISYPHUS_KEY)){
      Sisyphus sisyphus = new Sisyphus(villager);
      NbtCompound data = nbt.getCompound(SISYPHUS_KEY);
      sisyphus.timeUntilFound = data.contains(UNTIL_FOUND_KEY)? data.getInt(UNTIL_FOUND_KEY): TIME_OF_HUNT;
      sisyphus.afraid = data.getInt(AFRAID_KEY);
      sisyphus.found = data.getInt(FOUND_COUNT_KEY);
      if(nbt.contains(PRISON_KEY)){
        sisyphus.setHome(Utils.inWhichStructure(villager.getServer().getOverworld(), BlockPos.fromLong(nbt.getLong(PRISON_KEY)), StructureKeys.ANCIENT_CITY));
      }
      return sisyphus;
    }
    return null;
  }

  public Sisyphus readCustomDataFromNbt(NbtCompound nbt) {
    if(nbt.contains(SISYPHUS_KEY)){
      NbtCompound data = nbt.getCompound(SISYPHUS_KEY);
      this.timeUntilFound = data.getInt(UNTIL_FOUND_KEY);
      this.afraid = data.getInt(AFRAID_KEY);
      this.found = data.getInt(FOUND_COUNT_KEY);
      this.prison = BlockPos.fromLong(nbt.getLong(PRISON_KEY));
      return this;
    }
    return null;
  }
  public boolean isAlive(){
    return villager.isAlive();
  }
  public @NotNull VillagerEntity getVillager(){
    return villager;
  }

  public void huntTick(ServerWorld world){
    if(!isAlive() || Utils.inStructure(world, villager.getBlockPos(), StructureKeys.ANCIENT_CITY)){
      return;
    }
    if(timeUntilFound > 0){
      timeUntilFound --;
      if(villager.hasVehicle()){
        timeUntilFound -= 2;
      }
      return;
    }
    found ++;
    if(found > 3){
      TravelingTunes.LOGGER.debug("Sisyphus dying from fear");
      villager.damage(DamageSource.MAGIC,  Float.MAX_VALUE);
      return;
    }
    boolean spawned = LargeEntitySpawnHelper.trySpawnAt(EntityType.WARDEN, SpawnReason.TRIGGERED, world, villager.getBlockPos(), 20, 5, 6, LargeEntitySpawnHelper.Requirements.WARDEN).isPresent();
    afraid = TIME_OF_AFRAID * 2;
    if(!spawned){
      timeUntilFound = TIME_OF_AFRAID;
    } else {
      timeUntilFound = TIME_OF_AFRAID * 4;
    }
  }

  public boolean isAfraid(){
    return afraid > 0 || Utils.inStructure(villager.getServer().getOverworld(), villager.getBlockPos(), StructureKeys.ANCIENT_CITY);
  }
  
  public void afraidTick(){
    if(isAfraid()){
      afraid --;
    }
  }

  public void panicTick(ServerWorld world){
    if(panic > 0){
      panic --;
      if(villager.hasVehicle()){
        villager.dismountVehicle();
      }
      return;
    }
    if(villager.getBrain().getMemory(MemoryModuleType.NEAREST_HOSTILE) != 0){
      panic = PANIC_COOLDOWN;
      timeUntilFound -= PANIC_COOLDOWN * 3;
      afraid = TIME_OF_AFRAID;
    }
  }

  public void isFree(ServerWorld world) {
    if(villager.isAlive() && villager.getY() > SKYS_LEVEL && world.isSkyVisible(villager.getBlockPos())) {
      LightningEntity smite = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
      smite.setCosmetic(true);
      smite.setPosition(villager.getPos());
      world.spawnEntity(smite);
      villager.damage(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
      ItemEntity itemEntity = new ItemEntity(world, villager.getX(), villager.getY(), villager.getZ(), TRAPPED_DISC.get());
      world.spawnEntity(itemEntity);
    }
  }
  public void tick(ServerWorld world){
    isFree(world);
    panicTick(world);
    afraidTick();
    huntTick(world);
  }
}
