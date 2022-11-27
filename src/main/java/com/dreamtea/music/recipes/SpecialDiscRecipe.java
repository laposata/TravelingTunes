package com.dreamtea.music.recipes;

import com.dreamtea.music.items.DiscShard;
import com.dreamtea.music.TravelingTunes;
import com.dreamtea.music.consts;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SpecialDiscRecipe extends MusicDiskRecipe {

  private String prop;
  public static final String VILLAGER_ID = "villager_discs";
  public static final String PROF_PROPERTY = "profession";
  public static final String VEX_KILLER_PROPERTY = "killer";

  protected SpecialDiscRecipe(Identifier id, ItemStack output, String prop) {
    super(id, output);
    this.prop = prop;
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

  public boolean matches(CraftingInventory inventory, World world) {
    Set<String> propsUsed = new HashSet<>();
    for(int i = 0; i < inventory.getWidth(); ++i) {
      for(int j = 0; j < inventory.getHeight(); ++j) {
        if (!this.checkAtSlot(i, j, inventory)) {
          return false;
        }
        DiscShard shard = DiscShard.fromStack(inventory.getStack(i + j * inventory.getWidth()));
        if(shard == null || shard.properties() == null
          || shard.properties().get(prop) == null || !propsUsed.add(shard.properties().get(prop))){
          return false;
        }
      }
    }
    return true;
  }

  public static void createAll(Map<Identifier, Recipe<?>> specials){
    Identifier id = new Identifier(TravelingTunes.NAMESPACE, VILLAGER_ID);
    SpecialDiscRecipe sdr = new SpecialDiscRecipe(id, consts.VILLAGERS_DISC.get(), PROF_PROPERTY);
    sdr.create(id, specials);
  }

  public static void exportAll(Consumer<RecipeJsonProvider> exporter){
    Identifier id = new Identifier(TravelingTunes.NAMESPACE, VILLAGER_ID);
    SpecialDiscRecipe sdr = new SpecialDiscRecipe(id, consts.VILLAGERS_DISC.get(), PROF_PROPERTY);
    sdr.export(id, exporter);
  }
}
