package me.theabab2333.harvestheritage.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

public record SeedComponent(Holder<Item> seed) {
    public static final Codec<SeedComponent> CODEC = Item.CODEC.xmap(
        SeedComponent::new,
        SeedComponent::seed
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SeedComponent> STREAM_CODEC = Item.STREAM_CODEC.map(
        SeedComponent::new,
        SeedComponent::seed
    );

    public static SeedComponent createSeed(Holder<Item> seed) {
        return new SeedComponent(seed);
    }

    public Item getSeed() {
        return seed().value();
    }
}
