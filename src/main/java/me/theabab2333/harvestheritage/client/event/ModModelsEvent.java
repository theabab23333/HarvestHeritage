package me.theabab2333.harvestheritage.client.event;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.fluid.ModFluids;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSources;

@EventBusSubscriber(modid = HarvestHeritage.MOD_ID, value = Dist.CLIENT)
public class ModModelsEvent {
    @SubscribeEvent
    public static void registerFluidModels(RegisterFluidModelsEvent event) {
        // hahaha, 这样用.png
        event.register(
            new FluidModel.Unbaked(
                new Material(Identifier.withDefaultNamespace("block/water_still")),
                new Material(Identifier.withDefaultNamespace("block/water_flow")),
                new Material(Identifier.withDefaultNamespace("block/water_overlay")),
                FluidTintSources.constant(0xFFD57EEB)
            ),
            ModFluids.GRAPE_JUICE
        );
    }
}
