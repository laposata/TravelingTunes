package com.dreamtea.music.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

import static net.minecraft.item.ItemStack.DISPLAY_KEY;
import static net.minecraft.item.ItemStack.LORE_KEY;

public class DiskGenerator {
  public static String EDITION_KEY = "DISC_EDITION";
  public static String COPIES_KEY = "COPIES_MADE";

  private static String edition(int copies){
    if(copies == 0){
      return "Original";
    }
    if(copies == 1){
      return "First Edition";
    }
    if(copies == 2){
      return "Second Edition";
    }
    if(copies == 3){
      return "Third Edition";
    }
    int i = copies % 100;
    if(i < 10 || i > 14){
      i = copies % 10;
      if(i == 1){
        return copies + "st Edition";
      }
      if(i == 2){
        return copies + "nd Edition";
      }
      if(i == 3){
        return copies + "rd Edition";
      }
    }
    return copies + "th Edition";
  }

  public static ItemStack cloneDisk(ItemStack stack, int edInc, int copInc){
    int edition = edInc;
    int copies = copInc;
    if(stack.getOrCreateNbt().contains(EDITION_KEY)){
      edition += stack.getNbt().getInt(EDITION_KEY);
    }
    if(stack.getOrCreateNbt().contains(COPIES_KEY)){
      copies += stack.getNbt().getInt(COPIES_KEY);
    }
    ItemStack result = new ItemStack(stack.getItem());
    NbtCompound nbt = result.getOrCreateNbt();
    NbtCompound display = new NbtCompound();
    NbtList nbtList = new NbtList();
    display.put(LORE_KEY, nbtList);

    nbt.putInt(EDITION_KEY, edition );
    nbt.putInt(COPIES_KEY, copies);
    nbt.put(DISPLAY_KEY, display);

    nbtList.add(toLore(edition(edition)));
    if(copies == 1){
      nbtList.add(toLore(copies + " copy made"));
    }
    if(copies > 1){
      nbtList.add(toLore(copies + " copies made"));
    }
    return result;

  }

  private static NbtString toLore(String string){
    Text result = Text.of(string);
    return NbtString.of(
      Text.Serializer.toJson(
        result
      )
    );
  }
}
