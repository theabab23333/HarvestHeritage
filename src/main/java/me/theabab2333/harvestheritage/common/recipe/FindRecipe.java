package me.theabab2333.harvestheritage.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import me.theabab2333.harvestheritage.init.ModRecipes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;

@Getter
public class FindRecipe extends BaseAbstractRecipe<RecipeInput> {

    public static final MapCodec<FindRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Ingredient.CODEC.fieldOf("ingredient").forGetter(FindRecipe::getIngredient),
        ItemStackTemplate.CODEC.listOf().fieldOf("result").forGetter(FindRecipe::getResult)
    ).apply(inst, FindRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FindRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC,
        FindRecipe::getIngredient,
        ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()),
        FindRecipe::getResult,
        FindRecipe::new
    );

    private final Ingredient ingredient;
    private final List<ItemStackTemplate> result;

    public FindRecipe(Ingredient ingredient, List<ItemStackTemplate> result) {
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return ModRecipes.FIND_SERIALIZERS.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipes.FIND_TYPE.get();
    }

    public static class Builder {
        private final Ingredient ingredient;
        private final List<ItemStackTemplate> result;

        public Builder(Ingredient inputItem, List<ItemStackTemplate> result) {
            this.ingredient = inputItem;
            this.result = result;
        }

        public static Builder builder(ItemLike input, ItemLike... output) {
            return new Builder(
                Ingredient.of(input),
                Arrays.stream(output).map(itemLike -> new ItemStackTemplate(itemLike.asItem())).toList()
            );
        }

        public static Builder builder(HolderSet<Item> input, ItemLike... output) {
            return new Builder(
                Ingredient.of(input),
                Arrays.stream(output).map(itemLike -> new ItemStackTemplate(itemLike.asItem())).toList()
            );
        }

        public static Builder builder(Ingredient input, ItemLike... output) {
            return new Builder(
                input,
                Arrays.stream(output).map(itemLike -> new ItemStackTemplate(itemLike.asItem())).toList()
            );
        }

        public static Builder builder(ItemLike input, List<ItemStackTemplate> output) {
            return new Builder(Ingredient.of(input), output);
        }

        public void save(RecipeOutput consumer, Identifier id) {
            var recipe = new FindRecipe(ingredient, result);
            consumer.accept(ResourceKey.create(Registries.RECIPE, id), recipe, null);
        }
    }
}
