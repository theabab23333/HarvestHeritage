package me.theabab2333.harvestheritage.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

@Getter
public class HybridRecipe extends BaseAbstractRecipe<RecipeInput> {

    public static final MapCodec<HybridRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Item.CODEC.listOf().fieldOf("input_seeds").forGetter(HybridRecipe::getInputSeeds),
        Item.CODEC.listOf().fieldOf("output_seeds").forGetter(HybridRecipe::getOutputSeeds)
    ).apply(inst, HybridRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, HybridRecipe> STREAM_CODEC = StreamCodec.composite(
        Item.STREAM_CODEC.apply(ByteBufCodecs.list()),
        HybridRecipe::getInputSeeds,
        Item.STREAM_CODEC.apply(ByteBufCodecs.list()),
        HybridRecipe::getOutputSeeds,
        HybridRecipe::new
    );

    private final List<Holder<Item>> inputSeeds;
    private final List<Holder<Item>> outputSeeds;

    public HybridRecipe(List<Holder<Item>> inputSeeds, List<Holder<Item>> outputSeeds) {
        this.inputSeeds = inputSeeds;
        this.outputSeeds = outputSeeds;
    }

    public List<ItemStack> getInputSeedPacketStacks() {
        return inputSeeds.stream().map(Holder::value).map(item -> {
            var info = ModSeeds.ALL_SEED.get(item);
            if (info != null) {
                var patch = SeedUtil.createSeedComponentPatch(item, info);
                return new ItemStack(ModItems.SEED_PACKET, 1, patch);
            }
            return ItemStack.EMPTY;
        }).filter(stack -> !stack.isEmpty()).toList();
    }

    public List<ItemStack> getOutputSeedPacketStacks() {
        return outputSeeds.stream().map(h -> {
            var comp = SeedUtil.getSeedComponent(h.value());
            DataComponentPatch patch = DataComponentPatch.builder().set(ModDataComponents.SEED_COMPONENT.get(), comp).build();
            return new ItemStack(ModItems.SEED_PACKET, 1, patch);
        }).toList();
    }

    @Override
    public RecipeSerializer<HybridRecipe> getSerializer() {
        return ModRecipes.HYBRID_SERIALIZERS.get();
    }

    @Override
    public RecipeType<HybridRecipe> getType() {
        return ModRecipes.HYBRID_TYPE.get();
    }

    public static class Builder {
        private final List<Holder<Item>> inputSeeds;
        private final List<Holder<Item>> outputSeeds;

        public Builder(List<Holder<Item>> inputSeeds, List<Holder<Item>> outputSeeds) {
            this.inputSeeds = inputSeeds;
            this.outputSeeds = outputSeeds;
        }

        public static Builder builder(List<Holder<Item>> inputSeeds, Holder<Item> outputSeed) {
            return new Builder(inputSeeds, List.of(outputSeed));
        }

        public static Builder builder(List<Holder<Item>> inputSeeds, List<Holder<Item>> outputSeeds) {
            return new Builder(inputSeeds, outputSeeds);
        }

        public void save(RecipeOutput consumer, Identifier id) {
            var recipe = new HybridRecipe(inputSeeds, outputSeeds);
            consumer.accept(ResourceKey.create(Registries.RECIPE, id), recipe, null);
        }
    }
}
