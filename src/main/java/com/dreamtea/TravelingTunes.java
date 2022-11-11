package com.dreamtea;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dreamtea.consts.SISYPHUS_SPAWNER_ID;
import static com.dreamtea.datagen.LootTablesInsert.addToMobs;
import static com.dreamtea.datagen.MusicLootTables.removing;

public class TravelingTunes implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String NAMESPACE = "music_travels";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	static{
		removing();
		addToMobs();

	}

	@Override
	public void onInitialize() {
	}
}
