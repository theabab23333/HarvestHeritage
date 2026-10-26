package me.theabab2333.harvestheritage.common.seed;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.theabab2333.harvestheritage.common.seed.requirement.SeedBlockRequirement;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

import java.util.List;

// TODO: Level\Light
public record SeedInfo(
    Holder<Item> seed,
    List<Holder<Item>> results,
    int stage,
    List<SeedBlockRequirement> needBlocks
) {
    public static final Codec<SeedInfo> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Item.CODEC.fieldOf("seed").forGetter(SeedInfo::seed),
        Item.CODEC.listOf().fieldOf("results").forGetter(SeedInfo::results),
        Codec.INT.fieldOf("stage").forGetter(SeedInfo::stage),
        SeedBlockRequirement.CODEC.listOf().optionalFieldOf("need_blocks", List.of()).forGetter(SeedInfo::needBlocks)
    ).apply(inst, SeedInfo::new));

    public SeedInfo {
        results = List.copyOf(results);
        needBlocks = List.copyOf(needBlocks);
    }

    public SeedInfo(Holder<Item> seed, int stage) {
        this(seed, List.of(seed), stage, List.of());
    }

    public SeedInfo(Holder<Item> seed, List<Holder<Item>> results, int stage) {
        this(seed, results, stage, List.of());
    }
}
