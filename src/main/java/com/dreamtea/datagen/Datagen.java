package com.dreamtea.datagen;

import com.dreamtea.TravelingTunes;
import com.dreamtea.datagen.LootTableHelper.AdvancementsLootTable;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static com.dreamtea.datagen.LootTablesInsert.addToMobs;
import static com.dreamtea.datagen.MusicLootTables.removing;

public class Datagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        TravelingTunes.LOGGER.info("Generating data");
        fabricDataGenerator.addProvider(AdvancementsLootTable::new);
        fabricDataGenerator.addProvider(Tags::new);
        fabricDataGenerator.addProvider(Advancements::new);
        fabricDataGenerator.addProvider(Recipes::new);
    }
    static{
        removing();
        addToMobs();
    }
}
