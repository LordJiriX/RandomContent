package io.github.lordjirix.randomcontent.common.data;

import io.github.lordjirix.randomcontent.loader.RCCustom;
import io.github.lordjirix.randomcontent.loader.RCItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

public class RCRecipeGen extends RecipeProvider implements IConditionBuilder {
    public RCRecipeGen(PackOutput pOutput) {
        super(pOutput);
    }
    @SuppressWarnings("removal")
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pw) {
        /*ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RCCustom.bedrockium.getItem(0))
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',RCCustom.bedrockium.getItem(1))
                .unlockedBy(getHasName(RCCustom.bedrockium.getItem(1)),has(RCCustom.bedrockium.getItem(1)))
                .save(pw);*/
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RCItems.BEDROCKIUM_DUST.get()),RecipeCategory.MISC,RCItems.BEDROCKIUM_INGOT.get() ,1.0f, 20*32)
                .unlockedBy(getHasName(RCItems.BEDROCKIUM_DUST.get()),has(RCItems.BEDROCKIUM_DUST.get()))
                .save(pw, new ResourceLocation(MODID, "smelting/bedrockium_ingot_from_dust"));

    }

}
