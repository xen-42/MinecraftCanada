package xen42.canadamod.mixin.client;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import xen42.canadamod.gui.ThermosTooltipComponent;
import xen42.canadamod.item.ThermosTooltipData;

// Have to patch all uses of TooltipComponent::of (for TooltipData)

@Mixin(DrawContext.class)
public class DrawContextMixin {
    @Inject(at = @At("HEAD"), 
        method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Ljava/util/Optional;IILnet/minecraft/util/Identifier;)V",
        cancellable = true)
	private void drawTooltip(TextRenderer textRenderer, List<Text> text, Optional<TooltipData> data, int x, int y, @Nullable Identifier texture, CallbackInfo info) {
        if (data.isPresent() && data.get() instanceof ThermosTooltipData thermosTooltipData) {
            var list = (List)text.stream().map(Text::asOrderedText).map(TooltipComponent::of).collect(Util.toArrayList());

            list.add(list.isEmpty() ? 0 : 1, new ThermosTooltipComponent(thermosTooltipData.contents()));

            var drawContext = (DrawContext)(Object)this;
            drawContext.drawTooltip(textRenderer, list, x, y, HoveredTooltipPositioner.INSTANCE, texture);
            
            info.cancel();
        }
	}
}
