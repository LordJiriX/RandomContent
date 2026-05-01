package io.github.lordjirix.randomcontent.common.data;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.loader.RCItems;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class RCRecipeGen extends RecipeProvider implements IConditionBuilder {
  public RCRecipeGen(PackOutput pOutput) {
    super(pOutput);
  }

  @SuppressWarnings("removal")
  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> pw) {
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RCItems.BEDROCKIUM_DRILL.get())
    .pattern(" B ")
    .pattern("SSB")
    .pattern(" B ")
    .define('S',Items.STICK)
    .define('B',RCItems.BEDROCKIUM_INGOT.get())
    .unlockedBy(getHasName(RCItems.BEDROCKIUM_INGOT.get()),has(RCItems.BEDROCKIUM_DUST.get()))
    .save(pw);
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RCItems.SIMPLE_GRINDER.get())
            .pattern(" I ")
            .pattern("SIS")
            .pattern("SSS")
            .define('S', Blocks.STONE)
            .define('I',Items.IRON_INGOT)
            .unlockedBy(getHasName(Blocks.STONE),has(Items.IRON_INGOT))
            .save(pw);
    SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(RCItems.BEDROCKIUM_DUST.get()),
            RecipeCategory.MISC,
            RCItems.BEDROCKIUM_INGOT.get(),
            1.0f,
            20 * 32)
        .unlockedBy(getHasName(RCItems.BEDROCKIUM_DUST.get()), has(RCItems.BEDROCKIUM_DUST.get()))
        .save(pw, new ResourceLocation(MODID, "smelting/bedrockium_ingot_from_dust"));
  }
}
