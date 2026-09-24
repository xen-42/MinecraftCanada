package xen42.canadamod.rei;

import java.util.List;
import java.util.Optional;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.plugin.client.displays.ClientsidedRecipeBookDisplay;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.NetworkRecipeId;
import xen42.canadamod.recipe.CookingPotRecipeDisplay;

public class ClientsidedCookingPotREIDisplay extends CookingPotREIDisplay implements ClientsidedRecipeBookDisplay {
	public static final DisplaySerializer<ClientsidedCookingPotREIDisplay> SERIALIZER = DisplaySerializer.of(
			RecordCodecBuilder.mapCodec(instance -> instance.group(
					EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(ClientsidedCookingPotREIDisplay::getInputEntries),
					EntryIngredient.codec().listOf().fieldOf("containers").forGetter(ClientsidedCookingPotREIDisplay::getContainerEntries),
					EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(ClientsidedCookingPotREIDisplay::getOutputEntries),
					Codec.INT.xmap(NetworkRecipeId::new, NetworkRecipeId::index).optionalFieldOf("id").forGetter(ClientsidedCookingPotREIDisplay::recipeDisplayId)
			).apply(instance, ClientsidedCookingPotREIDisplay::new)),
			PacketCodec.tuple(
					EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
					ClientsidedCookingPotREIDisplay::getInputEntries,
					EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
					ClientsidedCookingPotREIDisplay::getContainerEntries,
					EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
					ClientsidedCookingPotREIDisplay::getOutputEntries,
					PacketCodecs.optional(PacketCodecs.INTEGER.xmap(NetworkRecipeId::new, NetworkRecipeId::index)),
					ClientsidedCookingPotREIDisplay::recipeDisplayId,
					ClientsidedCookingPotREIDisplay::new
			), false);
	
	private final Optional<NetworkRecipeId> id;
	
	public ClientsidedCookingPotREIDisplay(CookingPotRecipeDisplay recipe, Optional<NetworkRecipeId> id) {
		super(recipe);
		this.id = id;
	}
	
	public ClientsidedCookingPotREIDisplay(List<EntryIngredient> inputs, List<EntryIngredient> containers, List<EntryIngredient> outputs, Optional<NetworkRecipeId> id) {
		super(inputs, containers, outputs);
		this.id = id;
	}

	@Override
	public Optional<NetworkRecipeId> recipeDisplayId() {
		return id;
	}

	@Override
	public DisplaySerializer<? extends Display> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return CookingPotServerPlugin.COOKING_POT_CATEGORY;
	}
}
