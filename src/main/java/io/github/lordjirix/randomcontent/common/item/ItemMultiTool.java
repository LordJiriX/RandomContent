package io.github.lordjirix.randomcontent.common.item;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class ItemMultiTool extends Item {
  public ArrayList<Block> unbreakableBlocks = new ArrayList<>();
  private int mode = 0;

  public ItemMultiTool(Properties properties) {
    super(properties.durability(Integer.MAX_VALUE / 16));

    unbreakableBlocks.add(Blocks.BEDROCK);
    unbreakableBlocks.add(Blocks.COMMAND_BLOCK);
    unbreakableBlocks.add(Blocks.END_PORTAL_FRAME);
  }

  @Override
  public InteractionResult useOn(UseOnContext ctx) {
    Level world = ctx.getLevel();
    Player player = ctx.getPlayer();
    Item item = ctx.getItemInHand().getItem();
    BlockPos blockPos = ctx.getClickedPos();
    Block block = world.getBlockState(ctx.getClickedPos()).getBlock();
    if (world.isClientSide()) {
      return InteractionResult.SUCCESS;
    }
    if (player.isCreative()) {
      removeBlock(world, player, ctx.getClickedPos());
      return InteractionResult.SUCCESS;
    }
    for (Block unbreakableBlock : unbreakableBlocks) {
      if (block == unbreakableBlock) {
        return InteractionResult.FAIL;
      }
    }
    int thisMode = mode;
    if (ctx.getItemInHand().hasTag() && ctx.getItemInHand().getTag().contains("mode")) {
      thisMode = ctx.getItemInHand().getTag().getInt("mode");
    }
    if (thisMode == 1) {
      removeBlock(world, player, ctx.getClickedPos());
      player.getInventory().add(new ItemStack(block));
      return InteractionResult.SUCCESS;
    }
    if (thisMode == 2) {
      removeBlock(world, player, ctx.getClickedPos());
      return InteractionResult.SUCCESS;
    }
    removeBlock(world, player, ctx.getClickedPos());
    ItemEntity drop =
        new ItemEntity(
            world,
            blockPos.getX() + 0.5,
            blockPos.getY() + 0.5,
            blockPos.getZ() + 0.5,
            new ItemStack(block));
    world.addFreshEntity(drop);
    ctx.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(ctx.getHand()));
    return InteractionResult.SUCCESS;
  }

  @Override
  public InteractionResult interactLivingEntity(
      ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
    if (p_41399_.level().isClientSide) {
      return InteractionResult.SUCCESS;
    }
    DamageSource source = p_41400_.level().damageSources().generic();
    p_41400_.hurt(source, Float.POSITIVE_INFINITY);
    return InteractionResult.SUCCESS;
  }

  @Override
  public void appendHoverText(
      ItemStack p_41421_, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {
    tooltip.add(Component.literal("No more tool switching!"));
    tooltip.add(Component.translatable("Mode: " + getMode(p_41421_)));
    tooltip.add(Component.literal("§4Void blocks NBT data!"));
    super.appendHoverText(p_41421_, p_41422_, tooltip, p_41424_);
  }

  public void removeBlock(Level world, Player player, BlockPos blockPos) {
    Block block = world.getBlockState(blockPos).getBlock();
    world.setBlockAndUpdate(
        blockPos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState());
  }

  @Override
  public InteractionResultHolder<ItemStack> use(
      Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
    if (p_41432_.isClientSide()) {
      return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));
    }
    if (p_41433_.isShiftKeyDown()) {
      this.mode++;
      if (mode > 2) {
        mode = 0;
      }
      switch (mode) {
        case 0 -> p_41433_.displayClientMessage(Component.translatable("Mode: Normal"), true);
        case 1 -> p_41433_.displayClientMessage(Component.translatable("Mode: Move Drops"), true);
        case 2 -> p_41433_.displayClientMessage(Component.translatable("Mode: Void Drops"), true);
      }
      ItemStack i = p_41433_.getItemInHand(p_41434_);
      if (i.hasTag()) {
        i.getTag().putInt("mode", mode);
      } else {
        i.setTag(new net.minecraft.nbt.CompoundTag());
        i.getTag().putInt("mode", mode);
      }
    }
    return super.use(p_41432_, p_41433_, p_41434_);
  }

  public String getMode(ItemStack i) {
    int thisMode = mode;
    if (i.hasTag() && i.getTag().contains("mode")) {
      thisMode = i.getTag().getInt("mode");
    }
    return switch (thisMode) {
      case 0 -> "Normal";
      case 1 -> "Move Drops";
      case 2 -> "Void Drops";
      default -> "Unknown";
    };
  }

  @Override
  public boolean isEnchantable(ItemStack pStack) {
    return false;
  }
}
