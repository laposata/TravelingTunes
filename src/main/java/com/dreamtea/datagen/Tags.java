package com.dreamtea.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class Tags extends FabricTagProvider {
  /**
   * Construct a new {@link net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider} with the default computed path.
   *
   * <p>Common implementations of this class are provided. For example @see BlockTagProvider
   *
   * @param dataGenerator The data generator instance
   * @param registry      The backing registry for the Tag type.
   */
  public Tags(FabricDataGenerator dataGenerator, Registry registry) {
    super(dataGenerator, registry);
  }

  @Override
  protected void generateTags() {
    this.getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).setReplace(true)
      .add(Items.MUSIC_DISC_PIGSTEP,
        Items.MUSIC_DISC_OTHERSIDE,
        Items.MUSIC_DISC_5,
        Items.MUSIC_DISC_13,
        Items.MUSIC_DISC_CAT,
        Items.MUSIC_DISC_BLOCKS,
        Items.MUSIC_DISC_CHIRP,
        Items.MUSIC_DISC_FAR,
        Items.MUSIC_DISC_MALL,
        Items.MUSIC_DISC_MELLOHI,
        Items.MUSIC_DISC_STAL,
        Items.MUSIC_DISC_STRAD,
        Items.MUSIC_DISC_WARD,
        Items.MUSIC_DISC_11,
        Items.MUSIC_DISC_WAIT
      );
    this.getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS).setReplace(true)
      .add(Items.MUSIC_DISC_11);
  }
}
