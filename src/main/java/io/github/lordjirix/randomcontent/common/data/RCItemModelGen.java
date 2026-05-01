package io.github.lordjirix.randomcontent.common.data;

import io.github.lordjirix.randomcontent.Randomcontent;
import io.github.lordjirix.randomcontent.loader.RCItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class RCItemModelGen extends ItemModelProvider {

  public RCItemModelGen(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
    super(output, modid, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    simpleItem(RCItems.MULTI_TOOL);
    simpleItem(RCItems.GAME_MODE_SWAPPER);
    simpleItem(RCItems.EXAMPLE_LOOTBAG);
    simpleItem(RCItems.SIMPLE_GRINDER);
    // Materials ++ Parts
    simpleItem(RCItems.BEDROCKIUM_INGOT);
    simpleItem(RCItems.BEDROCKIUM_DRILL);
    simpleItem(RCItems.BEDROCKIUM_DUST);
    simpleItem(RCItems.LASER_CORE);
    simpleItem(RCItems.PROCESSING_CORE_T1);
    simpleItem(RCItems.PROCESSING_CORE_T2);
    simpleItem(RCItems.DIAMOND_DRILL);
    simpleItem(RCItems.STEEL_DUST);
    simpleItem(RCItems.STEEL_INGOT);
    simpleItem(RCItems.IRON_DUST);
  }

  @SuppressWarnings("removal")
  private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
    //noinspection removal
    return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
        .texture(
            "layer0", new ResourceLocation(Randomcontent.MODID, "item/" + item.getId().getPath()));
  }
}
