package io.github.lordjirix.randomcontent.loader;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.RCData;
import io.github.lordjirix.randomcontent.common.item.ItemGameModeSwapper;
import io.github.lordjirix.randomcontent.common.item.ItemLootBag;
import io.github.lordjirix.randomcontent.common.item.ItemMultiTool;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RCItems {
  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

  public static final RegistryObject<Item> MULTI_TOOL =
      ITEMS.register("multi_tool", () -> new ItemMultiTool(new Item.Properties()));
  public static final RegistryObject<Item> GAME_MODE_SWAPPER =
      ITEMS.register("game_mode_swapper", () -> new ItemGameModeSwapper(new Item.Properties()));
  public static final RegistryObject<Item> EXAMPLE_LOOTBAG =
      ITEMS.register(
          "example_lootbag",
          () -> new ItemLootBag(new Item.Properties(), RCData.exampleLootbagDrops));

  public static final RegistryObject<Item> BEDROCKIUM_DUST =
      ITEMS.register("bedrockium_dust", () -> new Item(new Item.Properties()));
  public static final RegistryObject<Item> BEDROCKIUM_INGOT =
      ITEMS.register("bedrockium_ingot", () -> new Item(new Item.Properties()));
  public static final RegistryObject<Item> BEDROCKIUM_DRILL =
      ITEMS.register("bedrockium_drill", () -> new Item(new Item.Properties()));
  public static final RegistryObject<Item> DIAMOND_DRILL =
      ITEMS.register("diamond_drill", () -> new Item(new Item.Properties()));
  public static final RegistryObject<Item> LASER_CORE =
      ITEMS.register("laser_core", () -> new Item(new Item.Properties()));
  public static final RegistryObject<Item> PROCESSING_CORE_T1 =
      ITEMS.register("processing_core_t1", () -> new Item(new Item.Properties()));
  public static final RegistryObject<Item> PROCESSING_CORE_T2 =
      ITEMS.register("processing_core_t2", () -> new Item(new Item.Properties()));

  public static void init(IEventBus bus) {
    ITEMS.register(bus);
  }
}
