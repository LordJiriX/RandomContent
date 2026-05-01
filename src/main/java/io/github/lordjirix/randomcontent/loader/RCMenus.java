package io.github.lordjirix.randomcontent.loader;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.gui.menu.BedrockMinerMenu;
import io.github.lordjirix.randomcontent.gui.menu.GreenHouseMenu;
import io.github.lordjirix.randomcontent.gui.menu.SimpleOneMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RCMenus {
  public static final DeferredRegister<MenuType<?>> MENUS =
      DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

  public static final RegistryObject<MenuType<BedrockMinerMenu>> BEDROCK_MINER =
      MENUS.register("bedrock_miner", () -> IForgeMenuType.create(BedrockMinerMenu::new));

  public static final RegistryObject<MenuType<GreenHouseMenu>> GREEN_HOUSE =
      MENUS.register("green_house", () -> IForgeMenuType.create(GreenHouseMenu::new));
    public static final RegistryObject<MenuType<SimpleOneMenu>> SIMPLE_ONESLOT_MENU =
            MENUS.register("oneslot_menu", () -> IForgeMenuType.create(SimpleOneMenu::new));
}
