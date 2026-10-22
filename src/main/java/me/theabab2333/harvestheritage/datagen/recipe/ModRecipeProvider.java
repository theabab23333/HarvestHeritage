package me.theabab2333.harvestheritage.datagen.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public abstract class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(
        BootstrapContext<Recipe<?>> recipeOutput,
        BootstrapContext<Advancement> advancementOutput
    ) {
        super(recipeOutput, advancementOutput);
    }

    @FunctionalInterface
    public interface RecipeProviderFactory {
        ModRecipeProvider create(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements);
    }

    private static final List<RecipeProviderFactory> PROVIDERS = List.of(
        FindRecipeProvider::new,
        ModItemRecipe::new,
        SeedPacketRecipeProvider::new,
        HybridRecipeProvider::new
    );

    public static RecipeProvider create(
        BootstrapContext<Recipe<?>> recipes,
        BootstrapContext<Advancement> advancements
    ) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            protected void buildRecipes() {
                for (RecipeProviderFactory provider : PROVIDERS) {
                    provider.create(recipes, advancements).buildRecipes();
                }
            }
        };
    }
}