package me.theabab2333.harvestheritage.event;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.block.BaseCropStandBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = HarvestHeritage.MODID)
public class ModFarmlandEvent {
    @SubscribeEvent
    public static void fallOn(BlockEvent.FarmlandTrampleEvent event) {
        var pos = event.getPos().above();
        if (event.getLevel().getBlockState(pos).getBlock() instanceof BaseCropStandBlock) {
            event.setCanceled(true);
        }
    }
}
