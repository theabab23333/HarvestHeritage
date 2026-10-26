package me.theabab2333.harvestheritage.init.seed;

import lombok.Getter;
import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.common.seed.requirement.SeedBlockRequirement;
import me.theabab2333.harvestheritage.common.seed.requirement.SeedLightRequirement;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

// 注册种子的，嗯对
public final class SeedDefinition {
    private final ResourceKey<SeedInfo> key;
    private final Holder<Item> seed;
    private final List<Holder<Item>> results;
    private final int stage;
    private final List<SeedBlockRequirement> blockRequirements;
    private final SeedLightRequirement lightRequirement;
    @Getter
    private final boolean crop;
    @Getter
    private final boolean packet;

    private SeedDefinition(Builder builder) {
        this.key = builder.key;
        this.seed = builder.seed;
        this.results = List.copyOf(builder.results);
        this.stage = builder.stage;
        this.blockRequirements = List.copyOf(builder.blockRequirements);
        this.lightRequirement = builder.lightRequirement;
        this.crop = builder.crop;
        this.packet = builder.packet;
    }

    public SeedInfo toSeedInfo() {
        return new SeedInfo(
            this.seed,
            this.results,
            this.stage,
            this.blockRequirements,
            this.lightRequirement
        );
    }

    public ResourceKey<SeedInfo> key() {
        return this.key;
    }

    public static Builder seed(String name, ItemLike seed, Consumer<SeedDefinition> register) {
        return new Builder(name, itemHolder(seed), register);
    }

    public Holder<Item> seed() {
        return this.seed;
    }

    public int stage() {
        return this.stage;
    }

    public List<Holder<Item>> results() {
        return this.results;
    }

    public List<SeedBlockRequirement> blockRequirements() {
        return this.blockRequirements;
    }

    public SeedLightRequirement lightRequirement() {
        return this.lightRequirement;
    }

    public boolean isFind() {
        return this.packet;
    }

    private static Holder<Item> itemHolder(ItemLike item) {
        if (item instanceof DeferredItem<?> deferredItem) {
            return deferredItem;
        }
        return item.asItem().builtInRegistryHolder;
    }

    public static final class Builder {
        private final ResourceKey<SeedInfo> key;
        private final Holder<Item> seed;
        private final Consumer<SeedDefinition> register;
        private final List<SeedBlockRequirement> blockRequirements = new ArrayList<>();
        private final List<Holder<Item>> results = new ArrayList<>();
        private int stage;
        private SeedLightRequirement lightRequirement = SeedLightRequirement.any();
        private boolean crop;
        private boolean packet;

        private Builder(String name, Holder<Item> seed, Consumer<SeedDefinition> register) {
            this.key = ResourceKey.create(ModSeedRegistry.SEED_REGISTRY, HarvestHeritage.of(name));
            this.seed = seed;
            this.register = register;
        }

        public Builder stage(int stage) {
            this.stage = stage;
            return this;
        }

        public Builder result(ItemLike result) {
            this.results.add(itemHolder(result));
            return this;
        }

        public Builder need(Block block) {
            this.blockRequirements.add(SeedBlockRequirement.of(block.builtInRegistryHolder));
            return this;
        }

        public Builder need(Holder<Block> block) {
            this.blockRequirements.add(SeedBlockRequirement.of(block));
            return this;
        }

        public Builder needTag(TagKey<Block> blockTag) {
            this.blockRequirements.add(SeedBlockRequirement.of(blockTag));
            return this;
        }

        public Builder needLight(SeedLightRequirement lightRequirement) {
            this.lightRequirement = Objects.requireNonNull(lightRequirement, "lightRequirement");
            return this;
        }

        public Builder needLight(int level) {
            return this.needLight(SeedLightRequirement.of(level));
        }

        public Builder needLightAtLeast(int min) {
            return this.needLight(SeedLightRequirement.atLeast(min));
        }

        public Builder needLightAtMost(int max) {
            return this.needLight(SeedLightRequirement.atMost(max));
        }

        public Builder needLightBetween(int min, int max) {
            return this.needLight(SeedLightRequirement.between(min, max));
        }

        public Builder crop() {
            this.crop = true;
            return this;
        }

        public Builder packet() {
            this.packet = true;
            return this;
        }

        public SeedDefinition build() {
            if (this.results.isEmpty()) {
                throw new IllegalStateException("Seed definition " + this.key.identifier() + " must declare at least one result");
            }
            SeedDefinition definition = new SeedDefinition(this);
            this.register.accept(definition);
            return definition;
        }
    }
}
