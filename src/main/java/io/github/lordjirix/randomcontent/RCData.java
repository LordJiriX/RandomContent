package io.github.lordjirix.randomcontent;

import io.github.lordjirix.randomcontent.api.data.recipe.GreenHouseRecipe;
import io.github.lordjirix.randomcontent.loader.RCBlocks;
import io.github.lordjirix.randomcontent.loader.RCCustom;
import io.github.lordjirix.randomcontent.loader.RCItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RCData {
    // core

    public static final ArrayList<RegistryObject<Item>> allItemList =
            new ArrayList<>(List.of(
                    RCItems.GAME_MODE_SWAPPER,RCItems.GAME_MODE_SWAPPER,RCItems.EXAMPLE_LOOTBAG,
                    RCItems.BEDROCKIUM_INGOT,RCItems.BEDROCKIUM_DRILL,RCItems.DIAMOND_DRILL,
                    RCItems.LASER_CORE,RCItems.PROCESSING_CORE_T1,RCItems.PROCESSING_CORE_T2,RCItems.BEDROCKIUM_DUST
            ));
    public static final ArrayList<RegistryObject<Block>> allBlockItemsList =
            new ArrayList<>(List.of(
                    RCBlocks.BEDROCK_MINER_BLOCK,RCBlocks.ELEVATOR_BLOCK,RCBlocks.GREENHOUSE_BLOCK
            ));

    // Lootbags data
    public static List<Item> exampleLootbagDrops = List.of(Items.COAL,Items.IRON_INGOT,Items.DIAMOND,Items.TORCH);

    // Recipes data
    public static final HashMap<Item, GreenHouseRecipe> greenHouseRecipes = new HashMap<>(){{
        put(Items.OAK_SAPLING, new GreenHouseRecipe(new ItemStack[]{new ItemStack(Items.OAK_LOG,8),new ItemStack(Items.OAK_LEAVES,4),new ItemStack(Items.APPLE,1),new ItemStack(Items.OAK_SAPLING,2)}));

    }};


}
