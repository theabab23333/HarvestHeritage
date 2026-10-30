package me.theabab2333.harvestheritage.common.recipe.builder;

import me.theabab2333.harvestheritage.common.recipe.FindRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class FindRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final List<ItemStackTemplate> result;

    public FindRecipeBuilder(Ingredient inputItem, List<ItemStackTemplate> result) {
        this.ingredient = inputItem;
        this.result = result;
    }

    public static FindRecipeBuilder builder(ItemLike input, ItemLike... output) {
        return new FindRecipeBuilder(
            Ingredient.of(input),
            Arrays.stream(output).map(itemLike -> new ItemStackTemplate(itemLike.asItem())).toList()
        );
    }

    public static FindRecipeBuilder builder(HolderSet<Item> input, ItemLike... output) {
        return new FindRecipeBuilder(
            Ingredient.of(input),
            Arrays.stream(output).map(itemLike -> new ItemStackTemplate(itemLike.asItem())).toList()
        );
    }

    public static FindRecipeBuilder builder(Ingredient input, ItemLike... output) {
        return new FindRecipeBuilder(
            input,
            Arrays.stream(output).map(itemLike -> new ItemStackTemplate(itemLike.asItem())).toList()
        );
    }

    public static FindRecipeBuilder builder(ItemLike input, List<ItemStackTemplate> output) {
        return new FindRecipeBuilder(Ingredient.of(input), output);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @Nullable ResourceKey<Recipe<?>> defaultId() {
        return null;
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        output.accept(id, new FindRecipe(this.ingredient, this.result), null);
    }

    public void save(RecipeOutput output, Identifier id) {
        this.save(output, ResourceKey.create(Registries.RECIPE, id));
    }
}
