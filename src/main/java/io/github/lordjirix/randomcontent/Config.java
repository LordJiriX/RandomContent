package io.github.lordjirix.randomcontent;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

// An example config class. This is not required, but it's a good idea to have one to keep your
// config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Randomcontent.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
  private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

  // private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER.comment("Whether to
  // log the dirt block on common setup").define("logDirtBlock", true);

  // private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER.comment("A magic
  // number").defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

  private static final ForgeConfigSpec.IntValue BEDROCK_MINER_RF_USAGE =
      BUILDER
          .comment("A amout of RF/t used while Bedrock Miner work")
          .defineInRange("bedrock_miner_RFt", 120, 20, Integer.MAX_VALUE);

  private static final ForgeConfigSpec.IntValue BEDROCK_MINER_WORK_TIME =
      BUILDER
          .comment("Time (in ticks) to mine a bedrock")
          .defineInRange("bedrock_miner_time", 4800, 20, Integer.MAX_VALUE);

  // public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION =
  // BUILDER.comment("What you want the introduction message to be for the magic
  // number").define("magicNumberIntroduction", "The magic number is... ");

  private static final ForgeConfigSpec.IntValue GREEN_HOUSE_RF_USAGE =
      BUILDER
          .comment("A amout of RF/t used while Green House work")
          .defineInRange("green_house_RFt", 60, 20, Integer.MAX_VALUE);
  private static final ForgeConfigSpec.IntValue GREEN_HOUSE_GROW_TIME =
      BUILDER
          .comment("Time (in ticks) to grow a crop in Green House")
          .defineInRange("green_house_time", 2400, 20, Integer.MAX_VALUE);

  public static final ForgeConfigSpec.BooleanValue GAME_MODE_SWAPPER_OWNER =
      BUILDER
          .comment("Whether the game mode swapper should check for ownership")
          .define("gameModeSwapperOwner", false);
  // a list of strings that are treated as resource locations for items
  // private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS =
  // BUILDER.comment("A list of items to log on common setup.").defineListAllowEmpty("items",
  // List.of("minecraft:iron_ingot"), Config::validateItemName);

  static final ForgeConfigSpec SPEC = BUILDER.build();

  // public static boolean logDirtBlock;
  // public static int magicNumber;
  // public static String magicNumberIntroduction;
  // public static Set<Item> items;
  public static int bedrockMinerRfUsage;
  public static boolean gameModeSwapperOwner;
  public static int bedrockMinerWorkTime;
  public static int greenHouseRfUsage;
  public static int greenHouseGrowTime;

  private static boolean validateItemName(final Object obj) {
    return obj instanceof final String itemName
        && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
  }

  @SubscribeEvent
  static void onLoad(final ModConfigEvent event) {
    // logDirtBlock = LOG_DIRT_BLOCK.get();
    // magicNumber = MAGIC_NUMBER.get();
    // magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

    // convert the list of strings into a set of items
    // items = ITEM_STRINGS.get().stream().map(itemName -> ForgeRegistries.ITEMS.getValue(new
    // ResourceLocation(itemName))).collect(Collectors.toSet());
    bedrockMinerRfUsage = BEDROCK_MINER_RF_USAGE.get();
    gameModeSwapperOwner = GAME_MODE_SWAPPER_OWNER.get();
    bedrockMinerWorkTime = BEDROCK_MINER_WORK_TIME.get();
    greenHouseRfUsage = GREEN_HOUSE_RF_USAGE.get();
    greenHouseGrowTime = GREEN_HOUSE_GROW_TIME.get();
  }
}
