package me.theabab2333.harvestheritage.integration.jei;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.common.recipe.FindRecipe;
import me.theabab2333.harvestheritage.common.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.event.ModRecipeReloadAndSyncEvent;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import me.theabab2333.harvestheritage.integration.jei.category.FindRecipeCategory;
import me.theabab2333.harvestheritage.integration.jei.category.HybridRecipeCategory;
import me.theabab2333.harvestheritage.integration.jei.category.SeedInfoCategory;
import me.theabab2333.harvestheritage.integration.jei.recipe.SeedInfoRecipe;
import me.theabab2333.harvestheritage.util.SeedUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

// steam?stream~
// var?~idk~
@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final Supplier<IRecipeHolderType<FindRecipe>> FIND_TYPE = IRecipeHolderType.createDeferred(ModRecipes.FIND_TYPE);
    public static final Supplier<IRecipeHolderType<HybridRecipe>> HYBRID_TYPE = IRecipeHolderType.createDeferred(ModRecipes.HYBRID_TYPE);

    public static final IRecipeType<SeedInfoRecipe> SEED_OUTPUT_TYPE = IRecipeType.create(
        HarvestHeritage.of("seed_output"),
        SeedInfoRecipe.class
    );

    @Override
    public Identifier getPluginUid() {
        return HarvestHeritage.of("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
            new FindRecipeCategory(guiHelper),
            new HybridRecipeCategory(guiHelper),
            new SeedInfoCategory(guiHelper)
        );
    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {
        registration.addExtraItemStacks(ModSeeds.getAllSeeds().stream().map(SeedUtil::getSeedPacket).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(FIND_TYPE.get(), ModItems.MAGNIFYING_GLASS);
        registration.addCraftingStation(HYBRID_TYPE.get(), ModBlocks.CROP_STAND_BLOCK);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(
            ModItems.KNOWN_SEED.get(), (stack, context) -> {
                SeedPacketComponent seed = SeedUtil.getSeedPacketComponent(stack);
                return seed != null ? seed.seed().key() : null;
            }
        );

        registration.registerSubtypeInterpreter(
            ModItems.SEED_PACKET.get(), (stack, context) -> {
                SeedPacketComponent component = SeedUtil.getSeedPacketComponent(stack);
                return component != null ? component.seed().key() : null;
            }
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(FIND_TYPE.get(), ModRecipeReloadAndSyncEvent.FIND_SEED_RECIPES);
        registration.addRecipes(HYBRID_TYPE.get(), ModRecipeReloadAndSyncEvent.HYBRID_RECIPES);

        List<SeedInfoRecipe> seedOutputRecipes = ModSeeds.getAllSeeds().stream().flatMap(seed -> {
            ItemStack seedPacket = SeedUtil.getSeedPacket(seed);
            SeedInfo info = SeedUtil.getSeedInfo(seed);
            if (info == null) return Stream.empty();
            return Stream.of(new SeedInfoRecipe(seedPacket, info));
        }).toList();
        registration.addRecipes(SEED_OUTPUT_TYPE, seedOutputRecipes);
    }
}
