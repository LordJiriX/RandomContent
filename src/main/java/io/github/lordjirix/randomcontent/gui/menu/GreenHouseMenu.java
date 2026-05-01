package io.github.lordjirix.randomcontent.gui.menu;

import io.github.lordjirix.randomcontent.loader.RCMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GreenHouseMenu extends AbstractContainerMenu {
  private final ItemStackHandler handler;

  public GreenHouseMenu(int id, Inventory inv, FriendlyByteBuf buf) {
    this(id, inv, new ItemStackHandler(10));
  }

  public GreenHouseMenu(int id, Inventory playerInv, ItemStackHandler handler) {
    super(RCMenus.GREEN_HOUSE.get(), id);
    this.handler = handler;
    this.addSlot(new SlotItemHandler(handler, 0, 24, 35));
    this.addSlot(new SlotItemHandler(handler, 1, 62, 35 - 18));
    this.addSlot(new SlotItemHandler(handler, 2, 62, 35));
    this.addSlot(new SlotItemHandler(handler, 3, 62, 53));
    this.addSlot(new SlotItemHandler(handler, 5, 62 + 18, 35));
    this.addSlot(new SlotItemHandler(handler, 8, 62 + 18 + 18, 35));
    this.addSlot(new SlotItemHandler(handler, 4, 62 + 18, 35 - 18));
    this.addSlot(new SlotItemHandler(handler, 7, 62 + 18 + 18, 35 - 18));
    this.addSlot(new SlotItemHandler(handler, 6, 62 + 18, 53));
    this.addSlot(new SlotItemHandler(handler, 9, 62 + 18 + 18, 53));

    addPlayerInventory(playerInv);
    addPlayerHotbar(playerInv);
  }

  private void addPlayerInventory(Inventory inv) {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 9; col++) {
        this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
      }
    }
  }

  private void addPlayerHotbar(Inventory inv) {
    for (int i = 0; i < 9; i++) {
      this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
    }
  }

  @Override
  public boolean stillValid(Player player) {
    return true;
  }

  @Override
  public ItemStack quickMoveStack(Player player, int index) {
    Slot slot = this.slots.get(index);
    if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;
    ItemStack stack = slot.getItem();
    ItemStack copy = stack.copy();
    int machineSlots = 9;
    if (index < machineSlots) {
      if (!this.moveItemStackTo(stack, machineSlots, this.slots.size(), true)) {
        return ItemStack.EMPTY;
      }
    } else {
      if (!this.moveItemStackTo(stack, 0, machineSlots, false)) {
        return ItemStack.EMPTY;
      }
    }
    if (stack.isEmpty()) {
      slot.set(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }
    return copy;
  }
}
