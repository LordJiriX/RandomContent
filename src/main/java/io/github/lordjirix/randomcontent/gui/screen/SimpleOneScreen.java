package io.github.lordjirix.randomcontent.gui.screen;

import io.github.lordjirix.randomcontent.gui.menu.BedrockMinerMenu;
import io.github.lordjirix.randomcontent.gui.menu.SimpleOneMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

public class SimpleOneScreen extends AbstractContainerScreen<SimpleOneMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MODID, "textures/gui/bedrock_miner_gui.png");

  public SimpleOneScreen(SimpleOneMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
      super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
      renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}

