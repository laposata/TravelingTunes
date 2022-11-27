package com.dreamtea.music.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;

public class LootTablePeeker extends Peeker<ItemStack> {

  public LootTablePeeker() {
  }

  @Override
  public void accept(ItemStack testing) {
    this.outcome = testing != null &&
      testing.getItem() instanceof MusicDiscItem;
  }
}
