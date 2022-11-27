package com.dreamtea.music.mobs.spawner;

import com.dreamtea.music.mobs.Sisyphus;
import com.dreamtea.music.util.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.structure.StructureKeys;
import net.minecraft.world.spawner.Spawner;

import java.util.HashMap;
import java.util.Map;

public class SisyphusSpawner implements Spawner {

  public static SisyphusSpawner instance = new SisyphusSpawner();
  public static final int SPAWN_ATTEMPTS = 10;
  public static final int PADDING = 6;
  public static final int Y_PADDING = 15;
  private final Map<StructureStart, SisyphusSpawn> ancientCities = new HashMap<>();

  public void insertAlreadySpawned(Sisyphus sisyphus, ServerWorld serverWorld){
    StructureStart prison = Utils.inWhichStructure(serverWorld, sisyphus.getHome(), StructureKeys.ANCIENT_CITY);
    if(ancientCities.get(prison) != null
      && ancientCities.get(prison).sisyphus != null
      && sisyphus.getVillager() == ancientCities.get(prison).sisyphus.getVillager()
    ) return;
    ancientCities.put(prison, new SisyphusSpawn(sisyphus, serverWorld.getTime() - sisyphus.getVillager().age, 0));
  }
  @Override
  public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
    return world.getPlayers()
      .stream()
      .filter(SisyphusSpawner::playerToCheck)
      .map(serverPlayerEntity ->
        spawnAtCity(
          Utils.inWhichStructure(world, serverPlayerEntity.getBlockPos(), StructureKeys.ANCIENT_CITY),
          world,
          serverPlayerEntity
        )
      )
      .reduce(Integer::sum)
      .orElse(0) +
      ancientCities.entrySet().stream()
        .filter(entry -> entry.getValue().respawnable(world))
        .map(entry -> spawnAtCity(entry.getKey(), world, null))
        .reduce(Integer::sum)
        .orElse(0);
  }

  private int spawnAtCity(StructureStart structure, ServerWorld world, ServerPlayerEntity player) {
    SisyphusSpawn lastSpawn;
    if(structure == null || structure.getChildren() == null || structure.getChildren().isEmpty()){
      return 0;
    }
    if((lastSpawn = ancientCities.get(structure)) != null){
      if(!lastSpawn.respawnable(world)){
        return 0;
      }
    }
    if(world.isChunkLoaded(structure.getPos().toLong())){
      BlockPos spawnPos = findSpawn(structure, world, player);
      SisyphusSpawn result = SisyphusSpawn.spawn(world, lastSpawn, spawnPos);
      if(result.sisyphus != null){
        result.sisyphus.setHome(structure);
      }
      ancientCities.put(structure, result);
      return result.failedAttempts == 0 ? 1: 0;
    }
    return 0;
  }

  private BlockPos findSpawn(StructureStart start, ServerWorld world, ServerPlayerEntity player){
    BlockPos acceptableSpot = null;
    BlockPos blockPos;
    Random random = world.random;
    BlockBox box = start.getBoundingBox();
    int x,y, yMax,z;
    for(int i = 0; i < SPAWN_ATTEMPTS; i ++){
      x = box.getMinX() + PADDING + random.nextInt(box.getBlockCountX() - PADDING);
      z = box.getMinZ() + PADDING + random.nextInt(box.getBlockCountZ() - PADDING);
      yMax = box.getMaxY() - random.nextInt(box.getBlockCountY() - Y_PADDING);
      for(y = box.getMinY(); y < yMax; y ++){
        blockPos = new BlockPos(x,y,z);
        if(SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, blockPos, EntityType.VILLAGER)){
          if(player == null || player.getBlockPos().getManhattanDistance(new Vec3i(x,y,z)) > 12){
            if(Utils.inStructurePiece(blockPos, start)){
              return blockPos;
            }
            acceptableSpot = blockPos;
          }
        }
      }
    }
    return acceptableSpot;
  }

  public record SisyphusSpawn(Sisyphus sisyphus, long spawnedAt, int failedAttempts){
    private static final int SPAWN_COOLDOWN = 20 * 60 * 0 + 10;

    SisyphusSpawn(VillagerEntity villager, ServerWorld world){
      this(new Sisyphus(villager), world.getTime(), 0);
    }

    public boolean respawnable(ServerWorld world){
      return !sisyphus.isAlive() && world.getTime() - spawnedAt > SPAWN_COOLDOWN;
    }

    public static SisyphusSpawn failedSpawnCooldown(ServerWorld world, SisyphusSpawn previous){
      int attemptsSoFar = 0;
      if(previous != null){
        attemptsSoFar = previous.failedAttempts();
      }
      int failedCooldown;
      if(attemptsSoFar < 5){
        failedCooldown = 20 * 60;
      } else if(attemptsSoFar < 10) {
        failedCooldown = 20 * 60 * 5;
      } else if(attemptsSoFar < 25){
        failedCooldown = 20 * 60 * 10;
      } else {
        failedCooldown = SPAWN_COOLDOWN;
      }
      return new SisyphusSpawn(
        null,
        world.getTime() - SPAWN_COOLDOWN + failedCooldown,
        attemptsSoFar + 1
      );
    }

    public static SisyphusSpawn spawn(ServerWorld world, SisyphusSpawn previous, BlockPos pos){
      if(pos == null){
        return failedSpawnCooldown(world, previous);
      }
      VillagerEntity villager = new VillagerEntity(EntityType.VILLAGER, world);
      villager.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
      world.spawnEntity(villager);
      return new SisyphusSpawn(villager, world);
    }

  }

  private static boolean playerToCheck(ServerPlayerEntity player){
    return player.age % (20 * 5) == 0 && player.isAlive() && !player.isSpectator();
  }
}
