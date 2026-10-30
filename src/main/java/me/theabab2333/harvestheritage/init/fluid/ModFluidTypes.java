package me.theabab2333.harvestheritage.init.fluid;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(
        NeoForgeRegistries.FLUID_TYPES,
        HarvestHeritage.MOD_ID
    );

    public static final DeferredHolder<FluidType, FluidType> GRAPE_JUICE_TYPE = FLUID_TYPES.register(
        "grape_juice",
        id -> new FluidType(FluidType.Properties.create()
            .descriptionId(Util.makeDescriptionId("fluid", id))
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .canSwim(false)
        ));

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
