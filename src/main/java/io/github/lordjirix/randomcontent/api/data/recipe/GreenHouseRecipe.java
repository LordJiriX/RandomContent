package io.github.lordjirix.randomcontent.api.data.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GreenHouseRecipe {

    ItemStack[] output;
    public GreenHouseRecipe(ItemStack[] output) {
        this.output = output;
    }
    public ItemStack[] getOutput() {
        return output;
    }
    public void setOutput(ItemStack[] output) {
        this.output = output;
    }

}
