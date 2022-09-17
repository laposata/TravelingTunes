package com.dreamtea.datagen;

import com.dreamtea.util.ItemPeeker;
import com.dreamtea.util.LootTablePeeker;
import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.EnchantWithLevelsLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.loot.function.SetPotionLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potions;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.loot.entry.LootPoolEntryTypes.ITEM;

public class MusicLootTables {
  private static final Identifier JUNGLE_TEMPLE_LOOT_TABLE = LootTables.JUNGLE_TEMPLE_CHEST;
  private static final Identifier END_CITY_LOOT_TABLE = LootTables.END_CITY_TREASURE_CHEST;
  private static final Identifier IGLOO_LOOT_TABLE = LootTables.IGLOO_CHEST_CHEST;
  private static final Identifier MINESHAFT_LOOT_TABLE = LootTables.ABANDONED_MINESHAFT_CHEST;
  private static final Identifier OCEAN_RUIN_LOOT_TABLE = LootTables.UNDERWATER_RUIN_BIG_CHEST;
  private static final Identifier PILLAGER_OUTPOST_LOOT_TABLE = LootTables.PILLAGER_OUTPOST_CHEST;
  private static final Identifier CAT_LOOT_TABLE = LootTables.CAT_MORNING_GIFT_GAMEPLAY;
  private static final Identifier PIGLIN_LOOT_TABLE = LootTables.PIGLIN_BARTERING_GAMEPLAY;


  private static final Identifier ANCIENT_CITY_TABLE = LootTables.ANCIENT_CITY_CHEST;
  private static final Identifier SIMPLE_DUNGEON_TABLE = LootTables.SIMPLE_DUNGEON_CHEST;
  private static final Identifier STRONGHOLD_TABLE = LootTables.STRONGHOLD_CORRIDOR_CHEST;
  private static final Identifier WOODLAND_MANSION_TABLE = LootTables.WOODLAND_MANSION_CHEST;

  private static LootPool addItem(Item item, float chance){
    return LootPool.builder().with(ItemEntry.builder(item)).conditionally(RandomChanceLootCondition.builder(chance)).build();
  }

  public static void adding(){
    Map<Identifier, Item> addingToPool = new HashMap<>();
    addingToPool.put(JUNGLE_TEMPLE_LOOT_TABLE, Items.MUSIC_DISC_CHIRP);
    addingToPool.put(CAT_LOOT_TABLE, Items.MUSIC_DISC_CAT);
    addingToPool.put(END_CITY_LOOT_TABLE, Items.MUSIC_DISC_FAR);
    addingToPool.put(PIGLIN_LOOT_TABLE, Items.MUSIC_DISC_MALL);
    addingToPool.put(IGLOO_LOOT_TABLE, Items.MUSIC_DISC_MELLOHI);
    addingToPool.put(MINESHAFT_LOOT_TABLE, Items.MUSIC_DISC_STAL);
    addingToPool.put(OCEAN_RUIN_LOOT_TABLE, Items.MUSIC_DISC_STRAD);
    addingToPool.put(PILLAGER_OUTPOST_LOOT_TABLE, Items.MUSIC_DISC_WARD);

    Map<Identifier, Float> oddsInPool = new HashMap<>();
    oddsInPool.put(JUNGLE_TEMPLE_LOOT_TABLE, .05f);
    oddsInPool.put(CAT_LOOT_TABLE, .1f);
    oddsInPool.put(END_CITY_LOOT_TABLE, .1f);
    oddsInPool.put(PIGLIN_LOOT_TABLE, .005f);
    oddsInPool.put(IGLOO_LOOT_TABLE, .1f);
    oddsInPool.put(MINESHAFT_LOOT_TABLE, .01f);
    oddsInPool.put(OCEAN_RUIN_LOOT_TABLE, .05f);
    oddsInPool.put(PILLAGER_OUTPOST_LOOT_TABLE, .05f);

    LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
      Item loot;
      if (addingToPool.size() > 0 && source.isBuiltin() && (loot = addingToPool.remove(id)) != null) {
        tableBuilder.pool(addItem(loot, oddsInPool.get(id)));
      }
    });
  }

  public static void removing() {
    Map<Identifier, Item> removeExcept = new HashMap<>();
    removeExcept.put(ANCIENT_CITY_TABLE, null);
    removeExcept.put(SIMPLE_DUNGEON_TABLE, Items.MUSIC_DISC_13);
    removeExcept.put(STRONGHOLD_TABLE, null);
    removeExcept.put(WOODLAND_MANSION_TABLE, null);

    LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
      Item loot;
      LootTablePeeker musicDiskPeeker;
      ItemPeeker exceptionPeeker;
      if (removeExcept.size() > 0 && source.isBuiltin() && (loot = removeExcept.remove(id)) != null) {
        for(int i = 0; i < original.pools.length; i++){
          for(int n = 0; n < original.pools[i].entries.length; n++){
            LootPoolEntry item = original.pools[i].entries[n];
            if(item.getType().equals(ITEM)){
              ItemEntry itemEntry = (ItemEntry) item;
              musicDiskPeeker = new LootTablePeeker(ItemTags.MUSIC_DISCS);
              itemEntry.generateLoot(musicDiskPeeker, null);
              if(musicDiskPeeker.result()) {
                exceptionPeeker = new ItemPeeker(loot);
                itemEntry.generateLoot(exceptionPeeker, null);
                if (!exceptionPeeker.result()) {
                  original.pools[i].entries[n] = EmptyEntry.builder().weight(0).build();
                }
              }
            }
          }
        }
      }
      return null;
    });
  }

}
