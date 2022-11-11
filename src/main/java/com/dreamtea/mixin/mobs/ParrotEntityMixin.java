package com.dreamtea.mixin.mobs;

import com.dreamtea.imixin.IParrotDisc;
import com.dreamtea.mobs.PartyParrot;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParrotEntity.class)
public abstract class ParrotEntityMixin extends TameableShoulderEntity implements IParrotDisc {

  private PartyParrot parrot;

  protected ParrotEntityMixin(EntityType<? extends TameableShoulderEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "<init>", at = @At("RETURN"))
  public void initializeParrot(EntityType entityType, World world, CallbackInfo ci){
    this.parrot = new PartyParrot();
  }

  @Override
  public boolean canDropDisc() {
    return parrot.hasDisc;
  }

  @Override
  public void setDropDisc(boolean dropDisc) {
    this.parrot.hasDisc = dropDisc;
  }

  @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
  public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
    parrot.writeCustomDataToNbt(nbt);
  }

  @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
  public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
    parrot.readCustomDataFromNbt(nbt);
  }

  @Inject(method = "initGoals", at = @At("TAIL"))
  protected void initGoals(CallbackInfo ci) {
    goalSelector.add(1, new PartyParrot.FlyOntoJukebox(this, 1, 8));
  }

}
