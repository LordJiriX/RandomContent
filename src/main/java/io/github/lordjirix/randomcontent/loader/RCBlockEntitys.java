package io.github.lordjirix.randomcontent.loader;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.common.entity.BedrockMinerBlockEntity;
import io.github.lordjirix.randomcontent.common.entity.GreenHouseBlockEntity;
import io.github.lordjirix.randomcontent.common.entity.TimeCompressorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RCBlockEntitys {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

  public static final RegistryObject<BlockEntityType<BedrockMinerBlockEntity>>
      BEDROCK_MINER_BLOCK_ENTITY =
          BLOCK_ENTITIES.register(
              "bedrock_miner_block",
              () ->
                  BlockEntityType.Builder.of(
                          BedrockMinerBlockEntity::new, RCBlocks.BEDROCK_MINER_BLOCK.get())
                      .build(null));
  public static final RegistryObject<BlockEntityType<GreenHouseBlockEntity>>
      GREENHOUSE_BLOCK_ENTITY =
          BLOCK_ENTITIES.register(
              "greenhouse_block",
              () ->
                  BlockEntityType.Builder.of(
                          GreenHouseBlockEntity::new, RCBlocks.GREENHOUSE_BLOCK.get())
                      .build(null));
  public static final RegistryObject<BlockEntityType<TimeCompressorBlockEntity>>
      TIME_COMPRESSOR_BLOCK_ENTITY =
          BLOCK_ENTITIES.register(
              "time_compressor_block",
              () ->
                  BlockEntityType.Builder.of(
                          TimeCompressorBlockEntity::new, RCBlocks.TIME_COMPRESSOR_BLOCK.get())
                      .build(null));

  public static void init(IEventBus bus) {
    BLOCK_ENTITIES.register(bus);
  }
}
