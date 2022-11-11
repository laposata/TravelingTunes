package com.dreamtea.datagen.LootTableHelper;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

import static com.dreamtea.consts.KILL_VEX_REWARD;
import static com.dreamtea.datagen.LootTablesInsert.VEX_SHARD_LOOT;

public class AdvancementsLootTable extends SimpleFabricLootTableProvider {
  public AdvancementsLootTable(FabricDataGenerator dataGenerator) {
    super(dataGenerator, LootContextTypes.ADVANCEMENT_REWARD);
  }

  @Override
  public void accept(BiConsumer<Identifier, net.minecraft.loot.LootTable.Builder> biConsumer) {
    biConsumer.accept(KILL_VEX_REWARD,
      LootTable.builder()
        .pool(LootPool.builder().with(VEX_SHARD_LOOT).build()));
  }
}
