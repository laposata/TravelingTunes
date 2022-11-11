package com.dreamtea;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class CloneLimitGamerule {
  public static final String CLONE_LIMIT_GAMERULE = "discCloneLimit";
  public static GameRules.Key<GameRules.IntRule> CLONE_LIMIT;

  public static void initRule(){
    CLONE_LIMIT = GameRuleRegistry.register(CLONE_LIMIT_GAMERULE, GameRules.Category.PLAYER, GameRuleFactory.createIntRule(7, -1));
  }
}
