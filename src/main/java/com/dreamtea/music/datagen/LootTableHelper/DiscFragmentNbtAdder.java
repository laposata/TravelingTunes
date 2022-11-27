package com.dreamtea.music.datagen.LootTableHelper;

import com.dreamtea.music.items.DiscShard;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.SetNbtLootFunction;

public class DiscFragmentNbtAdder{
  public static LootTableEntry.Builder<?> getEntry(DiscShard shard){
    return ItemEntry.builder(shard.toStack().getItem())
      .apply(SetNbtLootFunction.builder(shard.toStack().getOrCreateNbt()));
  }
}
