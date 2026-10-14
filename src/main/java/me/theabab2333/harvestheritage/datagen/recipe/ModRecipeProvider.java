package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @FunctionalInterface
    public interface RecipeProviderFactory {
        ModRecipeProvider create(HolderLookup.Provider registries, RecipeOutput output);
    }

    public static class Runner extends RecipeProvider.Runner {

        private static final List<RecipeProviderFactory> PROVIDERS = List.of(
            FindRecipeProvider::new,
            ModItemRecipe::new,
            SeedPacketRecipeProvider::new,
            HybridRecipeProvider::new
        );

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new RecipeProvider(provider, output) {
                @Override
                protected void buildRecipes() {
                    for (var provider : PROVIDERS) {
                        provider.create(registries, output).buildRecipes();
                    }
                }
            };
        }

        @Override
        public String getName() {
            return HarvestHeritage.MOD_ID;
        }
    }
}
