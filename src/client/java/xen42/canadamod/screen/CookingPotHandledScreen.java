package xen42.canadamod.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenPos;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import xen42.canadamod.CanadaMod;

public class CookingPotHandledScreen extends RecipeBookScreen<CookingPotScreenHandler> {
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");
    private static final Identifier TEXTURE = Identifier.of(CanadaMod.MOD_ID, "textures/gui/cooking_pot_gui.png");

    public CookingPotHandledScreen(CookingPotScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new CookingPotRecipeBookWidget(handler), inventory, title);
        this.recipeBookWidget = (CookingPotRecipeBookWidget)this.recipeBook;
    }

    private final CookingPotRecipeBookWidget recipeBookWidget;

    @Override
    protected ScreenPos getRecipeBookButtonPos() {
        return new ScreenPos(this.x + 108, this.height / 2 - 28);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, i, j, 0.0F, 0.0F, 
            this.backgroundWidth, this.backgroundHeight, 256, 256);

        int n = MathHelper.ceil((this.handler.getFuelProgress() * 13.0F)) + 1;
        context.drawGuiTexture(RenderLayer::getGuiTextured, LIT_PROGRESS_TEXTURE, 14, 14, 0, 14 - n, i + 81, j + 58 - n, 14, n);

        int l = MathHelper.ceil(this.handler.getCookProgress() * 24.0F);
        context.drawGuiTexture(RenderLayer::getGuiTextured, BURN_PROGRESS_TEXTURE, 24, 16, 0, 0, i + 109, j + 34, l, 16);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
