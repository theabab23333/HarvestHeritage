package me.theabab2333.harvestheritage.datagen.advancement;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.datagen.advancement.predicate.SeedPacketMaxPredicate;
import me.theabab2333.harvestheritage.init.ModDataComponentPredicates;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementSubProvider {
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder WELCOME = Advancement.Builder.advancement()
            .display(
                ModItems.GRASS_SHEAR,
                Component.translatable("advancement.harvestheritage.welcome.title"),
                Component.translatable("advancement.harvestheritage.welcome.description"),
                Identifier.withDefaultNamespace("gui/advancements/backgrounds/husbandry"),
                AdvancementType.TASK,
                false,
                false,
                false
            )
            .addCriterion("join", PlayerTrigger.TriggerInstance.tick())
            .save(consumer, HarvestHeritage.of("welcome"));

        Advancement.Builder.advancement()
            .display(
                ModItems.KFC,
                Component.translatable("advancement.harvestheritage.kfc.title").withColor(0xf8d4d2),
                Component.translatable("advancement.harvestheritage.kfc.description").withColor(0xf8d4d2),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "consume_kfc",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                    provider.lookupOrThrow(Registries.ITEM),
                    ModItems.KFC.asItem()
                )
            )
            .parent(WELCOME)
            .save(consumer, HarvestHeritage.of("kfc"));

        Advancement.Builder.advancement()
            .display(
                ModItems.FRIED_SEEDSACK,
                Component.translatable("advancement.harvestheritage.fride_seedsack.title"),
                Component.translatable("advancement.harvestheritage.fride_seedsack.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "consume_fride_seedsack",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                    provider.lookupOrThrow(Registries.ITEM),
                    ModItems.FRIED_SEEDSACK.asItem()
                )
            )
            .parent(WELCOME)
            .save(consumer, HarvestHeritage.of("fride_seedsack"));

        Advancement.Builder.advancement()
            .display(
                ModItems.SEED_PACKET,
                Component.translatable("advancement.harvestheritage.max_seed_packet.title").withColor(0xfa709a),
                Component.translatable("advancement.harvestheritage.max_seed_packet.description").withColor(0xf5576c),
                null,
                AdvancementType.CHALLENGE,
                true,
                true,
                false
            )
            .addCriterion(
                "obtain_max_seed_packet",
                InventoryChangeTrigger.TriggerInstance.hasItems(
                    ItemPredicate.Builder.item()
                        .of(provider.lookupOrThrow(Registries.ITEM), ModItems.SEED_PACKET)
                        .withComponents(
                            DataComponentMatchers.Builder.components()
                                .partial(
                                    ModDataComponentPredicates.getSeedPacketMaxType(),
                                    new SeedPacketMaxPredicate()
                                )
                                .build()
                        )
                )
            )
            .parent(WELCOME)
            .save(consumer, HarvestHeritage.of("max_seed_packet"));

        Advancement.Builder.advancement()
            .display(
                ModItems.MAGNIFYING_GLASS,
                Component.translatable("advancement.harvestheritage.magnifying_glass.title"),
                Component.translatable("advancement.harvestheritage.magnifying_glass.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "find_it",
                InventoryChangeTrigger.TriggerInstance.hasItems(
                    ModItems.MAGNIFYING_GLASS
                )
            )
            .parent(WELCOME)
            .save(consumer, HarvestHeritage.of("find_it"));
    }
}
