package me.theabab2333.harvestheritage.common.block.entity;

import me.theabab2333.harvestheritage.common.block.ActivatorBlock;
import me.theabab2333.harvestheritage.init.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ActivatorBlockEntity extends BlockEntity {

    public ActivatorBlockEntity(
        BlockPos worldPosition,
        BlockState blockState
    ) {
        super(ModBlockEntities.ACTIVATOR_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ActivatorBlockEntity blockEntity) {
        if (level.isClientSide()) return;
        if (!state.getValue(ActivatorBlock.LIT)) return;

        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.relative(direction);
            BlockState blockState = level.getBlockState(blockPos);
            blockState.randomTick((ServerLevel) level, blockPos, level.getRandom());
        }
    }
}
