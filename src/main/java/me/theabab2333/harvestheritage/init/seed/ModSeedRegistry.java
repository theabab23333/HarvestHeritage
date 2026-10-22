package me.theabab2333.harvestheritage.init.seed;

import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.NewDatapackRegistryEvent;

// 哈哈数据包（）（）
public class ModSeedRegistry {

    public static final ResourceKey<Registry<SeedInfo>> SEED_REGISTRY = ResourceKey.createRegistryKey(Identifier.parse("seed"));

    public static void register(IEventBus eventBus) {
        eventBus.addListener(
            NewDatapackRegistryEvent.class,
            event -> event.worldRegistry(SEED_REGISTRY, SeedInfo.CODEC)
        );
    }
}
