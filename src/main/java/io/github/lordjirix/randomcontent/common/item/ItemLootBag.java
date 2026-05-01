package io.github.lordjirix.randomcontent.common.item;

import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ItemLootBag extends Item {
  // TODO add chance data (in %)
  private List<Item> drops;

  public ItemLootBag(Properties properties, List<Item> drops) {
    super(properties);
    this.drops = drops;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
    if (world.isClientSide) {
      return InteractionResultHolder.success(player.getItemInHand(hand));
    }
    if (player.isShiftKeyDown()) {
      return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
    Random rand = new Random();
    int num = rand.nextInt(drops.size() + 1);
    for (int i = 0; i < drops.size(); i++) {
      if (i == num) {
        addItem(player, new ItemStack(drops.get(i)));
        return InteractionResultHolder.success(player.getItemInHand(hand));
      }
    }
    return InteractionResultHolder.pass(player.getItemInHand(hand));
  }

  @Override
  public void appendHoverText(
      ItemStack p_41421_,
      @Nullable Level p_41422_,
      List<Component> p_41423_,
      TooltipFlag p_41424_) {
    if (!Screen.hasShiftDown()) {
      p_41423_.add(Component.literal("press SHIFT for drops"));
      return;
    }
    p_41423_.add(Component.literal("Drops: "));
    for (Item drop : drops) {
      p_41423_.add(Component.literal(" - " + formItemName(drop.toString())));
    }
    super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
  }

  public void addItem(Player p, ItemStack i) {
    if (!p.isCreative()) {
      addItem2(p, i);
      // p.displayClientMessage(Component.literal("Dropped: " + formItemName(i.toString())),true);
      remItem(p, p.getItemInHand(p.getUsedItemHand()));
      return;
    }
    addItem2(p, i);
    // p.displayClientMessage(Component.literal("Dropped: " + formItemName(i.toString())),true);
  }

  public void remItem(Player p, ItemStack i) {
    i.shrink(1);
  }

  public void addItem2(Player p, ItemStack i) {
    Level world = p.level();
    boolean full = p.getInventory().add(i);
    if (!full) {
      ItemEntity itemEntity = new ItemEntity(world, p.getX(), p.getY(), p.getZ(), i);
      world.addFreshEntity(itemEntity);
    }
  }

  // TODO you know what about this
  public String formItemName(String in) {
    in = in.replace("_", " ");
    String[] parts = in.split(" ");
    String out = "";
    for (String part : parts) {
      String s = String.valueOf(part.charAt(0));
      part = part.replace(s, "");
      s = s.toUpperCase();
      String main = s + part;
      out = out + " " + main;
    }
    return out;
  }
}
