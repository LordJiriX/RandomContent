package io.github.lordjirix.randomcontent.common.entity;

import io.github.lordjirix.randomcontent.Config;
import io.github.lordjirix.randomcontent.api.block.IRecipeRunnable;
import io.github.lordjirix.randomcontent.gui.menu.SimpleOneMenu;
import io.github.lordjirix.randomcontent.loader.RCBlockEntitys;
import io.github.lordjirix.randomcontent.loader.RCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

public class TimeCompressorBlockEntity extends BlockEntity
    implements MenuProvider, IRecipeRunnable {
  public int timeToRunRecipe = Config.timePerTimeNugget;
  public int currentRunTime = timeToRunRecipe;
  public int energyPerTick = Config.timeCompressorRfUsage;
  private final ItemStackHandler inventory =
      new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
          setChanged();
        }
      };
  private final EnergyStorage energy = new EnergyStorage(100000, 10000, 1000) {};
  private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);
  private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> inventory);

  public TimeCompressorBlockEntity(BlockPos pos, BlockState state) {
    super(RCBlockEntitys.TIME_COMPRESSOR_BLOCK_ENTITY_1.get(), pos, state);
  }

  public void tick() {
    if (level == null || level.isClientSide()) return;
    if (inventory.getStackInSlot(0).getCount() == 64) {
      currentRunTime = timeToRunRecipe;
      return;
    }
    if (energy.getEnergyStored() < energyPerTick) return;
    energy.extractEnergy(energyPerTick, false);
    currentRunTime--;
    if (currentRunTime <= 0) {
      currentRunTime = timeToRunRecipe;
      ItemStack output = new ItemStack(RCItems.TIME_NUGGET.get(), 1);
      inventory.insertItem(0, output, false);
      setChanged();
      return;
    }
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
    energyCap.invalidate();
    itemHandler.invalidate();
  }

  @Override
  public Component getDisplayName() {
    return Component.literal("Time Compressor");
  }

  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
    return new SimpleOneMenu(id, inv, this.inventory);
  }

  @Override
  protected void saveAdditional(CompoundTag pTag) {
    pTag.putInt("timeToRunRecipe", timeToRunRecipe);
    pTag.putInt("currentRunTime", currentRunTime);
    pTag.putInt("energyPerTick", energyPerTick);
    pTag.put("inventory", inventory.serializeNBT());
    super.saveAdditional(pTag);
  }

  @Override
  public void load(CompoundTag pTag) {
    timeToRunRecipe = pTag.getInt("timeToRunRecipe");
    currentRunTime = pTag.getInt("currentRunTime");
    energyPerTick = pTag.getInt("energyPerTick");
    inventory.deserializeNBT(pTag.getCompound("inventory"));
    super.load(pTag);
  }

  @Override
  public int getRFPerTick() {
    return energyPerTick;
  }

  @Override
  public int getTimeToRunRecipe() {
    return timeToRunRecipe;
  }

  @Override
  public int getCurrentRunTime() {
    return currentRunTime;
  }
}
