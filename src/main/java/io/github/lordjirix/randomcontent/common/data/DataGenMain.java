package io.github.lordjirix.randomcontent.common.data;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

import io.github.lordjirix.randomcontent.Randomcontent;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Randomcontent.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenMain {
  @SubscribeEvent
  public static void gatherData(final GatherDataEvent event) {
    DataGenerator gen = event.getGenerator();
    PackOutput output = gen.getPackOutput();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    gen.addProvider(event.includeServer(), new RCRecipeGen(output));
    gen.addProvider(
        event.includeClient(), new RCItemModelGen(output, Randomcontent.MODID, existingFileHelper));
    event
        .getGenerator()
        .addProvider(
            event.includeClient(),
            (DataProvider.Factory<RCLangGen>) outpu -> new RCLangGen(outpu, MOD_ID, "en_us"));
  }
}
