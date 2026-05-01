package io.github.lordjirix.randomcontent.common.data;

import io.github.lordjirix.randomcontent.loader.RCBlocks;
import io.github.lordjirix.randomcontent.loader.RCItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.LanguageProvider;

public class RCLangGen extends LanguageProvider {
  public RCLangGen(PackOutput gen, String modid, String locale) {
    super(gen, modid, locale);
  }

  @Override
  protected void addTranslations() {
    // utils
    add("config.jade.plugin_randomcontent.bedrock_miner", "Bedrock Miner");
    add("config.jade.plugin_randomcontent.recipe_runner_base", "RC Recipe Runner (Base)");
    add("itemGroup.randomcontent.random_content_tab", "Random Content");

    // MAIN

    // materials
    add(RCItems.BEDROCKIUM_INGOT.get(), "Bedrockium Ingot");
    add(RCItems.BEDROCKIUM_DUST.get(), "Bedrockium Dust");
    add(RCItems.LASER_CORE.get(), "Laser Core");
    add(RCItems.PROCESSING_CORE_T1.get(), "Processing Core T1");
    add(RCItems.PROCESSING_CORE_T2.get(), "Processing Core T2");
    add(RCItems.BEDROCKIUM_DRILL.get(), "Bedrockium Drill");
    add(RCItems.DIAMOND_DRILL.get(), "Diamond Drill");
    // tools
    add(RCItems.MULTI_TOOL.get(), "Multi Tool");
    // blocks
    add(new ItemStack(RCBlocks.BEDROCK_MINER_BLOCK.get()), "Bedrock Miner");
  }
}
