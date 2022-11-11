package com.dreamtea.recipes;

import com.dreamtea.items.DiscShard;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.dreamtea.TravelingTunes.NAMESPACE;
import static com.dreamtea.consts.CHEST_DISC;
import static com.dreamtea.consts.GIFT_DISC;
import static com.dreamtea.consts.TOTEM_DISC;
import static com.dreamtea.consts.VEX_DISC;
import static com.dreamtea.consts.WANDERING_DISC;

public class MusicDiskRecipe extends AbstractCustomShapedCrafting {
  public static final String group = "music_disc";
  public static Map<String, Supplier<ItemStack>> discs = Map.of(
    "totem_disc", TOTEM_DISC,
    "vex_disc", VEX_DISC,
    "gift_disc", GIFT_DISC,
    "wandering_disc", WANDERING_DISC,
    "chest_disc", CHEST_DISC
  );

  private static List<ItemStack> recipe(ItemStack musicDisc){
    List<ItemStack> recipe = new ArrayList<>();
    DiscShard shard = new DiscShard(musicDisc);
    for(int i = 0; i < 9; i ++){
      recipe.add(shard.toStack());
    }
    return recipe;
  }

  protected MusicDiskRecipe(Identifier id, ItemStack output) {
    super(id, recipe(output), output, 3, 3, group);
  }

  protected boolean checkAtSlot(int i, int j, CraftingInventory inventory){
    int ind = i + j * inventory.getWidth();
    if(ind < 0 || ind >= recipe.size()){
      return true;
    }
    DiscShard ingredient = DiscShard.fromStack(recipe.get(ind));
    DiscShard inv =  DiscShard.fromStack(inventory.getStack(ind));
    return ingredient != null && inv != null && ingredient.sameDisc(inv);
  }

  public static void createAll(Map<Identifier, Recipe<?>> specials){
    discs.forEach((id, disc) ->{
      Identifier identifiers = new Identifier(NAMESPACE, id);
      MusicDiskRecipe mdr = new MusicDiskRecipe(identifiers,disc.get());
      mdr.create(identifiers, specials);
    });
  }

  public static void exportAll(Consumer<RecipeJsonProvider> exporter){
    discs.forEach((id, disc) ->{
      Identifier identifiers = new Identifier(NAMESPACE, id);
      MusicDiskRecipe mdr = new MusicDiskRecipe(identifiers,disc.get());
      mdr.export(identifiers, exporter);
    });
  }
}
