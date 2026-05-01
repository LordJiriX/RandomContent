package io.github.lordjirix.randomcontent.common.block;

import io.github.lordjirix.randomcontent.Config;
import io.github.lordjirix.randomcontent.common.entity.TimeCompressorBlockEntity;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
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

public class TimeCompressorBlock extends Block implements EntityBlock {

  public TimeCompressorBlock(Properties properties) {
    super(properties);
  }

  @Override
  public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    return pLevel.isClientSide
        ? null
        : (lvl, pos, st, be) -> {
          if (be instanceof TimeCompressorBlockEntity te) {
            te.tick();
          }
        };
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    return new TimeCompressorBlockEntity(pPos, pState);
  }

  @Override
  public InteractionResult use(
      BlockState pState,
      Level pLevel,
      BlockPos pPos,
      Player pPlayer,
      InteractionHand pHand,
      BlockHitResult pHit) {
    if (!pLevel.isClientSide) {
      BlockEntity be = pLevel.getBlockEntity(pPos);
      if (be instanceof MenuProvider provider) {
        NetworkHooks.openScreen((ServerPlayer) pPlayer, provider, pPos);
      }
    }
    return InteractionResult.sidedSuccess(pLevel.isClientSide);
  }

  @Override
  public void appendHoverText(
      ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
    pTooltip.add(Component.literal("Time for solid time!"));
    if (Screen.hasShiftDown()) {
      pTooltip.add(Component.literal("§2Energy: §1" + Config.timeCompressorRfUsage + " §rRF/t§r"));
    }
    pTooltip.add(Component.literal("press §o§lSHIFT§r for more info§r"));
    super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
  }
}
