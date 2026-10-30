package me.theabab2333.harvestheritage.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.theabab2333.harvestheritage.common.recipe.builder.UpgradeRecipeBuilder;
import me.theabab2333.harvestheritage.init.ModRecipes;
import net.minecraft.core.HolderGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public record UpgradeRecipe(
    SizedFluidIngredient inputFluid, @Nullable FluidStackTemplate outputFluid, int point
) implements BetterRecipe<UpgradeRecipe.Input> {

    public static UpgradeRecipeBuilder builder(HolderGetter<Fluid> fluids) {
        return new UpgradeRecipeBuilder(fluids);
    }

    @Override
    public boolean matches(Input input, Level level) {
        FluidStack output = output();
        return inputFluid.test(input.input) && (input.output.isEmpty() || output.is(input.output.getFluid()));
    }

    @Override
    public ItemStack assemble(Input input) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<? extends Recipe<Input>> getSerializer() {
        return ModRecipes.UPGRADE_SERIALIZERS.get();
    }

    @Override
    public RecipeType<? extends Recipe<Input>> getType() {
        return ModRecipes.UPGRADE_TYPE.get();
    }

    public static final MapCodec<UpgradeRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        SizedFluidIngredient.CODEC.fieldOf("input_fluid").forGetter(UpgradeRecipe::inputFluid),
        FluidStackTemplate.CODEC.optionalFieldOf("output_fluid").forGetter(UpgradeRecipe::optionalOutputFluid),
        Codec.INT.fieldOf("point").forGetter(UpgradeRecipe::point)
    ).apply(inst, (input, output, point) -> new UpgradeRecipe(input, output.orElse(null), point)));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpgradeRecipe> STREAM_CODEC = StreamCodec.composite(
        SizedFluidIngredient.STREAM_CODEC,
        UpgradeRecipe::inputFluid,
        ByteBufCodecs.optional(FluidStackTemplate.STREAM_CODEC),
        UpgradeRecipe::optionalOutputFluid,
        ByteBufCodecs.VAR_INT,
        UpgradeRecipe::point,
        (input, output, coolant) -> new UpgradeRecipe(input, output.orElse(null), coolant)
    );

    public Optional<FluidStackTemplate> optionalOutputFluid() {
        return Optional.ofNullable(outputFluid);
    }

    public FluidStack output() {
        return outputFluid == null ? FluidStack.EMPTY : outputFluid.create();
    }

    public record Input(FluidStack input, FluidStack output) implements RecipeInput {
        @Override
        public ItemStack getItem(int i) {
            return ItemStack.EMPTY;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return input.isEmpty();
        }
    }
}
