package com.dreamtea.music;

import com.dreamtea.music.datagen.LootTablesInsert;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dreamtea.music.datagen.MusicLootTables.removing;

public class TravelingTunes implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String NAMESPACE = "music_travels";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	static{
		removing();
		LootTablesInsert.addToMobs();
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Around the world in 80 songs");
	}
}
