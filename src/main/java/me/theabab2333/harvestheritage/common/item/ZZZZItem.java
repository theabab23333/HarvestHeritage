package me.theabab2333.harvestheritage.common.item;

import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import me.theabab2333.harvestheritage.util.StyleUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ZZZZItem extends Item implements IHasTooltips {

    public ZZZZItem(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        return List.of(Component.translatable("item.harvestheritage.zzzz.tooltip").withStyle(StyleUtil.colorFromRatio(0xf6d365, 0xfda085)));
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return Component.translatable("item.harvestheritage.zzzz").setStyle(StyleUtil.colorFromRatio());
    }
}
