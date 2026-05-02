package io.github.lordjirix.randomcontent.common.data.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class RCTags {
  public class I {
    public static final TagKey<Item> IRON_DUST =
        ItemTags.create(new ResourceLocation("forge", "dusts/iron"));

    public static final TagKey<Item> STEEL_INGOT =
        ItemTags.create(new ResourceLocation("forge", "ingots/steel"));
  }
}
