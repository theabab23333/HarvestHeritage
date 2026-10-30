package me.theabab2333.harvestheritage.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public record HybridRecipe(
    List<Holder<Item>> inputSeeds, List<Holder<Item>> outputSeeds
) implements BetterRecipe<RecipeInput> {

    public static final MapCodec<HybridRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Item.CODEC.listOf().fieldOf("input_seeds").forGetter(HybridRecipe::inputSeeds),
        Item.CODEC.listOf().fieldOf("output_seeds").forGetter(HybridRecipe::outputSeeds)
    ).apply(inst, HybridRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, HybridRecipe> STREAM_CODEC = StreamCodec.composite(
        Item.STREAM_CODEC.apply(ByteBufCodecs.list()),
        HybridRecipe::inputSeeds,
        Item.STREAM_CODEC.apply(ByteBufCodecs.list()),
        HybridRecipe::outputSeeds,
        HybridRecipe::new
    );

    public List<ItemStack> inputSeedPacketStacks() {
        return this.inputSeeds.stream().map(Holder::value)
            .map(SeedUtil::getSeedPacket)
            .toList();
    }

    public List<ItemStack> outputSeedPacketStacks() {
        return this.outputSeeds.stream()
            .map(SeedUtil::getSeedPacket)
            .toList();
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput input) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<HybridRecipe> getSerializer() {
        return ModRecipes.HYBRID_SERIALIZERS.get();
    }

    @Override
    public RecipeType<HybridRecipe> getType() {
        return ModRecipes.HYBRID_TYPE.get();
    }
}
