package com.dreamtea.mixin;

import com.dreamtea.consts;
import com.dreamtea.imixin.IAddToChest;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.DesertWellFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DesertWellFeature.class)
public class DesertWellFeatureMixin {
  @Inject(method = "generate", at = @At("RETURN"))
  void addChest(FeatureContext<DefaultFeatureConfig> context, CallbackInfoReturnable<Boolean> cir){
    if(cir.getReturnValue()){
      StructureWorldAccess structureWorldAccess = context.getWorld();
      BlockPos blockPos = context.getOrigin();
      structureWorldAccess.setBlockState(blockPos.add(0, -3, 0), Blocks.CHEST.getDefaultState(), 2);
      ((IAddToChest)structureWorldAccess.getBlockEntity(blockPos.add(0, -3, 0))).addToInventory(consts.WELL_DISC.get());
    }
  }
}
