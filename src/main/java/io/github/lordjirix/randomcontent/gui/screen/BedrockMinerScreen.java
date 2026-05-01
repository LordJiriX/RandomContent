package io.github.lordjirix.randomcontent.gui.screen;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.gui.menu.BedrockMinerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BedrockMinerScreen extends AbstractContainerScreen<BedrockMinerMenu> {
  private static final ResourceLocation TEXTURE =
      new ResourceLocation(MODID, "textures/gui/bedrock_miner_gui.png");

  public BedrockMinerScreen(BedrockMinerMenu menu, Inventory inv, Component title) {
    super(menu, inv, title);
    this.imageWidth = 176;
    this.imageHeight = 166;
  }

  @Override
  protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    pGuiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
  }
}
