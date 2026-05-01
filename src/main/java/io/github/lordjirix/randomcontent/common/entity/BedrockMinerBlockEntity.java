package io.github.lordjirix.randomcontent.common.entity;

import io.github.lordjirix.randomcontent.Config;
import io.github.lordjirix.randomcontent.gui.menu.BedrockMinerMenu;
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
import net.minecraft.world.level.block.Blocks;
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

public class BedrockMinerBlockEntity extends BlockEntity implements MenuProvider {

  public boolean isValid;
  public int workTime = Config.bedrockMinerWorkTime;
  public int timeToFinish = workTime;
  private final ItemStackHandler inventory =
      new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
          setChanged();
        }
      };
  private int energy_rf_storage = Config.bedrockMinerRfUsage * 1000;
  private final EnergyStorage energy =
      new EnergyStorage(energy_rf_storage, energy_rf_storage / 10, energy_rf_storage / 10) {};
  private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);
  private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> inventory);

  public BedrockMinerBlockEntity(BlockPos pos, BlockState state) {
    super(RCBlockEntitys.BEDROCK_MINER_BLOCK_ENTITY.get(), pos, state);
  }

  @Override
  public Component getDisplayName() {
    return Component.literal("Bedrock Miner");
  }

  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
    return new SimpleOneMenu(id, inv, this.inventory);
  }

  public IItemHandler getInventory() {
    return inventory;
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
  }

  public void tick() {
    if (level == null || level.isClientSide) return;
    if (level.getBlockState(worldPosition.below()).getBlock() != Blocks.BEDROCK) {
      return;
    }
    setValid(true);
    if (energy.getEnergyStored() <= Config.bedrockMinerRfUsage) {
      return;
    }
    timeToFinish--;
    energy.extractEnergy(Config.bedrockMinerRfUsage, false);
    setChanged();
    if (getTimeToFinish() <= 0) {
      inventory.insertItem(0, new ItemStack(RCItems.BEDROCKIUM_DUST.get()), false);
      reset();
      setChanged();
      level.setBlockAndUpdate(worldPosition.below(), Blocks.AIR.defaultBlockState());
      return;
    }
    return;
  }

  @Override
  protected void saveAdditional(CompoundTag tag) {
    tag.putInt("timeToFinish", timeToFinish);
    tag.putInt("workTime", workTime);
    tag.putBoolean("isValid", isValid);
    tag.put("inventory", inventory.serializeNBT());
    super.saveAdditional(tag);
  }

  @Override
  public CompoundTag getUpdateTag() {
    return saveWithoutMetadata();
  }

  @Override
  public void load(CompoundTag tag) {
    timeToFinish = tag.getInt("timeToFinish");
    workTime = tag.getInt("workTime");
    isValid = tag.getBoolean("isValid");
    inventory.deserializeNBT(tag.getCompound("inventory"));
    super.load(tag);
  }

  public boolean setValid(boolean bol) {
    return isValid = bol;
  }

  public int getTimeToFinish() {
    return timeToFinish;
  }

  public void reset() {
    isValid = false;
    timeToFinish = workTime;
  }
}
