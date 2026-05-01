package io.github.lordjirix.randomcontent.common.block;

import io.github.lordjirix.randomcontent.Config;
import io.github.lordjirix.randomcontent.common.entity.GreenHouseBlockEntity;
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

public class BlockGreenHouse extends Block implements EntityBlock {
  public BlockGreenHouse(Properties properties) {
    super(properties);
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new GreenHouseBlockEntity(pos, state);
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      Level level, BlockState state, BlockEntityType<T> type) {
    return level.isClientSide
        ? null
        : (lvl, pos, st, be) -> {
          if (be instanceof GreenHouseBlockEntity tbe) {
            tbe.tick();
          }
        };
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

  @Override
  public void appendHoverText(
      ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
    pTooltip.add(Component.literal("Tree farm 3000§r"));
    if (Screen.hasShiftDown()) {
      pTooltip.add(Component.literal("§2Energy: §1" + Config.greenHouseRfUsage + " §rRF/t§r"));
      return;
    }
    pTooltip.add(Component.literal("press §o§lSHIFT§r for more info§r"));
    super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
  }
}
