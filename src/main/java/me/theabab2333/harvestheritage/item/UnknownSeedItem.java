package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class UnknownSeedItem extends Item implements IHasTooltips {
    public UnknownSeedItem(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        return List.of(Component.translatable("item.harvestheritage.unknown_seed.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
