package com.dreamtea.music.datagen;

import com.dreamtea.music.datagen.LootTableHelper.DiscFragmentNbtAdder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static com.dreamtea.music.consts.BAT_WOLF_DISC;
import static com.dreamtea.music.consts.BLAZE_DISC;
import static com.dreamtea.music.consts.CAVE_SPIDER_DISC;
import static com.dreamtea.music.consts.CHEST_DISC_SHARD;
import static com.dreamtea.music.consts.VEX_DISC_SHARD;

public class LootTablesInsert {
  private static final Identifier BLAZE = EntityType.BLAZE.getLootTableId();
  private static final Identifier CAVE_SPIDER = EntityType.CAVE_SPIDER.getLootTableId();
  private static final Identifier BAT = EntityType.BAT.getLootTableId();
  private static final Identifier VEX = EntityType.VEX.getLootTableId();

  public static final LeafEntry.Builder<?> VEX_SHARD_LOOT = DiscFragmentNbtAdder.getEntry(VEX_DISC_SHARD.get());
  public static final LeafEntry.Builder<?> CHEST_SHARD_LOOT = DiscFragmentNbtAdder.getEntry(CHEST_DISC_SHARD.get());

  public static final Map<Identifier, Float> oddsInStructure = new HashMap<>();
  static{
    oddsInStructure.put(LootTables.ABANDONED_MINESHAFT_CHEST, .1f);
    oddsInStructure.put(LootTables.ANCIENT_CITY_CHEST, .3f);
    oddsInStructure.put(LootTables.BASTION_BRIDGE_CHEST, .7f);
    oddsInStructure.put(LootTables.DESERT_PYRAMID_CHEST, .1f);
    oddsInStructure.put(LootTables.SIMPLE_DUNGEON_CHEST, .1f);
    oddsInStructure.put(LootTables.BURIED_TREASURE_CHEST, .2f);
    oddsInStructure.put(LootTables.WOODLAND_MANSION_CHEST, .2f);
    oddsInStructure.put(LootTables.JUNGLE_TEMPLE_CHEST, .4f);
    oddsInStructure.put(LootTables.PILLAGER_OUTPOST_CHEST, .1f);
    oddsInStructure.put(LootTables.RUINED_PORTAL_CHEST, .2f);
    oddsInStructure.put(LootTables.SHIPWRECK_TREASURE_CHEST, .1f);
    oddsInStructure.put(LootTables.STRONGHOLD_LIBRARY_CHEST, .4f);
    oddsInStructure.put(LootTables.STRONGHOLD_CORRIDOR_CHEST, .1f);
    oddsInStructure.put(LootTables.STRONGHOLD_CROSSING_CHEST, .3f);
  }

  private static final LootPool.Builder BLAZE_KILLED_BY_SNOWMAN =
    LootPool.builder()
    .with(ItemEntry.builder(BLAZE_DISC.get().getItem()))
    .conditionally(
      EntityPropertiesLootCondition
        .builder(LootContext.EntityTarget.KILLER
          , EntityPredicate.Builder.create().type(EntityType.SNOW_GOLEM))
    );

  private static final LootPool.Builder CAVE_SPIDER_BY_ENDER_DRAGON =
    LootPool.builder()
      .with(ItemEntry.builder(CAVE_SPIDER_DISC.get().getItem()))
      .conditionally(
        EntityPropertiesLootCondition
          .builder(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.create().type(EntityType.ENDER_DRAGON))
      );

  private static final LootPool.Builder BAT_BY_WOLF =
    LootPool.builder()
      .with(ItemEntry.builder(BAT_WOLF_DISC.get().getItem()))
      .conditionally(
        EntityPropertiesLootCondition.builder(LootContext.EntityTarget.DIRECT_KILLER, EntityPredicate.Builder.create().type(EntityType.WOLF)))
      .conditionally(RandomChanceLootCondition.builder(.1f))
      .conditionally(KilledByPlayerLootCondition.builder());

  private static final LootPool.Builder VEX_BY_PLAYER =
    LootPool.builder()
      .with(VEX_SHARD_LOOT)
      .conditionally(RandomChanceLootCondition.builder(.2f))
      .conditionally(KilledByPlayerLootCondition.builder());

  private static LootPool.Builder ANY_CHEST(Float chance){
    return LootPool.builder()
      .with(CHEST_SHARD_LOOT)
      .conditionally(RandomChanceLootCondition.builder(chance));

  }

  private static void addToTable(ResourceManager resourceManager, LootManager lootManager, Identifier id, LootTable.Builder tableBuilder, LootTableSource source){
    if (BLAZE.equals(id)) {
      tableBuilder.pool(BLAZE_KILLED_BY_SNOWMAN).build();
    }
    if (CAVE_SPIDER.equals(id)) {
      tableBuilder.pool(CAVE_SPIDER_BY_ENDER_DRAGON).build();
    }
    if (BAT.equals(id)) {
      tableBuilder.pool(BAT_BY_WOLF).build();
    }
    if (VEX.equals(id)) {
      tableBuilder.pool(VEX_BY_PLAYER).build();
    }
    if(oddsInStructure.containsKey(id)){
      tableBuilder.pool(ANY_CHEST(oddsInStructure.get(id)));
    }

  }

  public static void addToMobs() {
    LootTableEvents.MODIFY.register( LootTablesInsert::addToTable);
  }
}
