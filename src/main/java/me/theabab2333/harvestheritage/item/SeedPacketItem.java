package me.theabab2333.harvestheritage.item;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.block.entity.BaseCropStandBlockEntity;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class SeedPacketItem extends KnownSeedItem implements ISeedItem {
    public SeedPacketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.is(Blocks.SCAFFOLDING)) {
            if (!level.isClientSide()) {
                level.setBlock(blockPos, ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.get().defaultBlockState(), 3);
                if (level.getBlockEntity(blockPos) instanceof BaseCropStandBlockEntity blockEntity) {
                    blockEntity.seedUseOn(context.getItemInHand());
                    context.getItemInHand().shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        List<Component> list = new ArrayList<>();
        SeedPacketComponent packetComponent = itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT);
        if (packetComponent != null) {
            SeedComponent seedComponent = packetComponent.seedComponent();
            Item seedItem = seedComponent.getSeed();
            list.add(Component.translatable("item.harvestheritage.seed.tooltip.seed", SeedUtil.getSeedName(seedItem))
                .withStyle(ChatFormatting.GREEN));

            ModSeeds.SeedInfo info = ModSeeds.ALL_SEED.get(seedItem);
            if (info != null) {
                list.add(Component.translatable("item.harvestheritage.seed.tooltip.stage", info.stage())
                    .withStyle(ChatFormatting.LIGHT_PURPLE));
                StringBuilder resultBuilder = new StringBuilder();
                List<Item> results = info.results();
                for (int i = 0; i < results.size(); i++) {
                    if (i > 0) resultBuilder.append(", ");
                    resultBuilder.append(SeedUtil.getSeedName(results.get(i)).getString());
                }
                list.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.result", resultBuilder.toString())
                    .withStyle(ChatFormatting.YELLOW));
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
        if (itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT) instanceof SeedPacketComponent seedPacketComponent) {
            return seedPacketComponent.seedComponent().seed();
        } else if (itemStack.get(ModDataComponents.SEED_COMPONENT) instanceof SeedComponent(Holder<Item> seed)) {
            return seed;
        } else {
            return Items.AIR.builtInRegistryHolder();
        }
    }
}
