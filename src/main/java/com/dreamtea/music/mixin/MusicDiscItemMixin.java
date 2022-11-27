package com.dreamtea.music.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import org.spongepowered.asm.mixin.Mixin;

import static com.dreamtea.music.util.DiskGenerator.cloneDisk;

@Mixin(MusicDiscItem.class)
public abstract class MusicDiscItemMixin extends Item {
  public MusicDiscItemMixin(Settings settings) {
    super(settings);
  }

  @Override
  public ItemStack getDefaultStack() {
    return cloneDisk(new ItemStack(this),0, 0);
  }
}
