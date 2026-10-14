package me.theabab2333.harvestheritage.event;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@EventBusSubscriber(modid = HarvestHeritage.MOD_ID)
public class ModTagsUpdatedEvent {

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        if (!event.shouldUpdateStaticData()) return;
        ModSeeds.rebuild(event.getRegistries());
    }
}
