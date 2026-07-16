package me.theabab2333.harvestheritage.block.entity;

import me.theabab2333.harvestheritage.component.SeedPacketComponent;
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
        if (this.seedPacketComponent != null) {
            int speed = seedPacketComponent.speed();
            var seedInfo = SeedUtil.getSeedInfo(this.seedPacketComponent.seedComponent().getSeed());
            if (seedInfo == null) return;
            int needStage = seedInfo.stage();
            if (random.nextInt(3) < speed) {
                if (this.stage < needStage) {
                    this.stage++;
                    level.sendBlockUpdated(pos, state, state, 3);
                }
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
