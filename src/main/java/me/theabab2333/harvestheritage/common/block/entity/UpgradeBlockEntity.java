package me.theabab2333.harvestheritage.common.block.entity;

import me.theabab2333.harvestheritage.init.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UpgradeBlockEntity extends BlockEntity {
    public UpgradeBlockEntity(
        BlockPos worldPosition,
        BlockState blockState
    ) {
        super(ModBlockEntities.UPGRADE_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    public void tick() {

    }
}
