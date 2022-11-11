package com.dreamtea.mixin.block_entity;

import com.dreamtea.imixin.IAddToChest;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin implements IAddToChest {

  @Shadow private DefaultedList<ItemStack> inventory;

  @Override
  public void addToInventory(ItemStack stack) {
    List<Integer> slots = new ArrayList<>();
    for(int i = 0; i < inventory.size(); i++){
      if(inventory.get(i).isEmpty()){
        slots.add(i);
      }
    }
    if(!slots.isEmpty()){
      inventory.set(slots.get(Random.create().nextBetween(0, slots.size() - 1)), stack);
    }
  }
}
