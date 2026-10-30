package me.theabab2333.harvestheritage.integration.jei.category;

import me.theabab2333.harvestheritage.common.recipe.FindRecipe;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.integration.jei.ModJeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeHolder;

public class FindRecipeCategory extends AbstractRecipeCategory<RecipeHolder<FindRecipe>> {

    public FindRecipeCategory(IGuiHelper guiHelper) {
        super(
            ModJeiPlugin.FIND_TYPE.get(),
            Component.translatable("jei.harvestheritage.find"),
            guiHelper.createDrawableItemLike(ModItems.MAGNIFYING_GLASS),
            82,
            34
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<FindRecipe> holder, IFocusGroup focuses) {
        FindRecipe recipe = holder.value();

        builder.addInputSlot(1, 7)
            .setStandardSlotBackground().add(recipe.ingredient());

        builder.addOutputSlot(61, 7)
            .setOutputSlotBackground()
            .addItemStacks(recipe.result().stream()
                .map(ItemStackTemplate::create)
                .toList());
    }
}
