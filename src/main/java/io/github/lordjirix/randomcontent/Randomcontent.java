package io.github.lordjirix.randomcontent;

import static io.github.lordjirix.randomcontent.loader.RCItems.*;

import com.mojang.logging.LogUtils;
import io.github.lordjirix.randomcontent.gui.screen.BedrockMinerScreen;
import io.github.lordjirix.randomcontent.gui.screen.GreenHouseScreen;
import io.github.lordjirix.randomcontent.loader.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(Randomcontent.MODID)
public class Randomcontent {

  public static final String MODID = "randomcontent";
  public static final String VERSION = "0.0-indev";
  private static final Logger LOGGER = LogUtils.getLogger();
  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

  public static final RegistryObject<CreativeModeTab> RANDOM_CONTENT_TAB =
      CREATIVE_MODE_TABS.register(
          "RANDOM_CONTENT_TAB".toLowerCase(),
          () ->
              CreativeModeTab.builder()
                  .withTabsBefore(CreativeModeTabs.COMBAT)
                  .icon(() -> MULTI_TOOL.get().getDefaultInstance())
                  .displayItems(
                      (parameters, output) -> {
                        output.accept(MULTI_TOOL.get());
                        RCData.allItemList.forEach(
                            item -> {
                              output.accept(item.get());
                            });
                        RCData.allBlockItemsList.forEach(
                            item -> {
                              output.accept(item.get());
                            });
                      })
                  .build());

  @SuppressWarnings("removal")
  public Randomcontent() {
    @SuppressWarnings("removal")
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    modEventBus.addListener(this::commonSetup);
    BLOCKS.register(modEventBus);
    RCItems.init(modEventBus);
    RCCustom.init(modEventBus);
    RCBlocks.init(modEventBus);
    RCBlockEntitys.init(modEventBus);
    RCMenus.MENUS.register(modEventBus);
    CREATIVE_MODE_TABS.register(modEventBus);
    MinecraftForge.EVENT_BUS.register(this);

    modEventBus.addListener(this::addCreative);

    //noinspection removal
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    LOGGER.info(
        "----------------------------------------------------------------------------------------");
    LOGGER.warn(
        "MODID: " + MODID + ":" + VERSION + " is in INDEV verion expect bugs and missing content!");
    LOGGER.info(
        "----------------------------------------------------------------------------------------");
  }

  private void addCreative(BuildCreativeModeTabContentsEvent event) {}

  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    LOGGER.warn("Server is not tested!!!!");
  }

  @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {}

    @SubscribeEvent
    public static void registerScreens(FMLClientSetupEvent event) {
      event.enqueueWork(
          () -> {
            MenuScreens.register(RCMenus.BEDROCK_MINER.get(), BedrockMinerScreen::new);
            MenuScreens.register(RCMenus.GREEN_HOUSE.get(), GreenHouseScreen::new);
          });
    }
  }
}
