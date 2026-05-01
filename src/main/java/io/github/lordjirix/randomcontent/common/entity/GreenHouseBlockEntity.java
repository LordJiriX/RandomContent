package io.github.lordjirix.randomcontent.common.entity;

import io.github.lordjirix.randomcontent.RCData;
import io.github.lordjirix.randomcontent.api.block.IRecipeRunnable;
import io.github.lordjirix.randomcontent.gui.menu.GreenHouseMenu;
import io.github.lordjirix.randomcontent.loader.RCBlockEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GreenHouseBlockEntity extends BlockEntity implements IRecipeRunnable, MenuProvider {
  public int timeToRunRecipe = 20 * 10;
  public int currentRunTime = timeToRunRecipe;
  public int energyPerTick = 60;
  private final ItemStackHandler inventory = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
          setChanged();
        }
      };
  private final EnergyStorage energy = new EnergyStorage(100000, 1000, 1000) {};
  private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);
  private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> inventory);

  public GreenHouseBlockEntity(BlockPos pos, BlockState state) {
    super(RCBlockEntitys.GREENHOUSE_BLOCK_ENTITY.get(), pos, state);
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(
      @NotNull Capability<T> cap, @Nullable Direction side) {
    if (cap == ForgeCapabilities.ITEM_HANDLER) {
      return itemHandler.cast();
    }
    if (cap == ForgeCapabilities.ENERGY) {
      return energyCap.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    itemHandler.invalidate();
    energyCap.invalidate();
  }

  public void tick() {
    if (level == null || level.isClientSide()) return;
    if (energy.getEnergyStored() <= energyPerTick) {
      return;
    }
    if (!hasRecipe(inventory.getStackInSlot(0))) {
      currentRunTime = timeToRunRecipe;
      return;
    }
    energy.extractEnergy(energyPerTick, false);
    currentRunTime--;
    if (currentRunTime <= 0) {
        ItemStack[] output = null;
        try {
          output = RCData.greenHouseRecipes.get(inventory.getStackInSlot(0).getItem()).getOutput();
        } catch (Exception e) {}
        for (int i = 0; i < output.length; i++) {
            inventory.insertItem(i + 1, output[i].copy(), false);
        }
      currentRunTime = timeToRunRecipe;
      return;
    }
  }

  @Override
  public void load(CompoundTag pTag) {
    timeToRunRecipe = pTag.getInt("timeToRunRecipe");
    currentRunTime = pTag.getInt("currentRunTime");
    inventory.deserializeNBT(pTag.getCompound("inventory"));
    super.load(pTag);
  }

  @Override
  public CompoundTag getUpdateTag() {
    return saveWithoutMetadata();
  }

  @Override
  protected void saveAdditional(CompoundTag pTag) {
    pTag.putInt("timeToRunRecipe", timeToRunRecipe);
    pTag.putInt("currentRunTime", currentRunTime);
    pTag.put("inventory", inventory.serializeNBT());
    super.saveAdditional(pTag);
  }

  @Override
  public int getCurrentRunTime() {
    return currentRunTime;
  }

  @Override
  public int getTimeToRunRecipe() {
    return timeToRunRecipe;
  }

  @Override
  public Component getDisplayName() {
    return Component.literal("Green House");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
    return new GreenHouseMenu(id, inv, inventory);
  }

  public boolean hasRecipe(ItemStack stack) {
    for (Item in : RCData.greenHouseRecipes.keySet()) {
      if (stack.getItem() == in) {
        return true;
      }
      return false;
    }
    return false;
  }
}
