package io.github.lordjirix.randomcontent.gui.screen;

import static io.github.lordjirix.randomcontent.Randomcontent.MODID;

import io.github.lordjirix.randomcontent.gui.menu.GreenHouseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GreenHouseScreen extends AbstractContainerScreen<GreenHouseMenu> {
  private static final ResourceLocation TEXTURE =
      new ResourceLocation(MODID, "textures/gui/greenhouse_gui.png");

  public GreenHouseScreen(GreenHouseMenu menu, Inventory inv, Component title) {
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
