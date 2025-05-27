package xen42.canadamod.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;

@Mixin(AbstractSignEditScreen.class)
public class AbstractSignEditScreenMixin {
    @Shadow
	@Mutable
	protected WoodType signType;

	@Shadow
	@Mutable
	protected SignBlockEntity blockEntity;
}
