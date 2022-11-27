package com.dreamtea.music;

import com.dreamtea.music.items.DiscShard;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

import static net.minecraft.item.Items.MUSIC_DISC_11;
import static net.minecraft.item.Items.MUSIC_DISC_13;
import static net.minecraft.item.Items.MUSIC_DISC_5;
import static net.minecraft.item.Items.MUSIC_DISC_BLOCKS;
import static net.minecraft.item.Items.MUSIC_DISC_CAT;
import static net.minecraft.item.Items.MUSIC_DISC_CHIRP;
import static net.minecraft.item.Items.MUSIC_DISC_FAR;
import static net.minecraft.item.Items.MUSIC_DISC_MALL;
import static net.minecraft.item.Items.MUSIC_DISC_MELLOHI;
import static net.minecraft.item.Items.MUSIC_DISC_OTHERSIDE;
import static net.minecraft.item.Items.MUSIC_DISC_PIGSTEP;
import static net.minecraft.item.Items.MUSIC_DISC_STAL;
import static net.minecraft.item.Items.MUSIC_DISC_STRAD;
import static net.minecraft.item.Items.MUSIC_DISC_WAIT;
import static net.minecraft.item.Items.MUSIC_DISC_WARD;

public class consts {
  public static Supplier<ItemStack> CHEST_DISC = MUSIC_DISC_13::getDefaultStack;
  public static Supplier<ItemStack> VEX_DISC = MUSIC_DISC_OTHERSIDE::getDefaultStack;
  public static Supplier<ItemStack> WELL_DISC = MUSIC_DISC_BLOCKS::getDefaultStack;
  public static Supplier<ItemStack> PARROT_DISC = MUSIC_DISC_CHIRP::getDefaultStack;
  public static Supplier<ItemStack> SHULKER_DISC = MUSIC_DISC_FAR::getDefaultStack;
  public static Supplier<ItemStack> WANDERING_DISC = MUSIC_DISC_MALL::getDefaultStack;
  public static Supplier<ItemStack> VILLAGERS_DISC = MUSIC_DISC_MELLOHI::getDefaultStack;
  public static Supplier<ItemStack> BLAZE_DISC = MUSIC_DISC_STAL::getDefaultStack;
  public static Supplier<ItemStack> BAT_WOLF_DISC = MUSIC_DISC_STRAD::getDefaultStack;
  public static Supplier<ItemStack> TOTEM_DISC = MUSIC_DISC_WARD::getDefaultStack;
  public static Supplier<ItemStack> CAVE_SPIDER_DISC = MUSIC_DISC_WAIT::getDefaultStack;
  public static Supplier<ItemStack> GIFT_DISC = MUSIC_DISC_CAT::getDefaultStack;
  public static Supplier<ItemStack> TRAPPED_DISC = MUSIC_DISC_5::getDefaultStack;
  public static Supplier<ItemStack> BARTER_DISC = MUSIC_DISC_PIGSTEP::getDefaultStack;
  public static Supplier<ItemStack> CREEPER_DISC = MUSIC_DISC_11::getDefaultStack;

  public static Supplier<DiscShard> VEX_DISC_SHARD = () -> new DiscShard(VEX_DISC.get());
  public static Supplier<DiscShard> GIFT_DISC_SHARD = () -> new DiscShard(GIFT_DISC.get());
  public static Supplier<DiscShard> CHEST_DISC_SHARD = () -> new DiscShard(CHEST_DISC.get());
  public static final Identifier KILL_VEX = new Identifier(TravelingTunes.NAMESPACE, "kill_vex");
  public static final Identifier KILL_VEX_REWARD = new Identifier(TravelingTunes.NAMESPACE, "kill_vex_reward");
  public static final Identifier KILL_VEX_ACH = new Identifier(TravelingTunes.NAMESPACE, "adventure/kill_vex_ach");


  public static final Identifier SISYPHUS_SPAWNER_ID = new Identifier(TravelingTunes.NAMESPACE, "sisyphus_spawner");

}
