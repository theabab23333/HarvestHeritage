package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.recipe.builder.HybridRecipeBuilder;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import me.theabab2333.harvestheritage.init.seed.SeedDefinition;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HybridRecipeProvider extends ModRecipeProvider {
    private static final Identifier HYBRID = HarvestHeritage.of("hybrid/");

    public static final List<SeedDefinition> COMMON_SEEDS = ModSeeds.cropSeeds();

    protected HybridRecipeProvider(
        BootstrapContext<Recipe<?>> recipes,
        BootstrapContext<Advancement> advancements
    ) {
        super(recipes, advancements);
    }

    @Override
    protected void buildRecipes() {
        // common seeds
        this.buildCommonSeeds();

        // mob
        this.buildSeed(ModSeeds.CHICKEN, ModSeeds.SUGAR_CANE, ModSeeds.SLIME_BALL);
        this.buildSeed(ModSeeds.SLIME_BALL, ModSeeds.CHORUS_FRUIT, ModSeeds.ENDER_PEARL);
        this.buildSeed(ModSeeds.MUTTON, ModSeeds.SWEET_BERRIES, ModSeeds.STRING);
        this.buildSeed(ModSeeds.HONEYCOMB, ModSeeds.SLIME_BALL, ModSeeds.MAGMA_CREAM);
        this.buildSeed(ModSeeds.MAGMA_CREAM, ModSeeds.BREEZE_ROD, ModSeeds.BLAZE_ROD);
        this.buildSeed(ModSeeds.KELP, ModSeeds.CHICKEN, ModSeeds.BREEZE_ROD);
        this.buildSeed(ModSeeds.AMETHYST_SHARD, ModSeeds.ENDER_PEARL, ModSeeds.ECHO_SHARD);
        this.buildSeed(ModSeeds.MAGMA_CREAM, ModSeeds.SUGAR_CANE, ModSeeds.GUNPOWDER);
        this.buildSeed(ModSeeds.INK_SAC, ModSeeds.KELP, ModSeeds.PRISMARINE_CRYSTALS);
        this.buildSeed(ModSeeds.SLIME_BALL, ModSeeds.STRING, ModSeeds.SPIDER_EYE);
        this.buildSeed(ModSeeds.SPIDER_EYE, ModSeeds.GUNPOWDER, ModSeeds.GHAST_TEAR);
        this.buildSeed(ModSeeds.GHAST_TEAR, ModSeeds.STRING, ModSeeds.PHANTOM_MEMBRANE);
        this.buildSeed(ModSeeds.BEEF, ModSeeds.SPIDER_EYE, ModSeeds.ROTTEN_FLESH);
        this.buildSeed(ModSeeds.ROTTEN_FLESH, ModSeeds.GHAST_TEAR, ModSeeds.BONE);
        this.buildSeed(ModSeeds.PORKCHOP, ModSeeds.GLOW_BERRIES, ModSeeds.COOKED_PORKCHOP);
        this.buildSeed(ModSeeds.CHORUS_FRUIT, ModSeeds.ARMADILLO_SCUTE, ModSeeds.SHULKER_SHELL);

        // material
        this.buildSeed(ModSeeds.BONE, ModSeeds.GHAST_TEAR, ModSeeds.IRON_INGOT);
        this.buildSeed(ModSeeds.IRON_INGOT, ModSeeds.BREEZE_ROD, ModSeeds.COPPER_INGOT);
        this.buildSeed(ModSeeds.PORKCHOP, ModSeeds.IRON_INGOT, ModSeeds.GOLD_INGOT);
        this.buildSeed(ModSeeds.LAPIS_LAZULI, ModSeeds.PHANTOM_MEMBRANE, ModSeeds.AMETHYST_SHARD);
        this.buildSeed(ModSeeds.PRISMARINE_CRYSTALS, ModSeeds.GHAST_TEAR, ModSeeds.QUARTZ);
        this.buildSeed(ModSeeds.SEA_PICKLE, ModSeeds.KELP, ModSeeds.LAPIS_LAZULI);
        this.buildSeed(ModSeeds.BONE, ModSeeds.GUNPOWDER, ModSeeds.FLINT);
        this.buildSeed(ModSeeds.APPLE, ModSeeds.BLAZE_ROD, ModSeeds.COAL);
        this.buildSeed(ModSeeds.TURTLE_SCUTE, ModSeeds.AMETHYST_SHARD, ModSeeds.EMERALD);
        this.buildSeed(ModSeeds.SWEET_BERRIES, ModSeeds.LAPIS_LAZULI, ModSeeds.REDSTONE);
        this.buildSeed(ModSeeds.GLOW_BERRIES, ModSeeds.REDSTONE, ModSeeds.GLOWSTONE_DUST);
        this.buildSeed(ModSeeds.EMERALD, ModSeeds.COAL, ModSeeds.DIAMOND, ModSeeds.AMETHYST_SHARD, ModSeeds.QUARTZ);
        this.buildSeed(ModSeeds.DIAMOND, ModSeeds.ECHO_SHARD, ModSeeds.NETHERITE_SCRAP, ModSeeds.EMERALD, ModSeeds.DIAMOND, ModSeeds.GOLD_INGOT);
    }

    private void buildSeed(SeedDefinition input1, SeedDefinition input2, SeedDefinition... outputs) {
        if (outputs.length == 0) {
            throw new IllegalArgumentException(
                "Hybrid recipe of " + input1.seed().getRegisteredName()
                    + " and " + input2.seed().getRegisteredName() + " has no outputs"
            );
        }
        List<Holder<Item>> holders = List.of(input1.seed(), input2.seed());
        List<Holder<Item>> seeds = Arrays.stream(outputs).map(SeedDefinition::seed).toList();
        HybridRecipeBuilder.builder(holders, seeds)
            .save(this.output, HYBRID.withSuffix(SeedUtil.getPath(seeds.getFirst().value())));
    }

    private void buildCommonSeeds() {
        for (int i = 0; i < COMMON_SEEDS.size(); i++) {
            for (int j = i + 1; j < COMMON_SEEDS.size(); j++) {
                SeedDefinition input1 = COMMON_SEEDS.get(i);
                SeedDefinition input2 = COMMON_SEEDS.get(j);
                List<Holder<Item>> outputs = new ArrayList<>();
                for (SeedDefinition seed : COMMON_SEEDS) {
                    if (seed != input1 && seed != input2) {
                        outputs.add(seed.seed());
                    }
                }
                HybridRecipeBuilder.builder(List.of(input1.seed(), input2.seed()), outputs)
                    .save(this.output, HYBRID.withSuffix("common/").withSuffix(
                        SeedUtil.getPath(input1.seed().value()) + "_and_" + SeedUtil.getPath(input2.seed().value())
                    ));
            }
        }
    }
}
