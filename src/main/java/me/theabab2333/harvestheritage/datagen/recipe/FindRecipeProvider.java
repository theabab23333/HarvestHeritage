package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.recipe.FindRecipe;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.List;

public class FindRecipeProvider extends ModRecipeProvider {

    protected FindRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    private static final Identifier FIND = HarvestHeritage.of("find/");

    private void buildFindSeeds(RecipeOutput output) {
        ModSeeds.packetSeeds().forEach(definition -> {
            DataComponentPatch patch = SeedUtil.createSeedPacketPatch(definition.seed());
            ItemStackTemplate stack = new ItemStackTemplate(ModItems.KNOWN_SEED.get(), 1, patch);
            FindRecipe.Builder.builder(ModItems.UNKNOWN_SEED, List.of(stack))
                .save(output, FIND.withSuffix(SeedUtil.getPath(definition.seed().value())));
        });
    }

    @Override
    protected void buildRecipes() {
        this.buildFindSeeds(output);

        FindRecipe.Builder.builder(ModItems.MAGNIFYING_GLASS, ModItems.ZZZZ)
            .save(output, FIND.withSuffix(SeedUtil.getPath(ModItems.ZZZZ.asItem())));
    }
}
