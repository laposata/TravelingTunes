package com.dreamtea.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.OnKilledCriterion;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.text.Text;

import java.util.function.Consumer;

import static com.dreamtea.consts.KILL_VEX_ACH;
import static com.dreamtea.consts.KILL_VEX_REWARD;

public class Advancements extends FabricAdvancementProvider {
  protected Advancements(FabricDataGenerator dataGenerator) {
    super(dataGenerator);
  }

  @Override
  public void generateAdvancement(Consumer<Advancement> consumer) {
    Advancement.Builder.create()
      .display(
        Items.DISC_FRAGMENT_5,
        Text.of("Kill a Vex"),
        Text.of("First Vex kill gets you a fragment for sure"),
        null,
        AdvancementFrame.CHALLENGE,
        false,
        false,
        true)
      .criterion("kill_vex_crit",  OnKilledCriterion.Conditions.createPlayerKilledEntity(EntityPredicate.Builder.create().type(EntityType.VEX)))
      .rewards(AdvancementRewards.Builder.loot(KILL_VEX_REWARD))
      .build(consumer, KILL_VEX_ACH.toString());
  }
}
