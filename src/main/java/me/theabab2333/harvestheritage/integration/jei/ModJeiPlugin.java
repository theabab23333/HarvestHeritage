package me.theabab2333.harvestheritage.integration.jei;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.component.SeedComponent;
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
import me.theabab2333.harvestheritage.util.SeedUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.function.Supplier;
import java.util.stream.Stream;

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

        var allSeeds = ModSeeds.CROP_SEED.keySet().stream().map(SeedUtil::getHolder).toList();
        var allOutputs = ModSeeds.CROP_SEED.keySet().stream().map(SeedUtil::getHolder).toList();
        var commonAll = new RecipeHolder<>(
            ResourceKey.create(Registries.RECIPE, HarvestHeritage.of("hyprid/common/all")),
            new HybridRecipe(allSeeds, allOutputs)
        );
        var hybrids = Stream.concat(
            ModRecipeReloadAndSyncEvent.HYBRID_RECIPES.stream()
                .filter(recipeHolder -> !recipeHolder.id().identifier().getPath().startsWith("hyprid/common/")), Stream.of(commonAll)
        ).toList();

        registration.addRecipes(HYBRID_TYPE.get(), hybrids);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(FIND_TYPE.get(), ModItems.MAGNIFYING_GLASS);
        registration.addCraftingStation(HYBRID_TYPE.get(), ModBlocks.CROP_STAND_BLOCK);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(
            ModItems.SEED_PACKET.get(), (stack, _) -> {
                var packetComponent = stack.get(ModDataComponents.SEED_PACKET_COMPONENT.get());
                if (packetComponent != null) {
                    return BuiltInRegistries.ITEM.getKey(packetComponent.seedComponent().seed().value()).toString();
                }
                SeedComponent component = stack.get(ModDataComponents.SEED_COMPONENT.get());
                if (component != null) {
                    return BuiltInRegistries.ITEM.getKey(component.seed().value()).toString();
                }
                return null;
            }
        );
        registration.registerFromDataComponentTypes(ModItems.KNOWN_SEED.get(), ModDataComponents.SEED_COMPONENT.get());
    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {
        registration.addExtraItemStacks(ModSeeds.getSeedPackets());
    }
}
