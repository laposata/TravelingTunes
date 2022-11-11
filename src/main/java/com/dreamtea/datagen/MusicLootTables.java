package com.dreamtea.datagen;

import com.dreamtea.util.LootTablePeeker;
import com.dreamtea.util.ShardPeeker;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.tag.ItemTags;

import static net.minecraft.loot.entry.LootPoolEntryTypes.ITEM;

public class MusicLootTables {

  public static void removing() {
    LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
      LootTablePeeker musicDiskPeeker;
      ShardPeeker diskShardPeeker;
      if (original.getType().equals(LootContextTypes.CHEST)) {
        for(int i = 0; i < original.pools.length; i++){
          for(int n = 0; n < original.pools[i].entries.length; n++){
            LootPoolEntry item = original.pools[i].entries[n];
            if(item.getType().equals(ITEM)){
              ItemEntry itemEntry = (ItemEntry) item;
              musicDiskPeeker = new LootTablePeeker(ItemTags.MUSIC_DISCS);
              diskShardPeeker = new ShardPeeker(Items.DISC_FRAGMENT_5);
              itemEntry.generateLoot(musicDiskPeeker, null);
              itemEntry.generateLoot(diskShardPeeker, null);
              if(musicDiskPeeker.result() || diskShardPeeker.result()) {
                original.pools[i].entries[n] = EmptyEntry.builder().weight(0).build();
              }
            }
          }
        }
      }
      return null;
    });
  }

}
