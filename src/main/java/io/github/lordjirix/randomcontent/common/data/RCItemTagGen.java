package io.github.lordjirix.randomcontent.common.data;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.common.data.tag.RCTags;
import io.github.lordjirix.randomcontent.loader.RCItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class RCItemTagGen extends ItemTagsProvider {

  public RCItemTagGen(
      PackOutput pOutput,
      CompletableFuture<HolderLookup.Provider> pLookupProvider,
      CompletableFuture<TagLookup<Block>> pBlockTags,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(pOutput, pLookupProvider, pBlockTags, MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider pProvider) {
    this.tag(RCTags.I.IRON_DUST).add(RCItems.IRON_DUST.get());
    this.tag(RCTags.I.STEEL_INGOT).add(RCItems.STEEL_INGOT.get());
  }
}
