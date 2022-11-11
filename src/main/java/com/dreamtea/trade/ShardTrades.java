package com.dreamtea.trade;

import com.dreamtea.items.DiscShard;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

import java.util.Iterator;
import java.util.List;

import static com.dreamtea.consts.VILLAGERS_DISC;
import static com.dreamtea.consts.WANDERING_DISC;
import static com.dreamtea.recipes.SpecialDiscRecipe.PROF_PROPERTY;

public class ShardTrades {
  private static DiscShard shardGenerator(VillagerProfession profession){
    DiscShard shard = new DiscShard(VILLAGERS_DISC.get());
    shard.addProperty(PROF_PROPERTY, profession.id());
    return shard;
  }

  private static DiscShard wanderingShard(){
    return new DiscShard(WANDERING_DISC.get());
  }

  private static TradeOffer makeWanderingTrade(Item first, int firstCount){
    ItemStack firstStack = first.getDefaultStack();
    firstStack.setCount(firstCount);
    TradeOffer offer = new TradeOffer(
      firstStack,
      wanderingShard().toStack(),
      1,0, 1f
    );
    return offer;
  }

  private static TradeOffer makeTrade(Item first, int firstCount, VillagerProfession job){
    return  makeTrade(first, firstCount,  Items.AIR, 0, job);
  }

  private static TradeOffer makeTrade(Item first, VillagerProfession job){
    return  makeTrade(first, 1, Items.AIR , 0, job);
  }

  private static TradeOffer makeTrade(Item first, int firstCount, Item second, int secondCount, VillagerProfession job){
    ItemStack firstStack = first.getDefaultStack();
    firstStack.setCount(firstCount);
    ItemStack secondStack = second.getDefaultStack();
    secondStack.setCount(secondCount);
    TradeOffer offer = new TradeOffer(
      firstStack,
      secondStack,
      shardGenerator(job).toStack(),
      1,0, 1f
    );
    offer.setSpecialPrice(firstCount);
    return offer;
  }

  public static TradeOffer getTrade(VillagerProfession profession){
    return switch (profession.id()) {
      case "armorer" -> makeTrade(Items.DIAMOND, 32, VillagerProfession.ARMORER);
      case "butcher" -> makeTrade(Items.RABBIT_FOOT, VillagerProfession.BUTCHER);
      case "cartographer" -> makeTrade(Items.FILLED_MAP, VillagerProfession.CARTOGRAPHER);
      case "cleric" -> makeTrade(Items.ROTTEN_FLESH, 64, Items.ROTTEN_FLESH, 64, VillagerProfession.CLERIC);
      case "farmer" -> makeTrade(Items.POISONOUS_POTATO, 32, VillagerProfession.FARMER);
      case "fisherman" -> makeTrade(Items.PUFFERFISH_BUCKET, 1, Items.PUFFERFISH_BUCKET, 1, VillagerProfession.FISHERMAN);
      case "fletcher" -> makeTrade(Items.DRAGON_BREATH, 16, VillagerProfession.FLETCHER);
      case "leatherworker" -> makeTrade(Items.LEATHER, 64, Items.LEATHER, 64, VillagerProfession.LEATHERWORKER);
      case "librarian" -> makeTrade(Items.INK_SAC, 64, VillagerProfession.LIBRARIAN);
      case "mason" -> makeTrade(Items.DIAMOND_ORE, VillagerProfession.MASON);
      case "shepherd" -> makeTrade(Items.BONE_BLOCK, 64, VillagerProfession.SHEPHERD);
      case "toolsmith" -> makeTrade(Items.IRON_BLOCK, 64, VillagerProfession.TOOLSMITH);
      case "weaponsmith" -> makeTrade(Items.NETHERITE_INGOT, 3, VillagerProfession.WEAPONSMITH);
      default -> null;
    };
  }

  public static TradeOffer getWanderingTrade(Random random){
    List<Pair<Integer, TradeOffer>> trades = List.of(
      Pair.of(5, makeWanderingTrade(Items.TOTEM_OF_UNDYING, 1)),
      Pair.of(5, makeWanderingTrade(Items.TURTLE_HELMET, 1)),
      Pair.of(30, makeWanderingTrade(Items.LILY_OF_THE_VALLEY, 8)),
      Pair.of(30, makeWanderingTrade(Items.CARROT_ON_A_STICK, 1)),
      Pair.of(30, makeWanderingTrade(Items.MUSHROOM_STEM, 1)),
      Pair.of(30, makeWanderingTrade(Items.RABBIT_STEW, 1)),
      Pair.of(30, makeWanderingTrade(Items.FLOWER_BANNER_PATTERN, 1)),
      Pair.of(60, makeWanderingTrade(Items.BEETROOT, 32)),
      Pair.of(60, makeWanderingTrade(Items.CHORUS_FLOWER, 8)),
      Pair.of(100, makeWanderingTrade(Items.FURNACE_MINECART, 1)),
      Pair.of(100, makeWanderingTrade(Items.SWEET_BERRIES, 64)),
      Pair.of(100, makeWanderingTrade(Items.GLOW_BERRIES, 64))
    );
    int range = trades.stream().map(Pair::getFirst).reduce(Integer::sum).get();
    int value = random.nextBetween(0, range);
    Iterator<Pair<Integer, TradeOffer>> iterator = trades.iterator();
    Pair<Integer,TradeOffer> e = null ;
    while(value > 0){
      e = iterator.next();
      value -= e.getFirst();
    }
    return e == null ? null : e.getSecond();
  }
}
