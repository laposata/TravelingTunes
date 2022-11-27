package com.dreamtea.music.mixin.mobs;

import com.dreamtea.music.trade.ShardTrades;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity {

  public WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "fillRecipes", at = @At("TAIL"))
  protected void fillRecipes(CallbackInfo ci) {
     this.getOffers().set(3, ShardTrades.getWanderingTrade(this.random));
     this.getOffers().set(4, ShardTrades.getWanderingTrade(this.random));
  }
}
