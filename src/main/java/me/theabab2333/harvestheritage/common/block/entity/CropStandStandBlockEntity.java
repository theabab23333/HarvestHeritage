package me.theabab2333.harvestheritage.common.block.entity;

import me.theabab2333.harvestheritage.init.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CropStandStandBlockEntity extends BaseCropStandBlockEntity {
    public CropStandStandBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.CROP_STAND_BLOCK_ENTITY.get(), worldPosition, blockState);
    }
}
