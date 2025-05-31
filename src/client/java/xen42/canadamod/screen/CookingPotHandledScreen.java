package xen42.canadamod.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenPos;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaMod;

public class CookingPotHandledScreen extends HandledScreen<CookingPotScreenHandler> {

    public CookingPotHandledScreen(CookingPotScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    private static final Identifier TEXTURE = Identifier.of(CanadaMod.MOD_ID, "textures/gui/cooking_pot_gui.png");

    /*
    @Override
    protected ScreenPos getRecipeBookButtonPos() {
        return new ScreenPos(this.x + 132, this.height / 2 - 31 - 8);
    }
        */

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, i, j, 0.0F, 0.0F, 
            this.backgroundWidth, this.backgroundHeight, 256, 256);
    }
}
