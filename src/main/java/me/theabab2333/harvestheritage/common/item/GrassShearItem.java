package me.theabab2333.harvestheritage.common.item;

import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class GrassShearItem extends Item implements IHasTooltips {
    public GrassShearItem(Properties properties) {
        properties.stacksTo(1);
        super(properties);
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState state, BlockPos pos, LivingEntity owner) {
        if (!level.isClientSide()) {
            if (state.is(ModTags.ModBlockTags.CAN_SHEAR)) {
                RandomSource random = level.getRandom();
                if (random.nextFloat() < 0.07f) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ModItems.UNKNOWN_SEED.toStack());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        return List.of(Component.translatable("item.harvestheritage.grass_shear.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
