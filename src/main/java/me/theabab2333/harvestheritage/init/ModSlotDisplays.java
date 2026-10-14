package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.integration.jei.slot.BlockTagSlotDisplay;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

// ono，我只是为了 jei 里面的 BlockTagSlotDisplay 才注册的这个，以后有多方块温室了再说吧
public class ModSlotDisplays {
    public static final DeferredRegister<SlotDisplay.Type<?>> SLOT_DISPLAY_TYPES = DeferredRegister.create(
        Registries.SLOT_DISPLAY, HarvestHeritage.MOD_ID
    );

    public static final DeferredHolder<SlotDisplay.Type<?>, SlotDisplay.Type<BlockTagSlotDisplay>> BLOCK_TAG =
        SLOT_DISPLAY_TYPES.register("block_tag", () -> BlockTagSlotDisplay.TYPE);

    public static void register(IEventBus eventBus) {
        SLOT_DISPLAY_TYPES.register(eventBus);
    }
}
