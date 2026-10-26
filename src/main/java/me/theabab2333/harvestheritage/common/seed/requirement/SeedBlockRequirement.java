package me.theabab2333.harvestheritage.common.seed.requirement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Optional;

public record SeedBlockRequirement(Optional<Holder<Block>> block, Optional<TagKey<Block>> tag) {
    public static final Codec<SeedBlockRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        BuiltInRegistries.BLOCK.holderByNameCodec().optionalFieldOf("block").forGetter(SeedBlockRequirement::block),
        TagKey.codec(Registries.BLOCK).optionalFieldOf("tag").forGetter(SeedBlockRequirement::tag)
    ).apply(inst, SeedBlockRequirement::new));

    public SeedBlockRequirement {
        Objects.requireNonNull(block);
        Objects.requireNonNull(tag);
        if (block.isPresent() == tag.isPresent()) {
            throw new IllegalArgumentException("Seed block requirement must declare exactly one of block or tag");
        }
    }

    public static SeedBlockRequirement of(Holder<Block> block) {
        return new SeedBlockRequirement(Optional.of(block), Optional.empty());
    }

    public static SeedBlockRequirement of(TagKey<Block> tag) {
        return new SeedBlockRequirement(Optional.empty(), Optional.of(tag));
    }

    public boolean matches(BlockState state) {
        return this.block.map(state::is).orElseGet(() -> this.tag.filter(state::is).isPresent());
    }
}
