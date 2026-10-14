package me.theabab2333.harvestheritage.integration.jei.slot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

// 哈哈，抄的 SlotDisplay.TagSlotDisplay
public record BlockTagSlotDisplay(TagKey<Block> tag) implements SlotDisplay {
    public static final MapCodec<BlockTagSlotDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(TagKey.codec(Registries.BLOCK)
        .fieldOf("tag")
        .forGetter(BlockTagSlotDisplay::tag)).apply(i, BlockTagSlotDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BlockTagSlotDisplay> STREAM_CODEC = StreamCodec.composite(
        TagKey.streamCodec(
            Registries.BLOCK), BlockTagSlotDisplay::tag, BlockTagSlotDisplay::new
    );

    public static final Type<BlockTagSlotDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> factory) {
        if (!(factory instanceof DisplayContentsFactory.ForStacks<T> stacks)) {
            return Stream.empty();
        }

        HolderLookup.Provider registries = context.getOptional(SlotDisplayContext.REGISTRIES);
        if (registries == null) {
            return Stream.empty();
        }

        return registries.lookupOrThrow(Registries.BLOCK)
            .get(this.tag)
            .stream()
            .flatMap(HolderSet.ListBacked::stream)
            .map(block -> block.value().asItem())
            .filter(item -> item != Items.AIR)
            .map(item -> stacks.forStack(new ItemStack(item)));
    }

    @Override
    public Type<BlockTagSlotDisplay> type() {
        return TYPE;
    }
}
