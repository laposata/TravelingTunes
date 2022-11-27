package com.dreamtea.music.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ShardPeeker extends Peeker<ItemStack>{
  private Item target;
  public ShardPeeker(Item item){
    target = item;
  }
  @Override
  public void accept(ItemStack testing) {
    outcome = testing.isOf(target);
  }
}
