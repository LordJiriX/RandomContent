package io.github.lordjirix.randomcontent.api.gui;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class BaseRCMenu extends AbstractContainerMenu {
  private final ContainerLevelAccess access;
  private final ContainerData data;

  public BaseRCMenu(
      MenuType<?> type, int id, Inventory playerInv, Container container, ContainerData data) {
    super(type, id);
    this.access = ContainerLevelAccess.NULL;
    this.data = data;
    addPlayerInventory(playerInv);
    addPlayerHotbar(playerInv);
    addDataSlots(data);
  }

  public void addPlayerInventory(Inventory playerInv) {
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 9; ++j) {
        this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }
  }

  public void addPlayerHotbar(Inventory playerInv) {
    for (int i = 0; i < 9; ++i) {
      this.addSlot(new Slot(playerInv, i, 8 + i * 18, 142));
    }
  }

  @Override
  public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean stillValid(Player pPlayer) {
    return true;
  }
}
