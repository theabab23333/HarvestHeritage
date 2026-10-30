package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.recipe.builder.FindRecipeBuilder;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class FindRecipeProvider extends ModRecipeProvider {

    protected FindRecipeProvider(
        BootstrapContext<Recipe<?>> recipes,
        BootstrapContext<Advancement> advancements
    ) {
        super(recipes, advancements);
    }

    private static final Identifier FIND = HarvestHeritage.of("find/");

    private void buildFindSeeds(RecipeOutput output) {
        ModSeeds.packetSeeds().forEach(definition -> {
            DataComponentPatch patch = SeedUtil.createSeedPacketPatch(definition.seed());
            ItemStackTemplate stack = new ItemStackTemplate(ModItems.KNOWN_SEED.get(), 1, patch);
            FindRecipeBuilder.builder(ModItems.UNKNOWN_SEED, List.of(stack))
                .save(output, FIND.withSuffix(SeedUtil.getPath(definition.seed().value())));
        });
    }

    @Override
    protected void buildRecipes() {
        this.buildFindSeeds(output);

        FindRecipeBuilder.builder(ModItems.MAGNIFYING_GLASS, ModItems.ZZZZ)
            .save(output, FIND.withSuffix(SeedUtil.getPath(ModItems.ZZZZ.asItem())));
    }
}
