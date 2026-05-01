package io.github.lordjirix.randomcontent.compact.jade.provider;

import io.github.lordjirix.randomcontent.Config;
import io.github.lordjirix.randomcontent.common.entity.BedrockMinerBlockEntity;
import io.github.lordjirix.randomcontent.compact.jade.RandomcontentJadePlugin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

public enum BedrockMinerComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("workTime")) {
            if (!accessor.getServerData().getBoolean("isValid")) {
                tooltip.add(Component.literal("Valid: " + accessor.getServerData().getBoolean("isValid")));
                return;
            }
            tooltip.add(Component.literal("Time: " + accessor.getServerData().getInt("timeToFinish") / 20  + "/" + accessor.getServerData().getInt("workTime") / 20 + " s"));
            tooltip.add(Component.literal("RF/t: " + accessor.getServerData().getInt("RFUsage")));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        BedrockMinerBlockEntity furnace = (BedrockMinerBlockEntity) accessor.getBlockEntity();
        data.putInt("workTime", furnace.workTime);
        data.putBoolean("isValid", furnace.isValid);
        data.putInt("timeToFinish", furnace.timeToFinish);
        data.putInt("RFUsage", Config.bedrockMinerRfUsage);
    }

    @Override
    public ResourceLocation getUid() {
        //noinspection removal
        return new ResourceLocation(MODID,"bedrock_miner");
    }
}
