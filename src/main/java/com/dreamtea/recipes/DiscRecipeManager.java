package com.dreamtea.recipes;

import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class DiscRecipeManager extends NbtRecipeManager{
  @Override
  protected Map<Identifier, Recipe<?>> recipeCollection() {
    Map<Identifier, Recipe<?>> recipeMap = new HashMap<>();
    SpecialDiscRecipe.createAll(recipeMap);
    MusicDiskRecipe.createAll(recipeMap);
    return recipeMap;
  }
}
