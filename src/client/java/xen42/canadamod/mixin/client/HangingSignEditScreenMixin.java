package xen42.canadamod.mixin.client;

import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HangingSignEditScreen;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaBlocks;
import xen42.canadamod.CanadaMod;
import xen42.canadamod.sign.MapleHangingSignBlockEntity;

import javax.swing.text.html.HTMLDocument.BlockElement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HangingSignEditScreen.class)
public class HangingSignEditScreenMixin {
	@Shadow
	@Mutable
	private Identifier texture;

	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(CallbackInfo info) {
        var superThis = (AbstractSignEditScreenMixin)(Object)this;

		if (superThis.blockEntity instanceof MapleHangingSignBlockEntity) {
			superThis.signType = CanadaBlocks.MAPLE_WOOD_TYPE;
        	this.texture = Identifier.of(CanadaMod.MOD_ID, "textures/gui/hanging_signs/maple.png");
		}
	}
}