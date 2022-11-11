package com.dreamtea.imixin;

import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;

public interface IGetContextLoot {
  ContextLootNbtProvider getProvider(ContextLootNbtProvider.Target target);
}
