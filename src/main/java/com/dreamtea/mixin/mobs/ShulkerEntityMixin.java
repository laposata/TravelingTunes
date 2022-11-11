package com.dreamtea.mixin.mobs;

import com.dreamtea.imixin.IDuplicatedShulker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.dreamtea.consts.SHULKER_DISC;


@Mixin(value = ShulkerEntity.class)
public abstract class ShulkerEntityMixin extends GolemEntity implements IDuplicatedShulker {
  @Shadow protected abstract void setColor(DyeColor color);

  @Shadow public abstract @Nullable DyeColor getColor();

  @Shadow @Final protected static TrackedData<Byte> COLOR;
  private static final double VELOCITY_SCALE = 3;
  private static final TrackedData<Boolean> HAS_DISC = DataTracker.registerData(ShulkerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
  private static final String HAS_DISC_KEY = "has_disc";

  protected ShulkerEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "initDataTracker", at = @At("TAIL"))
  private void addDiscToTrackedData(CallbackInfo ci){
    this.dataTracker.startTracking(HAS_DISC, false);
    this.setCanDropDisc(this.random.nextFloat() < .2);
  }

  @Inject(method = "spawnNewShulker", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
  private void duplicateShulkerDropFragment(CallbackInfo ci, Vec3d vec3d, Box box, int i, float f, ShulkerEntity shulkerEntity){
    if(this.world.getDimensionEntry().matchesKey(DimensionTypes.OVERWORLD)){
      ((IDuplicatedShulker)(Object)shulkerEntity).setCanDropDisc(false);
      if(this.canDropDisc()){
        ItemEntity itemEntity = new ItemEntity(this.world, vec3d.getX(), vec3d.getY(), vec3d.getZ(), SHULKER_DISC.get());
        Vec3d direction = this.getPos().subtract(vec3d);
        itemEntity.setVelocity(direction.normalize().multiply(VELOCITY_SCALE));
        this.world.spawnEntity(itemEntity);
        this.setCanDropDisc(false);
      }
    }
  }

  @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
  private void readNbt(NbtCompound nbt, CallbackInfo ci){
    if(nbt.contains(HAS_DISC_KEY)){
      this.dataTracker.set(HAS_DISC, nbt.getBoolean(HAS_DISC_KEY));
    }
  }

  @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
  private void writeNbt(NbtCompound nbt, CallbackInfo ci){
    nbt.putBoolean(HAS_DISC_KEY, this.dataTracker.get(HAS_DISC));
  }

  @Override
  public boolean canDropDisc() {
    return this.dataTracker.get(HAS_DISC);
  }

  @Override
  public void setCanDropDisc(boolean drop) {
    this.dataTracker.set(HAS_DISC, drop);
    if(drop) {
      this.setColor(DyeColor.byId(this.random.nextBetween(0, 15)));
    } else{
      if(this.getColor()!= null){
        this.dataTracker.set(COLOR, (byte)16);
      }
    }

  }
}
