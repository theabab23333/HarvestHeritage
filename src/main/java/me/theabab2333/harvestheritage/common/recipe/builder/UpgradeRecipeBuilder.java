package me.theabab2333.harvestheritage.common.recipe.builder;

import lombok.Setter;
import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.recipe.UpgradeRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class UpgradeRecipeBuilder implements RecipeBuilder {

    @Nullable
    private final HolderGetter<Fluid> fluidLookup;

    private SizedFluidIngredient inputFluid = null;
    @Setter
    @Nullable
    private FluidStackTemplate outputFluid = null;
    @Setter
    private int point = 0;

    public UpgradeRecipeBuilder() {
        this(null);
    }

    public UpgradeRecipeBuilder(@Nullable HolderGetter<Fluid> fluidLookup) {
        this.fluidLookup = fluidLookup;
    }

    public UpgradeRecipeBuilder input(SizedFluidIngredient ingredient) {
        this.inputFluid = ingredient;
        return this;
    }

    public UpgradeRecipeBuilder input(TagKey<Fluid> tag, int amount) {
        if (fluidLookup == null) {
            throw new IllegalStateException("Fluid tag ingredients require fluid HolderGetter");
        }
        return input(new SizedFluidIngredient(FluidIngredient.of(fluidLookup.getOrThrow(tag)), amount));
    }

    public UpgradeRecipeBuilder input(FluidStack stack) {
        return input(new SizedFluidIngredient(FluidIngredient.of(stack), stack.getAmount()));
    }

    public UpgradeRecipeBuilder output(Holder<Fluid> fluid, int amount) {
        this.outputFluid = new FluidStackTemplate(fluid, amount);
        return this;
    }


    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public @Nullable ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, HarvestHeritage.of("upgrade"));
    }

    @Override
    public void save(RecipeOutput output) {
        throw new IllegalArgumentException("Recipe id is null");
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        Objects.requireNonNull(inputFluid, "input must not be null");
        if (point <= 0) {
            throw new IllegalStateException("point must be greater than 0");
        }
        var recipe = new UpgradeRecipe(inputFluid, outputFluid, point);
        output.accept(id, recipe, null);
    }

    public void save(RecipeOutput output, Identifier id) {
        save(output, ResourceKey.create(Registries.RECIPE, id));
    }
}
