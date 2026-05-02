package io.github.lordjirix.randomcontent.compact.jade.provider;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.Config;
import io.github.lordjirix.randomcontent.common.entity.BedrockMinerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum BedrockMinerComponentProvider
    implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
  INSTANCE;

  @Override
  public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
    if (accessor.getServerData().contains("timeToRunRecipe")) {
      if (!accessor.getServerData().getBoolean("isValid")) {
        tooltip.add(Component.literal("Valid: " + accessor.getServerData().getBoolean("isValid")));
        return;
      }
      tooltip.add(
          Component.literal(
              "Time: "
                  + accessor.getServerData().getInt("currentRunTime") / 20
                  + "/"
                  + accessor.getServerData().getInt("timeToRunRecipe") / 20
                  + " s"));
      tooltip.add(Component.literal("RF/t: " + accessor.getServerData().getInt("RFUsage")));
    }
  }

  @Override
  public void appendServerData(CompoundTag data, BlockAccessor accessor) {
    BedrockMinerBlockEntity furnace = (BedrockMinerBlockEntity) accessor.getBlockEntity();
    data.putInt("timeToRunRecipe", furnace.timeToRunRecipe);
    data.putBoolean("isValid", furnace.isValid);
    data.putInt("currentRunTime", furnace.currentRunTime);
    data.putInt("RFUsage", Config.bedrockMinerRfUsage);
  }

  @Override
  public ResourceLocation getUid() {
    //noinspection removal
    return new ResourceLocation(MODID, "bedrock_miner");
  }
}
