package me.theabab2333.harvestheritage.datagen.recipe;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.recipe.builder.SeedPacketRecipeBuilder;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import me.theabab2333.harvestheritage.init.seed.SeedDefinition;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class SeedPacketRecipeProvider extends ModRecipeProvider {
    private static final Identifier SEED_PACKET = HarvestHeritage.of("seedpacket/");

    protected SeedPacketRecipeProvider(
        BootstrapContext<Recipe<?>> recipes,
        BootstrapContext<Advancement> advancements
    ) {
        super(recipes, advancements);
    }

    @Override
    protected void buildRecipes() {
        for (SeedDefinition definition : ModSeeds.packetSeeds()) {
            var patch = SeedUtil.createSeedPacketPatch(definition.seed());

            SeedPacketRecipeBuilder.builder(
                Ingredient.of(ModItems.KNOWN_SEED),
                Ingredient.of(Items.PAPER),
                new ItemStackTemplate(ModItems.SEED_PACKET.asItem(), 1, patch)
            ).save(output, SEED_PACKET.withSuffix(SeedUtil.getPath(definition.seed().value())));
        }
    }
}
