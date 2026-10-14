package me.theabab2333.harvestheritage.common.block.entity;

import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModBlockEntities;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class ScaffoldingCropStandBlockEntity extends BaseCropStandBlockEntity {
    public ScaffoldingCropStandBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.SCAFFOLDING_CROP_STAND_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (this.seedPacketComponent == null) return;

        int speed = seedPacketComponent.speed();
        var seedInfo = SeedUtil.getSeedInfo(this.seedPacketComponent.seed());
        if (seedInfo == null) return;

        if (!SeedUtil.matchesGrowBlocks(level, pos, seedInfo.needBlocks())) return;

        int needStage = seedInfo.stage();

        if (random.nextInt(3) < speed) {
            if (this.stage < needStage) {
                this.stage++;
                setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    @Override
    public void find(ServerLevel level, BlockPos pos) {

    }

    @Override
    public void hybrid(
        BaseCropStandBlockEntity cropStandBlock,
        SeedPacketComponent component1,
        SeedPacketComponent component2,
        ServerLevel level
    ) {

    }
}
