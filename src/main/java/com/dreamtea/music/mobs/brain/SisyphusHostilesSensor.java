package com.dreamtea.music.mobs.brain;

import com.dreamtea.music.TravelingTunes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class SisyphusHostilesSensor extends NearestVisibleLivingEntitySensor {
  public static final SensorType<SisyphusHostilesSensor> SISYPHUS_HOSTILES = register();
  private static final HashMap<EntityType<?>, Float> SQUARED_DISTANCES_FOR_DANGER = new HashMap<>();
  static {
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.DROWNED, 8f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.EVOKER, 12.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.HUSK, 8.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.ILLUSIONER, 12.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.PILLAGER, 15.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.RAVAGER, 12.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.VEX, 8.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.VINDICATOR, 10.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.ZOGLIN, 10.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.ZOMBIE, 8.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.ZOMBIE_VILLAGER, 8.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.SKELETON, 12.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.SPIDER, 6.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.CAVE_SPIDER, 6.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.WARDEN, 24.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.STRAY, 12.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.CREEPER, 4.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.ENDERMAN, 10.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.ENDERMITE, 6.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.SILVERFISH, 6.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.PHANTOM, 6.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.ZOMBIFIED_PIGLIN, 6.0f);
    SQUARED_DISTANCES_FOR_DANGER.put(EntityType.GHAST, 12.0f);
  }

  @Override
  protected boolean matches(LivingEntity entity, LivingEntity target) {
    return this.isHostile(target) && this.isCloseEnoughForDanger(entity, target);
  }

  private boolean isCloseEnoughForDanger(LivingEntity villager, LivingEntity target) {
    float f = SQUARED_DISTANCES_FOR_DANGER.get(target.getType());
    return target.squaredDistanceTo(villager) <= (double)(f * f);
  }

  @Override
  protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
    return MemoryModuleType.NEAREST_HOSTILE;
  }

  private boolean isHostile(LivingEntity entity) {
    return SQUARED_DISTANCES_FOR_DANGER.containsKey(entity.getType());
  }

  private static SensorType<SisyphusHostilesSensor> register(){
    return SensorType.register(new Identifier(TravelingTunes.NAMESPACE, "sisyphus_fear").toString(), SisyphusHostilesSensor::new);
  }
}
