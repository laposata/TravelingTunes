package com.dreamtea.music.datagen;

import com.dreamtea.music.consts;
import com.dreamtea.music.util.TagUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.dreamtea.music.TravelingTunes.NAMESPACE;
import static net.minecraft.item.Items.ANCIENT_DEBRIS;
import static net.minecraft.item.Items.NETHERITE_AXE;
import static net.minecraft.item.Items.NETHERITE_BLOCK;
import static net.minecraft.item.Items.NETHERITE_BOOTS;
import static net.minecraft.item.Items.NETHERITE_CHESTPLATE;
import static net.minecraft.item.Items.NETHERITE_HELMET;
import static net.minecraft.item.Items.NETHERITE_HOE;
import static net.minecraft.item.Items.NETHERITE_INGOT;
import static net.minecraft.item.Items.NETHERITE_LEGGINGS;
import static net.minecraft.item.Items.NETHERITE_PICKAXE;
import static net.minecraft.item.Items.NETHERITE_SCRAP;
import static net.minecraft.item.Items.NETHERITE_SHOVEL;
import static net.minecraft.item.Items.NETHERITE_SWORD;

public class Tags extends FabricTagProvider<Item> {

  public static TagKey<Item> FIREPROOF = TagUtils.createItemTag(new Identifier(NAMESPACE, "fireproof"));

  public Tags(FabricDataGenerator dataGenerator) {
    super(dataGenerator, Registry.ITEM);
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
      .add(consts.CREEPER_DISC.get().getItem());
    this.getOrCreateTagBuilder(FIREPROOF)
      .add(Items.DISC_FRAGMENT_5,
        ANCIENT_DEBRIS,
        NETHERITE_INGOT,
        NETHERITE_BLOCK,
        NETHERITE_AXE,
        NETHERITE_BOOTS,
        NETHERITE_CHESTPLATE,
        NETHERITE_HELMET,
        NETHERITE_HOE,
        NETHERITE_LEGGINGS,
        NETHERITE_PICKAXE,
        NETHERITE_SCRAP,
        NETHERITE_SHOVEL,
        NETHERITE_SWORD);
  }
}
