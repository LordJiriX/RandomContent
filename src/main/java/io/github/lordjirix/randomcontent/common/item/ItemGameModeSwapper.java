package io.github.lordjirix.randomcontent.common.item;

import io.github.lordjirix.randomcontent.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class ItemGameModeSwapper extends Item {
  public ItemGameModeSwapper(Properties properties) {
    super(properties);
  }

  /*
   *  TODO
   *   - FIX owner + UUID
   * */

  @Override
  public InteractionResultHolder<ItemStack> use(
      Level world, Player player, InteractionHand p_41434_) {

    if (world.isClientSide) {
      return InteractionResultHolder.pass(player.getItemInHand(p_41434_));
    }
    if (player.isShiftKeyDown()) {
      return InteractionResultHolder.pass(player.getItemInHand(p_41434_));
    }
    ServerPlayer serverPlayer = (ServerPlayer) player;
    if (Config.gameModeSwapperOwner) {
      if (isOwner(player, player.getItemInHand(p_41434_))) {
        player.displayClientMessage(Component.literal("You are NOT owner of this item"), true);
        return InteractionResultHolder.pass(player.getItemInHand(p_41434_));
      }
    }
    if (player.isCreative()) {
      serverPlayer.setGameMode(GameType.SURVIVAL);
      player.displayClientMessage(Component.literal("Switched to survival mode"), true);
    } else {
      serverPlayer.setGameMode(GameType.CREATIVE);
      player.displayClientMessage(Component.literal("Switched to creative mode"), true);
    }
    return super.use(world, player, p_41434_);
  }

  @Override
  public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext ctx) {
    Level world = ctx.getLevel();
    Player player = ctx.getPlayer();
    ItemStack is = player.getMainHandItem();
    if (world.isClientSide()) {
      return InteractionResult.SUCCESS;
    }
    CompoundTag tag = is.getOrCreateTag();
    tag.putUUID("ownerID", player.getUUID());

    return super.onItemUseFirst(stack, ctx);
  }

  @Override
  public void onCraftedBy(ItemStack p_41447_, Level p_41448_, Player p_41449_) {
    if (p_41448_.isClientSide()) {
      return;
    }
    CompoundTag tag = p_41449_.getUseItem().getOrCreateTag();
    tag.putUUID("ownerID", p_41449_.getUUID());

    super.onCraftedBy(p_41447_, p_41448_, p_41449_);
  }

  public boolean isOwner(Player player, ItemStack stack) {
    if (!stack.hasTag()) {
      return false;
    }
    CompoundTag tag = stack.getTag();
    if (!tag.contains("ownerID")) {
      return false;
    }
    return tag.getUUID("ownerID").equals(player.getUUID());
  }

  @Override
  public boolean isFoil(ItemStack p_41453_) {
    return true;
  }
}
