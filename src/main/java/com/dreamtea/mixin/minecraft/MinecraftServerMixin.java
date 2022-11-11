package com.dreamtea.mixin.minecraft;

import com.dreamtea.mobs.spawner.SisyphusSpawner;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public abstract class MinecraftServerMixin {

  @Shadow @Final @Mutable
  private List<Spawner> spawners;

  @Inject(method = "<init>", at = @At("RETURN"))
  public void addSpawner(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, CallbackInfo ci){
    List<Spawner> s = new ArrayList<>();
    if(this.spawners != null){
      s.addAll(this.spawners);
    }
    s.add(SisyphusSpawner.instance);
    this.spawners = s;
  }

}
