package com.dreamtea.imixin;

import net.minecraft.entity.player.PlayerEntity;

public interface IPickDiskDrops {
  public boolean canGiveDisk(PlayerEntity player);
  public boolean friendly();
}
