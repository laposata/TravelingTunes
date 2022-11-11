package com.dreamtea.items;

import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsage;

import java.util.stream.Stream;

import static com.dreamtea.consts.TOTEM_DISC;


public class TotemOfUndying extends Item {

  public TotemOfUndying(Settings settings) {
    super(settings);
  }

  @Override
  public void onItemEntityDestroyed(ItemEntity entity) {
    if(entity.getEntityWorld().getRandom().nextFloat() < .4){
      FluidState fluid = entity.getEntityWorld().getFluidState(entity.getBlockPos());
      if(fluid.isOf(Fluids.FLOWING_LAVA) || fluid.isOf(Fluids.LAVA)){
        ItemUsage.spawnItemContents(entity, Stream.of(new DiscShard(TOTEM_DISC.get()).toStack()));
      }
    }
  }
}
