package me.theabab2333.harvestheritage.datagen.advancement;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.datagen.advancement.predicate.SeedPacketMaxPredicate;
import me.theabab2333.harvestheritage.init.ModDataComponentPredicates;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.ConsumeItemTrigger;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class ModAdvancementProvider extends AdvancementSubProvider {
    public ModAdvancementProvider(BootstrapContext<Advancement> output) {
        super(output);
    }

    @Override
    public void generate() {
        HolderGetter<Item> items = this.output.lookup(Registries.ITEM);

        AdvancementHolder welcome = Advancement.Builder.advancement()
            .rootDisplay(
                ModItems.GRASS_SHEAR.get(),
                Component.translatable("advancement.harvestheritage.welcome.title"),
                Component.translatable("advancement.harvestheritage.welcome.description"),
                Identifier.withDefaultNamespace("gui/advancements/backgrounds/husbandry"),
                AdvancementType.TASK,
                false,
                false,
                false
            )
            .addCriterion("join", PlayerTrigger.TriggerInstance.tick())
            .save(this.output, HarvestHeritage.of("welcome").toString());

        Advancement.Builder.advancement()
            .display(
                ModItems.KFC.get(),
                Component.translatable("advancement.harvestheritage.kfc.title").withColor(0xf8d4d2),
                Component.translatable("advancement.harvestheritage.kfc.description").withColor(0xf8d4d2),
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "consume_kfc",
                ConsumeItemTrigger.TriggerInstance.usedItem(items, ModItems.KFC.get())
            )
            .parent(welcome)
            .save(this.output, HarvestHeritage.of("kfc").toString());

        Advancement.Builder.advancement()
            .display(
                ModItems.FRIED_SEEDSACK.get(),
                Component.translatable("advancement.harvestheritage.fride_seedsack.title"),
                Component.translatable("advancement.harvestheritage.fride_seedsack.description"),
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "consume_fride_seedsack",
                ConsumeItemTrigger.TriggerInstance.usedItem(items, ModItems.FRIED_SEEDSACK.get())
            )
            .parent(welcome)
            .save(this.output, HarvestHeritage.of("fride_seedsack").toString());

        Advancement.Builder.advancement()
            .display(
                ModItems.SEED_PACKET.get(),
                Component.translatable("advancement.harvestheritage.max_seed_packet.title").withColor(0xfa709a),
                Component.translatable("advancement.harvestheritage.max_seed_packet.description").withColor(0xf5576c),
                AdvancementType.CHALLENGE,
                true,
                true,
                false
            )
            .addCriterion(
                "obtain_max_seed_packet",
                InventoryChangeTrigger.TriggerInstance.hasItems(
                    ItemPredicate.Builder.item()
                        .of(items, ModItems.SEED_PACKET.get())
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
            .parent(welcome)
            .save(this.output, HarvestHeritage.of("max_seed_packet").toString());

        Advancement.Builder.advancement()
            .display(
                ModItems.MAGNIFYING_GLASS.get(),
                Component.translatable("advancement.harvestheritage.magnifying_glass.title"),
                Component.translatable("advancement.harvestheritage.magnifying_glass.description"),
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "find_it",
                InventoryChangeTrigger.TriggerInstance.hasItems(
                    ModItems.MAGNIFYING_GLASS.get()
                )
            )
            .parent(welcome)
            .save(this.output, HarvestHeritage.of("find_it").toString());
    }
}