package me.theabab2333.harvestheritage.common.recipe.builder;

import me.theabab2333.harvestheritage.common.recipe.HybridRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class HybridRecipeBuilder implements RecipeBuilder {
    private final List<Holder<Item>> inputSeeds;
    private final List<Holder<Item>> outputSeeds;

    public HybridRecipeBuilder(List<Holder<Item>> inputSeeds, List<Holder<Item>> outputSeeds) {
        this.inputSeeds = inputSeeds;
        this.outputSeeds = outputSeeds;
    }

    public static HybridRecipeBuilder builder(List<Holder<Item>> inputSeeds, Holder<Item> outputSeed) {
        return new HybridRecipeBuilder(inputSeeds, List.of(outputSeed));
    }

    public static HybridRecipeBuilder builder(List<Holder<Item>> inputSeeds, List<Holder<Item>> outputSeeds) {
        return new HybridRecipeBuilder(inputSeeds, outputSeeds);
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
        output.accept(id, new HybridRecipe(this.inputSeeds, this.outputSeeds), null);
    }

    public void save(RecipeOutput output, Identifier id) {
        this.save(output, ResourceKey.create(Registries.RECIPE, id));
    }
}
