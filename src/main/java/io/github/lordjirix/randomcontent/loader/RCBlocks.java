package io.github.lordjirix.randomcontent.loader;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.common.block.BlockBedrockMiner;
import io.github.lordjirix.randomcontent.common.block.BlockElevator;
import io.github.lordjirix.randomcontent.common.block.BlockGreenHouse;
import io.github.lordjirix.randomcontent.common.block.TimeCompressorBlock;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RCBlocks {

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

  public static final RegistryObject<Block> BEDROCK_MINER_BLOCK =
      createBlock(
          "bedrock_miner_block",
          () -> new BlockBedrockMiner(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
  public static final RegistryObject<Block> GREENHOUSE_BLOCK =
      createBlock(
          "greenhouse_block",
          () -> new BlockGreenHouse(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
  public static final RegistryObject<Block> ELEVATOR_BLOCK =
      createBlock(
          "elevator_block",
          () -> new BlockElevator(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
  public static final RegistryObject<Block> TIME_COMPRESSOR_BLOCK =
      createBlock(
          "time_compressor_block",
          () -> new TimeCompressorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

  private static <T extends Block> RegistryObject<T> createBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    createBlockItem(name, toReturn);
    return toReturn;
  }

  private static <T extends Block> RegistryObject<Item> createBlockItem(
      String name, RegistryObject<T> block) {
    return RCItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
  }

  public static void init(IEventBus bus) {
    BLOCKS.register(bus);
  }
}
