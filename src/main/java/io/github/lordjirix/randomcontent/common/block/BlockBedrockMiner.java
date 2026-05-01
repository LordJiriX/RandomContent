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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockBedrockMiner extends Block implements EntityBlock {
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

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

  // TODO: remove (need impl in entity)
  @Override
  public void onPlace(
      BlockState p_60566_, Level world, BlockPos blockPos, BlockState p_60569_, boolean p_60570_) {
    if (world.isClientSide) {
      return;
    }
    BlockEntity blockEntity = world.getBlockEntity(blockPos);
    if (blockEntity instanceof BedrockMinerBlockEntity te) {
      if (world.getBlockState(blockPos.offset(0, -1, 0)).getBlock() != Blocks.BEDROCK) {
        return;
      }
      te.setValid(true);
      te.setChanged();
    }
    super.onPlace(p_60566_, world, blockPos, p_60569_, p_60570_);
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

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(FACING);
    super.createBlockStateDefinition(pBuilder);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext ctx) {
    return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
  }
}
