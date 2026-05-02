package io.github.lordjirix.randomcontent.compact.jade.provider;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.api.block.IRecipeRunnable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum RecipeRunnableProvider
    implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
  INSTANCE;

  @Override
  public void appendTooltip(
      ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
    if (blockAccessor.getServerData().contains("timeToRunRecipe")) {
      int currentRunTime = blockAccessor.getServerData().getInt("currentRunTime");
      int timeToRunRecipe = blockAccessor.getServerData().getInt("timeToRunRecipe");
      if (currentRunTime == timeToRunRecipe) {
        return;
      }
      iTooltip.add(
          Component.literal(
              "Time : "
                  + blockAccessor.getServerData().getInt("currentRunTime") / 20
                  + "/"
                  + blockAccessor.getServerData().getInt("timeToRunRecipe") / 20
                  + " s"));
      iTooltip.add(Component.literal("RF/t: " + blockAccessor.getServerData().getInt("RFPerTick")));
    }
  }

  @Override
  public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
    IRecipeRunnable runnable = (IRecipeRunnable) blockAccessor.getBlockEntity();
    compoundTag.putInt("timeToRunRecipe", runnable.getTimeToRunRecipe());
    compoundTag.putInt("currentRunTime", runnable.getCurrentRunTime());
    compoundTag.putInt("RFPerTick", runnable.getRFPerTick());
  }

  @Override
  public ResourceLocation getUid() {
    return new ResourceLocation(MODID, "recipe_runner_base");
  }
}
