package com.dreamtea.music.mixin.mobs;

import com.dreamtea.music.consts;
import com.dreamtea.music.imixin.IPickDiskDrops;
import com.dreamtea.music.util.Utils;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.structure.StructureKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {

  private static float DISK_DROP_RATE = .1f;


  @Inject(at = @At("HEAD"), method = "drop")
  private static void sometimesDropMusicDisk(PiglinEntity piglin, List<ItemStack> items, Vec3d pos, CallbackInfo ci) {
    if(!items.isEmpty()){
      if(piglin instanceof IPickDiskDrops pidd && piglin.world instanceof ServerWorld server && pidd.friendly()) {
        if (Utils.inStructure(server, piglin.getBlockPos(), StructureKeys.BASTION_REMNANT)) {
          for (int i = 0; i < items.size(); i++) {
            if (piglin.getRandom().nextFloat() < DISK_DROP_RATE) {
              items.set(i, consts.BARTER_DISC.get());
            }
          }
        }
      }
    }
  }
}

