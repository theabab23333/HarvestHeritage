package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.recipe.FindRecipe;
import me.theabab2333.harvestheritage.common.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.common.recipe.SeedPacketRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(
        Registries.RECIPE_TYPE,
        HarvestHeritage.MODID
    );

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
        Registries.RECIPE_SERIALIZER,
        HarvestHeritage.MODID
    );

    public static final Supplier<RecipeType<FindRecipe>> FIND_TYPE = RECIPE_TYPES.register(
        "find_type",
        RecipeType::simple
    );
    public static final Supplier<RecipeSerializer<FindRecipe>> FIND_SERIALIZERS = RECIPE_SERIALIZERS.register(
        "find",
        () -> new RecipeSerializer<>(FindRecipe.MAP_CODEC, FindRecipe.STREAM_CODEC)
    );
    public static final Supplier<RecipeType<SeedPacketRecipe>> SEED_PACKET_TYPE = RECIPE_TYPES.register(
        "seed_packet_recipe",
        RecipeType::simple
    );
    public static final Supplier<RecipeSerializer<SeedPacketRecipe>> SEED_PACKET_SERIALIZERS = RECIPE_SERIALIZERS.register(
        "seed_packet",
        () -> new RecipeSerializer<>(SeedPacketRecipe.MAP_CODEC, SeedPacketRecipe.STREAM_CODEC)
    );
    public static final Supplier<RecipeType<HybridRecipe>> HYBRID_TYPE = RECIPE_TYPES.register(
        "hyprid",
        RecipeType::simple
    );
    public static final Supplier<RecipeSerializer<HybridRecipe>> HYBRID_SERIALIZERS = RECIPE_SERIALIZERS.register(
        "hyprid",
        () -> new RecipeSerializer<>(HybridRecipe.MAP_CODEC, HybridRecipe.STREAM_CODEC)
    );

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}