package me.theabab2333.harvestheritage.datagen.definition;

import me.theabab2333.harvestheritage.init.seed.ModSeedRegistry;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import me.theabab2333.harvestheritage.init.seed.SeedDefinition;
import net.minecraft.core.RegistrySetBuilder;

// 值得庆幸的是，这样做可以让 neoforge 的数据加载条件控制某种子袋是否加载，很棒。至于其他问题我暂时没发现(没考虑到)
public class SeedDefinitionProvider {
    public static RegistrySetBuilder build() {
        return new RegistrySetBuilder().add(
            ModSeedRegistry.SEED_REGISTRY, context -> {
                for (SeedDefinition definition : ModSeeds.definitions()) {
                    context.register(definition.key(), definition.toSeedInfo());
                }
            }
        );
    }
}
