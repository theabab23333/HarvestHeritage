package me.theabab2333.harvestheritage.init.fluid;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, HarvestHeritage.MOD_ID);

    // 按理说这里注册流体应该像 nfdocs 里面把 BaseFlowingFluid.Source 和 Flowing 都写一遍
    // 但是我只写了 Source ，我寻思能用
    public static final DeferredHolder<Fluid, Fluid> GRAPE_JUICE = FLUIDS.register(
        "grape_juice",
        () -> new BaseFlowingFluid.Source(ModFluids.GRAPE_JUICE_PROPERTIES)
    );

    public static final BaseFlowingFluid.Properties GRAPE_JUICE_PROPERTIES = new BaseFlowingFluid.Properties(
        ModFluidTypes.GRAPE_JUICE_TYPE,
        GRAPE_JUICE,
        GRAPE_JUICE
    );

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
