package io.github.lordjirix.randomcontent.loader;

import io.github.lordjirix.randomcontent.common.entity.BedrockMinerBlockEntity;
import io.github.lordjirix.randomcontent.common.entity.GreenHouseBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

public class RCBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);


    public static final RegistryObject<BlockEntityType<BedrockMinerBlockEntity>> BEDROCK_MINER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("bedrock_miner_block", () -> BlockEntityType.Builder.of(BedrockMinerBlockEntity::new, RCBlocks.BEDROCK_MINER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<GreenHouseBlockEntity>> GREENHOUSE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("greenhouse_block", () -> BlockEntityType.Builder.of(GreenHouseBlockEntity::new, RCBlocks.GREENHOUSE_BLOCK.get()).build(null));



    public static void init(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
