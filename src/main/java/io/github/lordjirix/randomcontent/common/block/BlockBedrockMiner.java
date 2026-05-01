package io.github.lordjirix.randomcontent.common.block;

import io.github.lordjirix.randomcontent.Config;
import io.github.lordjirix.randomcontent.common.entity.BedrockMinerBlockEntity;
import io.github.lordjirix.randomcontent.loader.RCItems;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockBedrockMiner extends Block implements EntityBlock {

  public BlockBedrockMiner(Properties properties) {
    super(properties);
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new BedrockMinerBlockEntity(pos, state);
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      Level level, BlockState state, BlockEntityType<T> type) {
    return level.isClientSide
        ? null
        : (lvl, pos, st, be) -> {
          if (be instanceof BedrockMinerBlockEntity te) {
            te.tick();
          }
        };
  }

  // TODO: make it way better
  @Override
  public void appendHoverText(
      ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
    if (Screen.hasShiftDown()) {
      pTooltip.add(Component.literal("Requires bedrock below to work§r"));
      pTooltip.add(Component.literal("Bedrock -> Bedrockium Dust§r"));
      return;
    }
    pTooltip.add(Component.literal("- can mine bedrock - §r"));
    pTooltip.add(Component.literal("RF usage: " + Config.bedrockMinerRfUsage + " RF/t§r"));
    pTooltip.add(Component.literal("press §oshift§r for more info§r"));
    super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
  }

  @Override
  public void onRemove(
      BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
    if (!level.isClientSide && state.getBlock() != newState.getBlock()) {
      BlockEntity blockEntity = level.getBlockEntity(pos);
      if (blockEntity instanceof BedrockMinerBlockEntity te) {
        if (te.getInventory().getStackInSlot(0).getItem() == RCItems.BEDROCKIUM_DUST.get()) {
          ItemEntity entity =
              new ItemEntity(
                  level,
                  pos.getX(),
                  pos.getY(),
                  pos.getZ(),
                  new ItemStack(RCItems.BEDROCKIUM_DUST.get()));
          level.addFreshEntity(entity);
        }
      }
    }
    super.onRemove(state, level, pos, newState, moved);
  }

  @Override
  public InteractionResult use(
      BlockState state,
      Level level,
      BlockPos pos,
      Player player,
      InteractionHand hand,
      BlockHitResult hit) {
    if (!level.isClientSide) {
      BlockEntity be = level.getBlockEntity(pos);
      if (be instanceof MenuProvider provider) {
        NetworkHooks.openScreen((ServerPlayer) player, provider, pos);
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }
}
