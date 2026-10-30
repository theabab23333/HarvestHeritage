package me.theabab2333.harvestheritage;

import lombok.Getter;
import me.theabab2333.harvestheritage.api.integration.IntegrationManager;
import me.theabab2333.harvestheritage.init.ModConfigs;
import me.theabab2333.harvestheritage.init.ModCreativeModeTab;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.init.ModSlotDisplays;
import me.theabab2333.harvestheritage.init.block.ModBlockEntities;
import me.theabab2333.harvestheritage.init.block.ModBlocks;
import me.theabab2333.harvestheritage.init.component.ModDataComponentPredicates;
import me.theabab2333.harvestheritage.init.component.ModDataComponents;
import me.theabab2333.harvestheritage.init.fluid.ModFluidTypes;
import me.theabab2333.harvestheritage.init.fluid.ModFluids;
import me.theabab2333.harvestheritage.init.seed.ModSeedRegistry;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.progress.StartupNotificationManager;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// var~我乱用的
@Mod(HarvestHeritage.MOD_ID)
public class HarvestHeritage {
    public static final String MOD_ID = "harvestheritage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Getter
    private static final IntegrationManager integrationManager = new IntegrationManager();

    public HarvestHeritage(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::commonSetup);

        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModItems.register(eventBus);
        ModFluids.register(eventBus);
        ModFluidTypes.register(eventBus);
        ModCreativeModeTab.register(eventBus);
        ModDataComponents.register(eventBus);
        ModDataComponentPredicates.register(eventBus);
        ModRecipes.register(eventBus);
        ModSeedRegistry.register(eventBus);
        ModSlotDisplays.register(eventBus);

        NeoForge.EVENT_BUS.register(this);

        ModConfigs.register(modContainer);

        StartupNotificationManager.addModMessage("[HarvestHeritage] Loading Integrations");
        integrationManager.compileContent();
        integrationManager.loadAllIntegrations();
        StartupNotificationManager.addModMessage("[HarvestHeritage] Integrations Load Complete");

        LOGGER.info("Hey Player, your portal opening day is over. Give me the Crash.");
        LOGGER.info("If you want it, then you'll have to take it, as you have already known.");
    }

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
