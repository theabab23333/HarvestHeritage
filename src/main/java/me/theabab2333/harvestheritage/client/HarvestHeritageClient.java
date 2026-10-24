package me.theabab2333.harvestheritage.client;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = HarvestHeritage.MOD_ID, dist = Dist.CLIENT)
public class HarvestHeritageClient {
    public HarvestHeritageClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(this::registerItemModel);
        modEventBus.addListener(this::onClientSetup);
    }

    private void registerItemModel(RegisterItemModelsEvent event) {
        // WIP
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        HarvestHeritage.getIntegrationManager().loadAllClientIntegrations();
    }
}
