package me.theabab2333.harvestheritage.common.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

public abstract class BaseAbstractRecipe<T extends RecipeInput> implements Recipe<T> {

    @Override
    public final boolean matches(RecipeInput input, Level level) {
        return false;
    }

    @Override
    public final ItemStack assemble(RecipeInput input) {
        return ItemStack.EMPTY;
    }

    @Override
    public final boolean isSpecial() {
        return true;
    }

    @Override
    public final boolean showNotification() {
        return false;
    }

    @Override
    public final String group() {
        return "";
    }

    @Override
    public final PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public final RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
