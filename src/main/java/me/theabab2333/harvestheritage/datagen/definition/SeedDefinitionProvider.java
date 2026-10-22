package me.theabab2333.harvestheritage.datagen.definition;

import me.theabab2333.harvestheritage.init.seed.ModSeedRegistry;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import me.theabab2333.harvestheritage.init.seed.SeedDefinition;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import net.minecraft.data.worldgen.BootstrapContext;
import net.neoforged.neoforge.data.event.DatapackRegistryGatherer;

// 值得庆幸的是，这样做可以让 neoforge 的数据加载条件控制某种子袋是否加载，很棒。至于其他问题我暂时没发现(没考虑到)
public class SeedDefinitionProvider {
    public static void bootstrap(BootstrapContext<SeedInfo> context) {
        for (SeedDefinition definition : ModSeeds.definitions()) {
            context.register(definition.key(), definition.toSeedInfo());
        }
    }

    public static void register(DatapackRegistryGatherer gatherer) {
        gatherer.add(ModSeedRegistry.SEED_REGISTRY, SeedDefinitionProvider::bootstrap);
    }
}
