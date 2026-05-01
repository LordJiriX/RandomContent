package io.github.lordjirix.randomcontent.common.block;

import io.github.lordjirix.randomcontent.loader.RCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockElevator extends Block {
  private int timer = 0;

  public BlockElevator(Properties properties) {
    super(properties);
  }

  @Override
  public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
    if (pLevel.isClientSide) {
      return;
    }
    if (pEntity instanceof Player player) {
      if (player.isShiftKeyDown()) {
        doWarpDown(player, pPos, pLevel);
        return;
      }
      if (player.getY() - pPos.getY() == 0.5 && player.getY() - pPos.getY() <= 1.5) {
        doWarpUp(player, pPos, pLevel);
        return;
      }
    }
    super.stepOn(pLevel, pPos, pState, pEntity);
  }

  public void doWarpUp(Player player, BlockPos pos, Level level) {
    for (int i = 1; i < 256; i++) {
      BlockPos newPos = pos.offset(0, i, 0);
      if (level.getBlockState(newPos).getBlock() == RCBlocks.ELEVATOR_BLOCK.get()) {
        player.teleportTo(player.getX(), newPos.getY() + 1, player.getZ());
        return;
      }
    }
  }

  public void doWarpDown(Player player, BlockPos pos, Level level) {
    for (int i = 1; i < 256; i++) {
      BlockPos newPos = pos.offset(0, -i, 0);
      if (level.getBlockState(newPos).getBlock() == RCBlocks.ELEVATOR_BLOCK.get()) {
        player.teleportTo(player.getX(), newPos.getY() + 1, player.getZ());
        return;
      }
    }
  }
}
