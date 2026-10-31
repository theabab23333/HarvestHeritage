package me.theabab2333.harvestheritage.common.block;

import me.theabab2333.harvestheritage.common.block.entity.UpgradeBlockEntity;
import me.theabab2333.harvestheritage.init.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class UpgradeBlock extends BaseEntityBlock {
    public UpgradeBlock(Properties properties) {
        properties.noOcclusion();
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new UpgradeBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(
            type,
            ModBlockEntities.UPGRADE_BLOCK_ENTITY.get(),
            (_, _, _, blockEntity) -> blockEntity.tick()
        );
    }
}
