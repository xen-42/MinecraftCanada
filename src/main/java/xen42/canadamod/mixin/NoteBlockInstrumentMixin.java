package xen42.canadamod.mixin;

import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import xen42.canadamod.CanadaNoteBlockInstruments;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//ID OF NOTEBLOCK FIELD: field_12652
@Mixin(NoteBlockInstrument.class)
public class NoteBlockInstrumentMixin {

    @Shadow
    @Mutable
    @Final
    private static NoteBlockInstrument[] field_12652;

    @Invoker("<init>")
    private static NoteBlockInstrument newNoteType(String internalName,
                                                    int ordinal,
                                                    String name,
                                                    RegistryEntry<SoundEvent> sound,
                                                    NoteBlockInstrument.Type type)
    {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/block/enums/NoteBlockInstrument;field_12652:[Lnet/minecraft/block/enums/NoteBlockInstrument;",
            shift = At.Shift.AFTER))
    private static void addCustomNotes(CallbackInfo ci) {
        List<NoteBlockInstrument> instruments = new ArrayList<>(Arrays.asList(field_12652));
        NoteBlockInstrument last = instruments.get(instruments.size() - 1);
        int i = 1;
        for (CanadaNoteBlockInstruments instrument : CanadaNoteBlockInstruments.values()) {
            instruments.add(newNoteType(instrument.name(), last.ordinal() + i, instrument.asString(), instrument.getSound(), instrument.getType()));
            i++;
        }
        field_12652 = instruments.toArray(new NoteBlockInstrument[0]);
    }
}