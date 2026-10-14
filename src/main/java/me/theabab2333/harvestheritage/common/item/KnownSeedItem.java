package me.theabab2333.harvestheritage.common.item;

import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KnownSeedItem extends Item implements IHasTooltips {
    public KnownSeedItem(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        List<Component> list = new ArrayList<>();
        SeedPacketComponent packet = SeedUtil.getSeedPacketComponent(itemStack);
        if (packet == null) {
            Component component = Component.translatable("item.harvestheritage.seed.tooltip.fail").withStyle(ChatFormatting.DARK_RED);
            list.add(component);
        } else {
            list.add(Component.translatable("item.harvestheritage.seed.tooltip.seed", SeedUtil.getSeedName(packet.getSeed()))
                .withStyle(ChatFormatting.GREEN));
            SeedInfo info = SeedUtil.getSeedInfo(packet.seed());
            if (info != null) {
                list.add(Component.translatable("item.harvestheritage.seed.tooltip.stage", info.stage())
                    .withStyle(ChatFormatting.LIGHT_PURPLE));
            }
        }
        return list;
    }
}
