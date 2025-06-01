package xen42.canadamod.recipe;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.input.RecipeInput;

public class CookingPotRecipeInput implements RecipeInput {

    public final List<ItemStack> stacks;
    private final RecipeFinder matcher = new RecipeFinder();
    private final int stackCount;

	private CookingPotRecipeInput(List<ItemStack> stacks) {
		this.stacks = stacks;
		int i = 0;

		for (ItemStack itemStack : stacks) {
			if (!itemStack.isEmpty()) {
				i++;
				this.matcher.addInput(itemStack, 1);
			}
		}

		this.stackCount = i;
	}

    public static CookingPotRecipeInput create(List<ItemStack> stacks) {
		return new CookingPotRecipeInput(stacks);
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return (ItemStack)this.stacks.get(slot);
	}

	@Override
	public int size() {
		return this.stacks.size();
	}

    @Override
	public boolean isEmpty() {
		return this.stackCount == 0;
	}
}
