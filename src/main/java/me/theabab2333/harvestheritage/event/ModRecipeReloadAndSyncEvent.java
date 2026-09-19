package me.theabab2333.harvestheritage.event;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.datagen.listener.SeedDefinitionReloadListener;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.recipe.FindRecipe;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.recipe.SeedPacketRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = HarvestHeritage.MODID)
public class ModRecipeReloadAndSyncEvent {
    public static final List<RecipeHolder<FindRecipe>> FIND_SEED_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<SeedPacketRecipe>> SEED_PACKET_RECIPES = new ArrayList<>();
    public static final List<RecipeHolder<HybridRecipe>> HYBRID_RECIPES = new ArrayList<>();

    @SubscribeEvent
    public static void onAddServerReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(HarvestHeritage.of("seed_definitions"), new SeedDefinitionReloadListener());
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(ModRecipes.FIND_TYPE.get(), ModRecipes.SEED_PACKET_TYPE.get(), ModRecipes.HYBRID_TYPE.get());
    }

    @SubscribeEvent
    public static void recipesReceived(RecipesReceivedEvent event) {
        FIND_SEED_RECIPES.clear();
        SEED_PACKET_RECIPES.clear();
        HYBRID_RECIPES.clear();
        FIND_SEED_RECIPES.addAll(event.getRecipeMap().byType(ModRecipes.FIND_TYPE.get()));
        SEED_PACKET_RECIPES.addAll(event.getRecipeMap().byType(ModRecipes.SEED_PACKET_TYPE.get()));
        HYBRID_RECIPES.addAll(event.getRecipeMap().byType(ModRecipes.HYBRID_TYPE.get()));
    }

    @SubscribeEvent
    public static void clientLogOut(ClientPlayerNetworkEvent.LoggingOut event) {
        FIND_SEED_RECIPES.clear();
        SEED_PACKET_RECIPES.clear();
        HYBRID_RECIPES.clear();
    }
}
