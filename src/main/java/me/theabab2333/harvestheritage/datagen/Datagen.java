package me.theabab2333.harvestheritage.datagen;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.datagen.advancement.ModAdvancementProvider;
import me.theabab2333.harvestheritage.datagen.definition.SeedDefinitionProvider;
import me.theabab2333.harvestheritage.datagen.lang.ENUSProvider;
import me.theabab2333.harvestheritage.datagen.lang.ZHCNProvider;
import me.theabab2333.harvestheritage.datagen.loot.ModBlockLootProvider;
import me.theabab2333.harvestheritage.datagen.model.ModModelProvider;
import me.theabab2333.harvestheritage.datagen.recipe.ModRecipeProvider;
import me.theabab2333.harvestheritage.datagen.tag.ModBlockTagsProvider;
import me.theabab2333.harvestheritage.datagen.tag.ModItemTagsProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

@EventBusSubscriber(modid = HarvestHeritage.MOD_ID)
public class Datagen {
    @SubscribeEvent
    public static void gatherRegistryEntries(GatherDataRegistryEntriesEvent event) {
        // Datapack
        SeedDefinitionProvider.register(event);

        // Recipe
        event.recipe(ModRecipeProvider::create);

        // LootTable
        event.lootTable(new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new, LootContextParamSets.BLOCK));

        // Advancement
        event.advancement(ModAdvancementProvider::new);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        // Model
        event.createProvider(ModModelProvider::new);

        // Tag
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModBlockTagsProvider::new);

        // Lang
        event.createProvider(ENUSProvider::new);
        event.createProvider(ZHCNProvider::new);
    }
}