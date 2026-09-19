package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.datagen.advancement.predicate.SeedPacketMaxPredicate;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponentPredicates {
    private static final DeferredRegister<DataComponentPredicate.Type<?>> PREDICATE_TYPES =
        DeferredRegister.create(Registries.DATA_COMPONENT_PREDICATE_TYPE, HarvestHeritage.MODID);

    private static final DeferredHolder<DataComponentPredicate.Type<?>, DataComponentPredicate.ConcreteType<SeedPacketMaxPredicate>> SEED_PACKET_MAX =
        PREDICATE_TYPES.register(
            "seed_packet_max",
            () -> new DataComponentPredicate.ConcreteType<>(SeedPacketMaxPredicate.CODEC.codec())
        );

    public static DataComponentPredicate.ConcreteType<SeedPacketMaxPredicate> getSeedPacketMaxType() {
        return SEED_PACKET_MAX.get();
    }

    public static void register(IEventBus eventBus) {
        PREDICATE_TYPES.register(eventBus);
    }
}
