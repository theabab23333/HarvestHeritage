package me.theabab2333.harvestheritage.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.theabab2333.harvestheritage.init.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public record FindRecipe(
    Ingredient ingredient, List<ItemStackTemplate> result
) implements BetterRecipe<RecipeInput> {

    public static final MapCodec<FindRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Ingredient.CODEC.fieldOf("ingredient").forGetter(FindRecipe::ingredient),
        ItemStackTemplate.CODEC.listOf().fieldOf("result").forGetter(FindRecipe::result)
    ).apply(inst, FindRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FindRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC,
        FindRecipe::ingredient,
        ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()),
        FindRecipe::result,
        FindRecipe::new
    );

    @Override
    public boolean matches(RecipeInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput input) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return ModRecipes.FIND_SERIALIZERS.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipes.FIND_TYPE.get();
    }
}
