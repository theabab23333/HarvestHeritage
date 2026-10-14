package me.theabab2333.harvestheritage.client;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.client.render.item.SeedPacketItemModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = HarvestHeritage.MOD_ID, dist = Dist.CLIENT)
public class HarvestHeritageClient {
    public HarvestHeritageClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(this::registerItemModel);
    }

    private void registerItemModel(RegisterItemModelsEvent event) {
        event.register(HarvestHeritage.of("seed_packet"), SeedPacketItemModel.Unbaked.MAP_CODEC);
    }
}
