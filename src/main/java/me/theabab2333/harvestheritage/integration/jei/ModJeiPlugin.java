package me.theabab2333.harvestheritage.integration.jei;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.event.ModRecipeReloadAndSyncEvent;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.integration.jei.category.FindRecipeCategory;
import me.theabab2333.harvestheritage.integration.jei.category.HybridRecipeCategory;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final Supplier<IRecipeHolderType<FindRecipe>> FIND_TYPE = IRecipeHolderType.createDeferred(ModRecipes.FIND_TYPE);
    public static final Supplier<IRecipeHolderType<HybridRecipe>> HYBRID_TYPE = IRecipeHolderType.createDeferred(ModRecipes.HYBRID_TYPE);

    @Override
    public Identifier getPluginUid() {
        return HarvestHeritage.of("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new FindRecipeCategory(guiHelper), new HybridRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(FIND_TYPE.get(), ModRecipeReloadAndSyncEvent.FIND_SEED_RECIPES);
        registration.addRecipes(HYBRID_TYPE.get(), ModRecipeReloadAndSyncEvent.HYBRID_RECIPES);
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
                SeedComponent seed = stack.get(ModDataComponents.SEED_COMPONENT);
                return seed != null ? seed.seed().getKey() : null;
            }
        );

        registration.registerSubtypeInterpreter(
            ModItems.SEED_PACKET.get(), (stack, context) -> {
                SeedPacketComponent component = stack.get(ModDataComponents.SEED_PACKET_COMPONENT);
                if (component != null) {
                    return component.seedComponent().seed().getKey();
                }
                SeedComponent seed = stack.get(ModDataComponents.SEED_COMPONENT);
                return seed != null ? seed.seed().getKey() : null;
            }
        );
    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {
        registration.addExtraItemStacks(ModSeeds.getSeedPackets());
    }
}
