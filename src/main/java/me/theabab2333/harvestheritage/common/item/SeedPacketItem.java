package me.theabab2333.harvestheritage.common.item;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

// 或许还有人类
public class SeedPacketItem extends KnownSeedItem implements ISeedItem {
    public SeedPacketItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        var seed = new ItemStack(this.seed(itemStack));
        var seedName = seed.getComponents().getOrDefault(DataComponents.ITEM_NAME, CommonComponents.EMPTY);
        return Component.empty().append(seedName).append(" ").append(Component.translatable("item.harvestheritage.seed_packet"));
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        List<Component> list = new ArrayList<>();
        SeedPacketComponent packetComponent = SeedUtil.getSeedPacketComponent(itemStack);
        if (packetComponent != null) {
            Item seedItem = packetComponent.getSeed();
            list.add(Component.translatable("item.harvestheritage.seed.tooltip.seed", SeedUtil.getSeedName(seedItem))
                .withStyle(ChatFormatting.GREEN));

            SeedInfo info = SeedUtil.getSeedInfo(seedItem);
            if (info != null) {
                StringBuilder resultBuilder = new StringBuilder();
                List<Holder<Item>> results = info.results();
                for (int i = 0; i < results.size(); i++) {
                    if (i > 0) resultBuilder.append(", ");
                    resultBuilder.append(SeedUtil.getSeedName(results.get(i).value()).getString());
                }
                list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.result", resultBuilder.toString())
                    .withStyle(ChatFormatting.YELLOW));
                list.add(Component.translatable("item.harvestheritage.seed.tooltip.stage", info.stage())
                    .withStyle(ChatFormatting.LIGHT_PURPLE));
            }

            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.speed", packetComponent.speed())
                .withStyle(ChatFormatting.BLUE));
            list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.output", packetComponent.output())
                .withStyle(ChatFormatting.GOLD));
        } else {
            list.addAll(super.getTooltip(itemStack));
        }

        return list;
    }

    @Override
    public Holder<Item> seed(ItemStack itemStack) {
        SeedPacketComponent seedPacketComponent = SeedUtil.getSeedPacketComponent(itemStack);
        return seedPacketComponent != null ? seedPacketComponent.seed() : Items.AIR.builtInRegistryHolder;
    }
}
