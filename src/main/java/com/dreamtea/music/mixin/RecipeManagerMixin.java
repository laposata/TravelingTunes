package com.dreamtea.music.mixin;

import com.dreamtea.imixin.IRecipeManager;
import com.dreamtea.music.recipes.DiscRecipeManager;
import com.dreamtea.recipes.NbtRecipeManager;
import net.minecraft.recipe.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin implements IRecipeManager {

  public NbtRecipeManager getRecipes() {
    return new DiscRecipeManager();
  }
}
