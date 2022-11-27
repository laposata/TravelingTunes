package com.dreamtea.music.datagen;

import com.dreamtea.music.recipes.MusicDiskRecipe;
import com.dreamtea.music.recipes.SpecialDiscRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.function.Consumer;


public class Recipes extends FabricRecipeProvider {
  public Recipes(FabricDataGenerator dataGenerator) {
    super(dataGenerator);
  }

  @Override
  protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
    SpecialDiscRecipe.exportAll(exporter);
    MusicDiskRecipe.exportAll(exporter);
  }

}
