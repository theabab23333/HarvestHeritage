package me.theabab2333.harvestheritage.event;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import org.jspecify.annotations.Nullable;

import java.util.List;

@EventBusSubscriber(modid = HarvestHeritage.MOD_ID)
public class ModTooltipEvent {
    @SubscribeEvent
    public static void register(AddAttributeTooltipsEvent event) {
        ItemStack itemStack = event.getStack();
        List<Component> tooltips = getTooltips(itemStack);
        if (tooltips != null) {
            tooltips.forEach(event::addTooltipLines);
        }
    }

    static @Nullable List<Component> getTooltips(ItemStack itemStack) {
        IHasTooltips tooltips = findTooltipHolder(itemStack);
        if (tooltips == null) return null;
        List<Component> lines = tooltips.getTooltip(itemStack);
        if (lines.isEmpty()) return null;

        if (tooltips.hasShiftKeyDown() && !Minecraft.getInstance().hasShiftDown()) {
            return List.of(Component.translatable("gui.harvestheritage.tooltip.shift").withStyle(ChatFormatting.GRAY));
        }

        return lines;
    }

    static @Nullable IHasTooltips findTooltipHolder(ItemStack itemStack) {
        if (itemStack.getItem() instanceof IHasTooltips item) return item;
        if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof IHasTooltips block) {
            return block;
        }
        return null;
    }
}
