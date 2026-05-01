package io.github.lordjirix.randomcontent;

import io.github.lordjirix.randomcontent.api.data.recipe.GreenHouseRecipe;
import io.github.lordjirix.randomcontent.loader.RCBlocks;
import io.github.lordjirix.randomcontent.loader.RCItems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class RCData {
  // core

  public static final ArrayList<RegistryObject<Item>> allItemList =
      new ArrayList<>(
          List.of(
              RCItems.GAME_MODE_SWAPPER,
              RCItems.GAME_MODE_SWAPPER,
              RCItems.EXAMPLE_LOOTBAG,
              RCItems.BEDROCKIUM_INGOT,
              RCItems.BEDROCKIUM_DRILL,
              RCItems.DIAMOND_DRILL,
              RCItems.LASER_CORE,
              RCItems.PROCESSING_CORE_T1,
              RCItems.PROCESSING_CORE_T2,
              RCItems.BEDROCKIUM_DUST,
              RCItems.STEEL_DUST,
              RCItems.STEEL_INGOT,
              RCItems.IRON_DUST,
              RCItems.SIMPLE_GRINDER));

  public static final ArrayList<RegistryObject<Block>> allBlockItemsList =
      new ArrayList<>(
          List.of(
              RCBlocks.BEDROCK_MINER_BLOCK, RCBlocks.ELEVATOR_BLOCK, RCBlocks.GREENHOUSE_BLOCK));

  // Lootbags data
  public static List<Item> exampleLootbagDrops =
      List.of(Items.COAL, Items.IRON_INGOT, Items.DIAMOND, Items.TORCH);

  // Recipes data
  public static final HashMap<Item, GreenHouseRecipe> greenHouseRecipes =
      new HashMap<>() {
        {
          put(
              Items.OAK_SAPLING,
              new GreenHouseRecipe(
                  new ItemStack[] {
                    new ItemStack(Items.OAK_LOG, 8),
                    new ItemStack(Items.OAK_LEAVES, 4),
                    new ItemStack(Items.APPLE, 1)
                  }));
          put(
              Items.SPRUCE_SAPLING,
              new GreenHouseRecipe(
                  new ItemStack[] {
                    new ItemStack(Items.SPRUCE_LOG, 12), new ItemStack(Items.SPRUCE_LEAVES, 4)
                  }));
          put(
              Items.BIRCH_SAPLING,
              new GreenHouseRecipe(
                  new ItemStack[] {
                    new ItemStack(Items.BIRCH_LOG, 6), new ItemStack(Items.BIRCH_LEAVES, 4)
                  }));
          put(
              Items.JUNGLE_SAPLING,
              new GreenHouseRecipe(
                  new ItemStack[] {
                    new ItemStack(Items.JUNGLE_LOG, 16), new ItemStack(Items.JUNGLE_LEAVES, 8)
                  }));
          put(
              Items.ACACIA_SAPLING,
              new GreenHouseRecipe(
                  new ItemStack[] {
                    new ItemStack(Items.ACACIA_LOG, 8), new ItemStack(Items.ACACIA_LEAVES, 4)
                  }));
          put(
              Items.DARK_OAK_SAPLING,
              new GreenHouseRecipe(
                  new ItemStack[] {
                    new ItemStack(Items.DARK_OAK_LOG, 10), new ItemStack(Items.DARK_OAK_LEAVES, 4)
                  }));
        }
      };
}
