package me.theabab2333.harvestheritage.common.block;

import me.theabab2333.harvestheritage.common.block.entity.CropStandStandBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class CropStandBlock extends BaseCropStandBlock {
    private static final VoxelShape AABB = Block.box(0, -0.95, 0, 16, 15, 16);

    public CropStandBlock(Properties properties) {
        properties.noCollision().noOcclusion().sound(SoundType.WOOD);
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CropStandStandBlockEntity(blockPos, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        List<Component> components = new ArrayList<>();
        components.add(Component.translatable("block.harvestheritage.crop_stand.tooltip.1").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("block.harvestheritage.crop_stand.tooltip.2").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("block.harvestheritage.crop_stand.tooltip.3").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("block.harvestheritage.crop_stand.tooltip.4").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("block.harvestheritage.crop_stand.tooltip.5").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("block.harvestheritage.crop_stand.tooltip.6").withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public boolean hasShiftKeyDown() {
        return true;
    }
}
