package me.theabab2333.harvestheritage.common.recipe.builder;

import me.theabab2333.harvestheritage.common.recipe.SeedPacketRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

public class SeedPacketRecipeBuilder implements RecipeBuilder {
    private final Ingredient knownSeed;
    private final Ingredient acceptPaper;
    private final ItemStackTemplate result;

    public SeedPacketRecipeBuilder(Ingredient knownSeed, Ingredient acceptPaper, ItemStackTemplate result) {
        this.knownSeed = knownSeed;
        this.acceptPaper = acceptPaper;
        this.result = result;
    }

    public static SeedPacketRecipeBuilder builder(
        Ingredient knownSeed,
        Ingredient acceptPaper,
        ItemStackTemplate result
    ) {
        return new SeedPacketRecipeBuilder(knownSeed, acceptPaper, result);
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
        SeedPacketRecipe recipe = new SeedPacketRecipe(
            new Recipe.CommonInfo(false),
            new CraftingRecipe.CraftingBookInfo(CraftingBookCategory.MISC, ""),
            this.knownSeed,
            this.acceptPaper,
            this.result
        );
        output.accept(id, recipe, null);
    }

    public void save(RecipeOutput output, Identifier id) {
        this.save(output, ResourceKey.create(Registries.RECIPE, id));
    }
}
