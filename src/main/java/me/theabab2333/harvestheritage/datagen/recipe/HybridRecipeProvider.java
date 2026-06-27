package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.theabab2333.harvestheritage.init.ModSeeds.CROP_SEED;
import static me.theabab2333.harvestheritage.util.SeedUtil.getHolder;
import static me.theabab2333.harvestheritage.util.SeedUtil.getPath;

public class HybridRecipeProvider extends ModRecipeProvider {
    protected HybridRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static final Identifier HYPRID = HarvestHeritage.of("hyprid/");

    public static final List<Item> COMMON_SEEDS = new ArrayList<>();

    static {
        CROP_SEED.forEach((item, _) -> COMMON_SEEDS.add(item));
    }

    @Override
    protected void buildRecipes() {
        // common seeds
        buildCommonSeeds();

        // mob
        buildSeed(List.of(Items.CHICKEN, Items.SUGAR_CANE), Items.SLIME_BALL);
        buildSeed(List.of(Items.SLIME_BALL, Items.CHORUS_FRUIT), Items.ENDER_PEARL);
        buildSeed(List.of(Items.MUTTON, Items.SWEET_BERRIES), Items.STRING);
        buildSeed(List.of(Items.HONEYCOMB, Items.SLIME_BALL), Items.MAGMA_CREAM);
        buildSeed(List.of(Items.MAGMA_CREAM, Items.BREEZE_ROD), Items.BLAZE_ROD);
        buildSeed(List.of(Items.KELP, Items.CHICKEN), Items.BREEZE_ROD);
        buildSeed(List.of(Items.AMETHYST_SHARD, Items.ENDER_PEARL), Items.ECHO_SHARD);
        buildSeed(List.of(Items.MAGMA_CREAM, Items.SUGAR_CANE), Items.GUNPOWDER);
        buildSeed(List.of(Items.INK_SAC, Items.KELP), Items.PRISMARINE_CRYSTALS);
        buildSeed(List.of(Items.SLIME_BALL, Items.STRING), Items.SPIDER_EYE);
        buildSeed(List.of(Items.SPIDER_EYE, Items.GUNPOWDER), Items.GHAST_TEAR);
        buildSeed(List.of(Items.GHAST_TEAR, Items.STRING), Items.PHANTOM_MEMBRANE);
        buildSeed(List.of(Items.BEEF, Items.SPIDER_EYE), Items.ROTTEN_FLESH);
        buildSeed(List.of(Items.ROTTEN_FLESH, Items.GHAST_TEAR), Items.BONE);
        buildSeed(List.of(Items.PORKCHOP, Items.GLOW_BERRIES), Items.COOKED_PORKCHOP);
        buildSeed(List.of(Items.CHORUS_FRUIT, Items.ARMADILLO_SCUTE), Items.SHULKER_SHELL);

        // material
        buildSeed(List.of(Items.BONE, Items.GHAST_TEAR), Items.IRON_INGOT);
        buildSeed(List.of(Items.PORKCHOP, Items.IRON_INGOT), Items.GOLD_INGOT);
        buildSeed(List.of(Items.LAPIS_LAZULI, Items.PHANTOM_MEMBRANE), Items.AMETHYST_SHARD);
        buildSeed(List.of(Items.PRISMARINE_CRYSTALS, Items.GHAST_TEAR), Items.QUARTZ);
        buildSeed(List.of(Items.SEA_PICKLE, Items.KELP), Items.LAPIS_LAZULI);
        buildSeed(List.of(Items.BONE, Items.GUNPOWDER), Items.FLINT);
        buildSeed(List.of(Items.APPLE, Items.BLAZE_ROD), Items.COAL);
        buildSeed(List.of(Items.TURTLE_SCUTE, Items.AMETHYST_SHARD), Items.EMERALD);
        buildSeed(List.of(Items.SWEET_BERRIES, Items.LAPIS_LAZULI), Items.REDSTONE);
        buildSeed(List.of(Items.GLOW_BERRIES, Items.REDSTONE), Items.GLOWSTONE_DUST);
        buildSeed(List.of(Items.EMERALD, Items.COAL), Items.DIAMOND, Items.AMETHYST_SHARD, Items.QUARTZ);
        buildSeed(List.of(Items.DIAMOND, Items.ECHO_SHARD), Items.NETHERITE_SCRAP, Items.EMERALD, Items.DIAMOND, Items.GOLD_INGOT);
    }

    private void buildSeed(List<Item> inputs, Item... outputs) {
        if (inputs.size() != 2) {
            HarvestHeritage.LOGGER.warn("Datagen hyprid Recipe is exception! Inputs");
            return;
        }
        List<Holder<Item>> holders = new ArrayList<>();
        inputs.forEach(item -> holders.add(getHolder(item)));
        if (outputs.length == 0) {
            HarvestHeritage.LOGGER.warn("Datagen hyprid Recipe is exception! Outputs");
            return;
        }
        List<Holder<Item>> seeds = new ArrayList<>();
        Arrays.asList(outputs).forEach(seed -> seeds.add(getHolder(seed)));
        HybridRecipe.Builder.builder(holders, seeds).save(this.output, HYPRID.withSuffix(getPath(seeds.getFirst().value())));
    }

    private void buildCommonSeeds() {
        for (int i = 0; i < CROP_SEED.size(); i++) {
            for (int j = i + 1; j < COMMON_SEEDS.size(); j++) {
                Item input1 = COMMON_SEEDS.get(i);
                Item input2 = COMMON_SEEDS.get(j);
                List<Holder<Item>> outputs = new ArrayList<>();
                for (Item seed : COMMON_SEEDS) {
                    if (seed != input1 && seed != input2) {
                        outputs.add(getHolder(seed));
                    }
                }
                HybridRecipe.Builder.builder(List.of(getHolder(input1), getHolder(input2)), outputs)
                    .save(output, HYPRID.withSuffix("common/").withSuffix(getPath(input1) + "_and_" + getPath(input2)));
            }
        }
    }
}
