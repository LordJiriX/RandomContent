package io.github.lordjirix.randomcontent.loader;

import io.github.lordjirix.randomcontent.gui.menu.BedrockMinerMenu;
import io.github.lordjirix.randomcontent.gui.menu.GreenHouseMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

public class RCMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<BedrockMinerMenu>> BEDROCK_MINER =
            MENUS.register("bedrock_miner", () ->
                    IForgeMenuType.create(BedrockMinerMenu::new));

    public static final RegistryObject<MenuType<GreenHouseMenu>> GREEN_HOUSE =
            MENUS.register("green_house", () ->
                    IForgeMenuType.create(GreenHouseMenu::new));
}
