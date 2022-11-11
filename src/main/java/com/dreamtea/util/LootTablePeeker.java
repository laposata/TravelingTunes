package com.dreamtea.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nonnull;

public class LootTablePeeker extends Peeker<ItemStack> {
  private final TagKey<Item> targets;

  public LootTablePeeker(@Nonnull TagKey<Item> targets) {
    this.targets = targets;
  }

  @Override
  public void accept(ItemStack testing) {
    this.outcome = testing != null &&
      Registry.ITEM.getOrCreateEntry(Registry.ITEM.getKey(testing.getItem()).get()).isIn(targets);
  }
}
