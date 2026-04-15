package xen42.canadamod.rei;

import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;

import me.shedaniel.math.Rectangle;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplayMerger;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import xen42.canadamod.CanadaBlocks;

public class CookingPotCategory implements DisplayCategory<CookingPotREIDisplay> {

	public static final int WIDTH = 110;
	public static final int HEIGHT = 78;
	public static final int COOK_TIME = 40;
	
	@Override
	public Renderer getIcon() {
		return EntryStacks.of(CanadaBlocks.COOKING_POT);
	}

	@Override
	public Text getTitle() {
		return Text.translatable(CanadaBlocks.COOKING_POT.getTranslationKey());
	}

	@Override
	public CategoryIdentifier<? extends CookingPotREIDisplay> getCategoryIdentifier() {
		return CookingPotServerPlugin.COOKING_POT_CATEGORY;
	}

	@Override
	public List<Widget> setupDisplay(CookingPotREIDisplay display, Rectangle bounds) {
		List<Widget> widgets = Lists.newArrayList();
		widgets.add(Widgets.createRecipeBase(bounds));

		Point startPoint = new Point(bounds.getCenterX() - (WIDTH/2 - 10), bounds.getCenterY() - (HEIGHT/2 - 10));

		// 2x2 input grid
		int inputX = startPoint.x + 1;
		int inputY = startPoint.y + 1;

		// Fire (under inputs)
		int fireX = inputX + 9;
		int fireY = inputY + 38;

		// Arrow (to the right of inputs)
		int arrowX = inputX + 38;
		int arrowY = inputY + 9;

		// Output (to the right of arrow)
		int outputX = inputX + 70;
		int outputY = arrowY;

		// Container (slightly below arrow, not touching)
		int containerX = arrowX + 4;
		int containerY = fireY;

		widgets.add(Widgets.createArrow(new Point(arrowX, arrowY))
				.animationDurationTicks(COOK_TIME)); // cook time

		widgets.add(Widgets.createResultSlotBackground(new Point(outputX, outputY)));

		widgets.add(Widgets.createBurningFire(new Point(fireX, fireY))
				//.animationDurationMS(10000));
				.animationDurationTicks(COOK_TIME));

		List<InputIngredient<EntryStack<?>>> input = display.getInputIngredients();
		List<Slot> slots = Lists.newArrayList();

		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 2; x++) {
				slots.add(
					Widgets.createSlot(new Point(inputX + x * 18, inputY + y * 18))
						.markInput()
				);
			}
		}

		for (InputIngredient<EntryStack<?>> ingredient : input) {
			slots.get(ingredient.getIndex()).entries(ingredient.get());
		}

		widgets.addAll(slots);

		widgets.add(
			Widgets.createSlot(new Point(containerX, containerY))
				.entries(display.getContainerEntry())
				.markInput()
		);

		widgets.add(
			Widgets.createSlot(new Point(outputX, outputY))
				.entries(display.getOutputEntries().get(0))
				.disableBackground()
				.markOutput()
		);

		return widgets;
	}
	
	@Override
	@Nullable
	public DisplayMerger<CookingPotREIDisplay> getDisplayMerger() {
		return new DisplayMerger<CookingPotREIDisplay>() {
			@Override
			public boolean canMerge(CookingPotREIDisplay first, CookingPotREIDisplay second) {
				if (!first.getCategoryIdentifier().equals(second.getCategoryIdentifier())) return false;
				if (!equals(first.getOrganisedInputEntries(), second.getOrganisedInputEntries())) return false;
				if (!equals(first.getContainerEntries(), second.getContainerEntries())) return false;
				if (!equals(first.getOutputEntries(), second.getOutputEntries())) return false;
				return true;
			}
			
			@Override
			public int hashOf(CookingPotREIDisplay display) {
				return display.getCategoryIdentifier().hashCode() * 31 * 31 + display.getOrganisedInputEntries().hashCode() * 31 + display.getContainerEntries().hashCode() * 31 + display.getOutputEntries().hashCode();
			}
			
			private boolean equals(List<EntryIngredient> l1, List<EntryIngredient> l2) {
				if (l1.size() != l2.size()) return false;
				Iterator<EntryIngredient> it1 = l1.iterator();
				Iterator<EntryIngredient> it2 = l2.iterator();
				while (it1.hasNext() && it2.hasNext()) {
					if (!it1.next().equals(it2.next())) return false;
				}
				return true;
			}
		};
	}

	@Override
	public int getDisplayWidth(CookingPotREIDisplay display) {
		return WIDTH;
	}

	@Override
	public int getDisplayHeight() {
		return HEIGHT;
	}
}
