package com.dreamtea.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPeeker extends Peeker<ItemStack>{

  private final Item target;

  public ItemPeeker(Item target) {
    this.target = target;
  }

  @Override
  public void accept(ItemStack testing) {
    outcome = testing != null && testing.isOf(target);
  }
}
