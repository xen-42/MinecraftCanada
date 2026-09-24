package xen42.canadamod.rei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import xen42.canadamod.screen.CookingPotScreenHandler;

public class CookingPotTransferHandler implements SimpleTransferHandler {

	// Disabled
	// It works fine when every slot in the recipe has an item.
	// As soon as there is any empty slot it breaks and I can't figure out why.
	// It will tell me it had a successful transfer but then nothing actually moves, it only empties the slots.
	private static final boolean DISABLED = true;

	public CookingPotScreenHandler getCookingPotMenu(Context context) {
		if (context.getMenu() instanceof CookingPotScreenHandler menu) {
			return menu;
		}
		else {
			return null;
		}
	}
	
	@Override
	public ApplicabilityResult checkApplicable(Context context) {
		if (DISABLED) return ApplicabilityResult.createNotApplicable();
		
		if (context.getMenu() instanceof CookingPotScreenHandler
				&& context.getDisplay().getCategoryIdentifier() == CookingPotServerPlugin.COOKING_POT_CATEGORY
				&& context.getContainerScreen() != null) {
			return ApplicabilityResult.createApplicable();
		}
		else {
			return ApplicabilityResult.createNotApplicable();
		}
	}
	
	public Iterable<SlotAccessor> getInputSlotsWithoutEmpty(Context context, List<InputIngredient<ItemStack>> inputs) {
	    CookingPotScreenHandler menu = getCookingPotMenu(context);
	    List<Slot> slots = menu.getInputSlots();
	    List<SlotAccessor> accessors = new ArrayList<>();
	    List<InputIngredient<ItemStack>> filteredInputs = new ArrayList<>();

	    int i = 0;

	    for (Slot slot : slots) {
	        int slotIndex = i++;

	        InputIngredient<ItemStack> matchingInput = null;
	        boolean isEmpty = false;

	        for (var input : inputs) {
	            if (input.getIndex() == slotIndex) {
	                matchingInput = input;
	                isEmpty = input.get().isEmpty() || input.get().getFirst().isEmpty();
	                break;
	            }
	        }

	        if (!isEmpty) {
	            accessors.add(SlotAccessor.fromSlot(slot));

	            if (matchingInput != null) {
	                filteredInputs.add(matchingInput);
	            }
	        }
	    }

	    // Handle container slot
	    int containerSlotIndex = i++;

	    InputIngredient<ItemStack> containerInput = null;
	    boolean isEmptyContainer = false;

	    for (var input : inputs) {
	        if (input.getIndex() == containerSlotIndex) {
	            containerInput = input;
	            isEmptyContainer = input.get().isEmpty() || input.get().getFirst().isEmpty();
	            break;
	        }
	    }

	    if (!isEmptyContainer) {
	        accessors.add(SlotAccessor.fromSlot(menu.getContainerSlot()));
	        if (containerInput != null) {
	            filteredInputs.add(containerInput);
	        }
	    }

	    // Replace original list contents with reindexed filtered inputs
	    inputs.clear();

	    for (int newIndex = 0; newIndex < filteredInputs.size(); newIndex++) {
	        InputIngredient<ItemStack> input = filteredInputs.get(newIndex);
	        inputs.add(InputIngredient.of(newIndex, input.getDisplayIndex(), input.get()));
	    }

	    return accessors;
	}
	
	@Override
	public Iterable<SlotAccessor> getInputSlots(Context context) {
		CookingPotScreenHandler menu = getCookingPotMenu(context);
		List<Slot> slots = menu.getInputSlots();
		List<SlotAccessor> accessors = new ArrayList<SlotAccessor>();
		for (Slot slot : slots) {
			accessors.add(SlotAccessor.fromSlot(slot));
		}
		accessors.add(SlotAccessor.fromSlot(menu.getContainerSlot()));
		return accessors;
	}
	
    @Override
    public Result handle(Context context) {
    	var indexedInputs = getInputsIndexed(context);
        return handleSimpleTransfer(
        		context, 
        		getMissingInputRenderer(), 
        		indexedInputs, 
        		//getInputSlots(context),
        		getInputSlotsWithoutEmpty(context, indexedInputs), 
        		getInventorySlots(context)
        	);
    }
	
	@Override
	public Iterable<SlotAccessor> getInventorySlots(Context context) {
		ClientPlayerEntity player = context.getMinecraft().player;
		PlayerInventory inventory = player.getInventory();
		return IntStream.range(0, inventory.getMainStacks().size())
				.mapToObj(index -> SlotAccessor.fromPlayerInventory(player, index))
				.collect(Collectors.toList());
	}

	@Override
	public List<InputIngredient<ItemStack>> getInputsIndexed(Context context) {
		Display display = context.getDisplay();
		if (display instanceof CookingPotREIDisplay reiDisplay) {
			CookingPotScreenHandler menu = getCookingPotMenu(context);
			ClientPlayerEntity player = context.getMinecraft().player;
			List<InputIngredient<EntryStack<?>>> ingredients = 
					reiDisplay.getInputAndContainerIngredients(menu, player);
			List<InputIngredient<ItemStack>> inputs = CollectionUtils.map(ingredients, (entry) ->
					InputIngredient.withType(entry, VanillaEntryTypes.ITEM));
			return inputs;
		}
		else {
			return Collections.emptyList();
		}
	}
}
