package com.dreamtea.music.util;

import net.minecraft.block.Block;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

public class TagUtils {
    public static TagKey<Item> createItemTag(Identifier tag){
        return TagKey.of(Registry.ITEM_KEY,tag);
    }
    public static boolean itemIsIn(Item block, TagKey<Item> key){
        return Registry.ITEM.getOrCreateEntry(Registry.ITEM.getKey(block).get()).isIn(key);
    }
}
