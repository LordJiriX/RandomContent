package io.github.lordjirix.randomcontent.common.data;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.loader.RCBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class RCBlockModelGen extends BlockStateProvider {

  public RCBlockModelGen(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    blockWithItem(RCBlocks.MACHINE_CASING_STEEL);
  }

  private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
    simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
  }
}
