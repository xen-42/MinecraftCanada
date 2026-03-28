package xen42.canadamod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xen42.canadamod.CanadaSounds;
import xen42.canadamod.block.MooseSkullBlockEntity;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {
    @Redirect(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/enums/NoteBlockInstrument;hasCustomSound()Z"))
    private boolean hasCustomSoundInjection(NoteBlockInstrument instrument, BlockState state, World world, BlockPos pos) {
        return instrument.hasCustomSound() && !(world.getBlockEntity(pos.up()) instanceof MooseSkullBlockEntity);
    }

    @Redirect(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/enums/NoteBlockInstrument;getSound()Lnet/minecraft/registry/entry/RegistryEntry;"))
    private RegistryEntry<SoundEvent> onSyncedBlockEventInjection(NoteBlockInstrument instrument, BlockState state, World world, BlockPos pos) {
    	if (world.getBlockEntity(pos.up()) instanceof MooseSkullBlockEntity mooseSkullBlockEntity) {
    		return RegistryEntry.of(CanadaSounds.SOUND_MOOSE_AMBIENT);
        }
    	return instrument.getSound();
    }
}
