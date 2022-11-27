package com.dreamtea.music.items;

import com.dreamtea.item.AlternateItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public record DiscShard(String id, ItemStack musicDisc, String name, Map<String, String> properties) {

  public static final String DISC_FRAG_NBT = "disc_fragment_alt_data";
  public static final String DISC_FRAG_DISC_KEY = "disc_fragment_of";
  public static final String DISC_FRAG_DISC_PROPERTIES = "disc_fragment_props";

  public DiscShard(ItemStack musicDisc, String name){
    this(getId(musicDisc), musicDisc, name, new HashMap<>());
  }

  public DiscShard(ItemStack musicDisc){
    this(getId(musicDisc), musicDisc, getName(musicDisc), new HashMap<>());
  }

  private static String getId(ItemStack musicDisk){
    return String.format("%s_fragment", Registry.ITEM.getId(musicDisk.getItem()));
  }


  private static String getName(ItemStack musicDisk){
    String name;
    if(musicDisk != null){
      if(musicDisk.getItem() instanceof MusicDiscItem musicDiscItem){
        name = musicDiscItem.getDescription().getString();
        return String.format("Fragment of %s", name);
      }
    }
    return "Disc Fragment ";

  }

  public boolean sameDisc(DiscShard shard){
    return shard.musicDisc().isOf(musicDisc.getItem());
  }

  public static DiscShard fromStack(ItemStack itemStack){
    String id;
    ItemStack musicDisk;
    String name;
    Map<String, String> properties = new HashMap<>();
    if(!itemStack.isOf(Items.DISC_FRAGMENT_5)){
      return null;
    }
    if(!AlternateItemStack.hasAlternate(itemStack)){
      return new DiscShard(Items.MUSIC_DISC_5.getDefaultStack(), itemStack.getName().getString());
    }
    AlternateItemStack alt = AlternateItemStack.getFrom(itemStack);
    name = alt.getName().getString();
    id = alt.getIdentifier();
    NbtCompound data = itemStack.getSubNbt(DISC_FRAG_NBT);
    if(data != null){
      musicDisk = Registry.ITEM.get(new Identifier(data.getString(DISC_FRAG_DISC_KEY))).getDefaultStack();
      if(data.contains(DISC_FRAG_DISC_PROPERTIES)){
        NbtCompound props = data.getCompound(DISC_FRAG_DISC_PROPERTIES);
        props.getKeys().forEach(key -> {
          properties.put(key, props.getString(key));
        });
      }
    } else {
      musicDisk = Items.MUSIC_DISC_5.getDefaultStack();
    }
    return new DiscShard(id, musicDisk, name, properties);
  }

  public ItemStack toStack(){
    AlternateItemStack alt = new AlternateItemStack(Items.DISC_FRAGMENT_5, getId(musicDisc));
    alt.name(Text.of(name));
    ItemStack output = alt.create();
    NbtCompound props = new NbtCompound();
    properties.forEach(props::putString);
    NbtCompound discData = new NbtCompound();
    discData.putString(DISC_FRAG_DISC_KEY, Registry.ITEM.getId(musicDisc.getItem()).toString());
    discData.put(DISC_FRAG_DISC_PROPERTIES, props);
    output.getOrCreateNbt().put(DISC_FRAG_NBT, discData);
    return output;
  }

  public void addProperty(String key, String value){
    properties.put(key, value);
  }
}
