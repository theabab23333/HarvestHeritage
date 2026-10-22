package me.theabab2333.harvestheritage.datagen.advancement.predicate;

import com.mojang.serialization.MapCodec;
import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModConfigs;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import net.minecraft.advancements.predicates.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;

public record SeedPacketMaxPredicate() implements SingleComponentItemPredicate<SeedPacketComponent> {
    public static final MapCodec<SeedPacketMaxPredicate> CODEC = MapCodec.unit(new SeedPacketMaxPredicate());

    @Override
    public DataComponentType<SeedPacketComponent> componentType() {
        return ModDataComponents.SEED_PACKET_COMPONENT.get();
    }

    @Override
    public boolean matches(SeedPacketComponent component) {
        return component.speed() == ModConfigs.SEED_SPEED_MAX.get()
               && component.output() == ModConfigs.OUTPUT_MAX.get();
    }
}
