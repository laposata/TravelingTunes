package com.dreamtea.music.imixin;

import com.dreamtea.music.mobs.Sisyphus;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;

public interface IAmSisyphus {
  boolean isSisyphus();
  Sisyphus getSisyphus();
  Sisyphus makeSisyphus(Sisyphus sisyphus);

  public static void makeSisyphus(VillagerEntity villager){
    villager.setVillagerData(villager.getVillagerData().withLevel(5).withProfession(VillagerProfession.NITWIT).withType(VillagerType.SWAMP));
    villager.getAttributes().setFrom(new AttributeContainer(DefaultAttributeContainer.builder()
      .add(EntityAttributes.GENERIC_MAX_HEALTH, 4f)
      .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6f)
      .build()));
  }
}
