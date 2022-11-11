package com.dreamtea.datagen.LootTableHelper;

import com.dreamtea.items.DiscShard;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.function.SetNbtLootFunction;

import static com.dreamtea.items.DiscShard.DISC_FRAG_DISC_PROPERTIES;
import static com.dreamtea.items.DiscShard.DISC_FRAG_NBT;
import static com.dreamtea.recipes.SpecialDiscRecipe.VEX_KILLER_PROPERTY;

public class DiscFragmentNbtAdder{
  public static LootTableEntry.Builder<?> getEntry(DiscShard shard){
    return ItemEntry.builder(shard.toStack().getItem())
      .apply(SetNbtLootFunction.builder(shard.toStack().getOrCreateNbt()))
      .apply(
        CopyNbtLootFunction.builder(LootContext.EntityTarget.KILLER_PLAYER)
          .withOperation("UUID", String.format("%s.%s.%s", DISC_FRAG_NBT, DISC_FRAG_DISC_PROPERTIES, VEX_KILLER_PROPERTY))
      );
  }
}
