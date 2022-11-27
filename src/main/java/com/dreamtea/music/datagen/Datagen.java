package com.dreamtea.music.datagen;

import com.dreamtea.music.TravelingTunes;
import com.dreamtea.music.datagen.LootTableHelper.AdvancementsLootTable;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static com.dreamtea.music.datagen.MusicLootTables.removing;

public class Datagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        TravelingTunes.LOGGER.info("Generating data");
        fabricDataGenerator.addProvider(AdvancementsLootTable::new);
        fabricDataGenerator.addProvider(Tags::new);
        fabricDataGenerator.addProvider(Advancements::new);
        fabricDataGenerator.addProvider(Recipes::new);
        removing();
        LootTablesInsert.addToMobs();
    }
    static{
        removing();
        LootTablesInsert.addToMobs();
    }
}
