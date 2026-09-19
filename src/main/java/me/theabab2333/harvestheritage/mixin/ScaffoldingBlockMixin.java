package me.theabab2333.harvestheritage.mixin;

import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static me.theabab2333.harvestheritage.common.block.ScaffoldingCropStandBlock.BOTTOM;
import static me.theabab2333.harvestheritage.common.block.ScaffoldingCropStandBlock.SHAPE_STABLE;
import static me.theabab2333.harvestheritage.common.block.ScaffoldingCropStandBlock.SHAPE_UNSTABLE;
import static net.minecraft.world.level.block.ScaffoldingBlock.DISTANCE;

@Mixin(ScaffoldingBlock.class)
public abstract class ScaffoldingBlockMixin extends Block {
    public ScaffoldingBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * @author theabab2333
     * @reason 统一脚手架与悬挂式作物架
     */
    @Overwrite
    public static int getDistance(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos relativePos = pos.mutable().move(Direction.DOWN);
        BlockState belowState = level.getBlockState(relativePos);
        int distance = 7;
        if (belowState.is(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK) || belowState.is(Blocks.SCAFFOLDING)) {
            distance = belowState.getValue(DISTANCE);
        } else if (belowState.isFaceSturdy(level, relativePos, Direction.UP)) {
            return 0;
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState relativeState = level.getBlockState(relativePos.setWithOffset(pos, direction));
            if (relativeState.is(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.get()) || relativeState.is(Blocks.SCAFFOLDING)) {
                distance = Math.min(distance, relativeState.getValue(DISTANCE) + 1);
                if (distance == 1) {
                    break;
                }
            }
        }
        return distance;
    }

    /**
     * @author theabab2333
     * @reason 统一脚手架与悬挂式作物架
     */
    @Overwrite
    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, CollisionContext context) {
        if (context.isHoldingItem(state.getBlock().asItem())) {
            return Shapes.block();
        } else if (ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.isBound() && context.isHoldingItem(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.asItem())) {
            return Shapes.block();
        } else if (ModItems.SEED_PACKET.isBound() && context.isHoldingItem(ModItems.SEED_PACKET.asItem())) {
            return Shapes.block();
        } else {
            return state.getValue(BOTTOM) ? SHAPE_UNSTABLE : SHAPE_STABLE;
        }
    }
}
