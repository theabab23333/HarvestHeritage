package me.theabab2333.harvestheritage.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public record SeedGrowComponent(SeedComponent seedComponent, Block block) {
    public static final Codec<SeedGrowComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        SeedComponent.CODEC.fieldOf("seed_component").forGetter(SeedGrowComponent::seedComponent),
        Block.CODEC.fieldOf("need_block").forGetter(SeedGrowComponent::block)
    ).apply(inst, (sc, block) -> new SeedGrowComponent(sc, block == Blocks.AIR ? null : block)));

    public static final StreamCodec<RegistryFriendlyByteBuf, SeedGrowComponent> STREAM_CODEC = StreamCodec.composite(
        SeedComponent.STREAM_CODEC,
        SeedGrowComponent::seedComponent,
        ByteBufCodecs.registry(Registries.BLOCK),
        SeedGrowComponent::block,
        SeedGrowComponent::new
    );
}
