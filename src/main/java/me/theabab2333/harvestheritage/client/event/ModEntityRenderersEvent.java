package me.theabab2333.harvestheritage.client.event;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.client.render.blockentity.ActivatorBlockRenderer;
import me.theabab2333.harvestheritage.client.render.blockentity.CropStandBlockEntityRenderer;
import me.theabab2333.harvestheritage.client.render.blockentity.ScaffoldingCropStandBlockEntityRenderer;
import me.theabab2333.harvestheritage.init.ModBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = HarvestHeritage.MOD_ID, value = Dist.CLIENT)
public class ModEntityRenderersEvent {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
            ModBlockEntities.CROP_STAND_BLOCK_ENTITY.get(),
            CropStandBlockEntityRenderer::new
        );

        event.registerBlockEntityRenderer(
            ModBlockEntities.SCAFFOLDING_CROP_STAND_BLOCK_ENTITY.get(),
            ScaffoldingCropStandBlockEntityRenderer::new
        );

        event.registerBlockEntityRenderer(
            ModBlockEntities.ACTIVATOR_BLOCK_ENTITY.get(),
            ActivatorBlockRenderer::new
        );
    }
}
