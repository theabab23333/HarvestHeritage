package me.theabab2333.harvestheritage.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

// 对的对的，种子袋的组件，种子生长速度产量的
public record SeedPacketComponent(Holder<Item> seed, int speed, int output) {
    public static final int DEFAULT_SPEED = 1;
    public static final int DEFAULT_OUTPUT = 1;

    public static final Codec<SeedPacketComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Item.CODEC.fieldOf("seed").forGetter(SeedPacketComponent::seed),
        Codec.INT.optionalFieldOf("speed", DEFAULT_SPEED).forGetter(SeedPacketComponent::speed),
        Codec.INT.optionalFieldOf("output", DEFAULT_OUTPUT).forGetter(SeedPacketComponent::output)
    ).apply(inst, SeedPacketComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SeedPacketComponent> STREAM_CODEC = StreamCodec.composite(
        Item.STREAM_CODEC,
        SeedPacketComponent::seed,
        ByteBufCodecs.INT,
        SeedPacketComponent::speed,
        ByteBufCodecs.INT,
        SeedPacketComponent::output,
        SeedPacketComponent::new
    );

    public static SeedPacketComponent of(Holder<Item> seed) {
        return new SeedPacketComponent(seed, DEFAULT_SPEED, DEFAULT_OUTPUT);
    }

    public static SeedPacketComponent of(Item seed) {
        return of(seed.builtInRegistryHolder);
    }

    public Item getSeed() {
        return this.seed.value();
    }
}
