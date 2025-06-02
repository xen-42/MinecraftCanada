package xen42.canadamod.recipe;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.recipe.display.SlotDisplay.ItemSlotDisplay;
import net.minecraft.recipe.display.SlotDisplay.StackSlotDisplay;
import net.minecraft.resource.featuretoggle.FeatureSet;

public record CookingPotRecipeDisplay(List<SlotDisplay> ingredients, SlotDisplay containers, StackSlotDisplay result, ItemSlotDisplay craftingStation)
	implements RecipeDisplay {

	public static final MapCodec<CookingPotRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				SlotDisplay.CODEC.listOf().fieldOf("ingredients").forGetter(CookingPotRecipeDisplay::ingredients),
				SlotDisplay.CODEC.fieldOf("containers").forGetter(CookingPotRecipeDisplay::containers),
				StackSlotDisplay.CODEC.fieldOf("result").forGetter(CookingPotRecipeDisplay::result),
				ItemSlotDisplay.CODEC.fieldOf("crafting_station").forGetter(CookingPotRecipeDisplay::craftingStation)
			)
			.apply(instance, CookingPotRecipeDisplay::new)
	);
	public static final PacketCodec<RegistryByteBuf, CookingPotRecipeDisplay> PACKET_CODEC = PacketCodec.tuple(
		SlotDisplay.PACKET_CODEC.collect(PacketCodecs.toList()),
		CookingPotRecipeDisplay::ingredients,
		SlotDisplay.PACKET_CODEC,
		CookingPotRecipeDisplay::containers,
		StackSlotDisplay.PACKET_CODEC,
		CookingPotRecipeDisplay::result,
		ItemSlotDisplay.PACKET_CODEC,
		CookingPotRecipeDisplay::craftingStation,
		CookingPotRecipeDisplay::new
	);
	public static final RecipeDisplay.Serializer<CookingPotRecipeDisplay> SERIALIZER = new RecipeDisplay.Serializer<>(CODEC, PACKET_CODEC);

    @Override
    public Serializer<? extends RecipeDisplay> serializer() {
        return SERIALIZER;
    }

    @Override
	public boolean isEnabled(FeatureSet features) {
		return this.ingredients.stream().allMatch(ingredient -> ingredient.isEnabled(features)) && RecipeDisplay.super.isEnabled(features) && containers.isEnabled(features);
	}
}
