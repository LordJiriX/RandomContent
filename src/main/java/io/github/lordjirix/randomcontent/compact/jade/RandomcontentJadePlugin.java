package io.github.lordjirix.randomcontent.compact.jade;

import io.github.lordjirix.randomcontent.common.block.BlockBedrockMiner;
import io.github.lordjirix.randomcontent.common.block.BlockGreenHouse;
import io.github.lordjirix.randomcontent.common.block.TimeCompressorBlock;
import io.github.lordjirix.randomcontent.common.entity.BedrockMinerBlockEntity;
import io.github.lordjirix.randomcontent.common.entity.GreenHouseBlockEntity;
import io.github.lordjirix.randomcontent.common.entity.TimeCompressorBlockEntity;
import io.github.lordjirix.randomcontent.compact.jade.provider.BedrockMinerComponentProvider;
import io.github.lordjirix.randomcontent.compact.jade.provider.RecipeRunnableProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class RandomcontentJadePlugin implements IWailaPlugin {
  @Override
  public void register(IWailaCommonRegistration registration) {
    registration.registerBlockDataProvider(
        BedrockMinerComponentProvider.INSTANCE, BedrockMinerBlockEntity.class);
    registration.registerBlockDataProvider(
        RecipeRunnableProvider.INSTANCE, GreenHouseBlockEntity.class);
    registration.registerBlockDataProvider(
        RecipeRunnableProvider.INSTANCE, TimeCompressorBlockEntity.class);
    // TODO register data providers
  }

  @Override
  public void registerClient(IWailaClientRegistration registration) {
    registration.registerBlockComponent(
        BedrockMinerComponentProvider.INSTANCE, BlockBedrockMiner.class);
    registration.registerBlockComponent(RecipeRunnableProvider.INSTANCE, BlockGreenHouse.class);
    registration.registerBlockComponent(RecipeRunnableProvider.INSTANCE, TimeCompressorBlock.class);
    // TODO register component providers, icon providers, callbacks, and config options here
  }
}
