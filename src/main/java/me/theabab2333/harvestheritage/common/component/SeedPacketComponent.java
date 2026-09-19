package me.theabab2333.harvestheritage.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

public record SeedPacketComponent(SeedComponent seedComponent, int speed, int output) {
    public static final Codec<SeedPacketComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        SeedComponent.CODEC.fieldOf("seed_component").forGetter(SeedPacketComponent::seedComponent),
        Codec.INT.fieldOf("speed").forGetter(SeedPacketComponent::speed),
        Codec.INT.fieldOf("output").forGetter(SeedPacketComponent::output)
    ).apply(inst, SeedPacketComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SeedPacketComponent> STREAM_CODEC = StreamCodec.composite(
        SeedComponent.STREAM_CODEC,
        SeedPacketComponent::seedComponent,
        ByteBufCodecs.INT,
        SeedPacketComponent::speed,
        ByteBufCodecs.INT,
        SeedPacketComponent::output,
        SeedPacketComponent::new
    );

    public static SeedPacketComponent createSeedPacket(Holder<Item> seed, int speed, int output) {
        return new SeedPacketComponent(SeedComponent.createSeed(seed), speed, output);
    }

    public static SeedPacketComponent createSeedPacket(SeedComponent seedComponent, int speed, int output) {
        return new SeedPacketComponent(seedComponent, speed, output);
    }

    public static SeedPacketComponent updateSeedPacket(SeedPacketComponent component, int speed, int output) {
        return new SeedPacketComponent(component.seedComponent, speed, output);
    }
}
