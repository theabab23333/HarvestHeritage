package me.theabab2333.harvestheritage.mixin;

import me.theabab2333.harvestheritage.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ScaffoldingBlockItem.class)
public abstract class ScaffoldingBlockItemMixin extends BlockItem {
    public ScaffoldingBlockItemMixin(Block block, Properties properties) {
        super(block, properties);
    }

    /**
     * @author theabab2333
     * @reason 统一脚手架与悬挂式作物架
     */
    @Overwrite
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState replacedState = level.getBlockState(pos);
        if (!replacedState.is(ModTags.ModBlockTags.SCAFFOLDING_BLOCKS)) {
            return ScaffoldingBlock.getDistance(level, pos) == 7 ? null : context;
        } else {
            Direction direction;
            if (context.isSecondaryUseActive()) {
                direction = context.isInside() ? context.getClickedFace().getOpposite() : context.getClickedFace();
            } else {
                direction = context.getClickedFace() == Direction.UP ? context.getHorizontalDirection() : Direction.UP;
            }

            int horizontalDistance = 0;
            BlockPos.MutableBlockPos placementPos = pos.mutable().move(direction);

            while (horizontalDistance < 7) {
                if (!level.isClientSide() && !level.isInWorldBounds(placementPos)) {
                    Player player = context.getPlayer();
                    int maxY = level.getMaxY();
                    if (player instanceof ServerPlayer serverPlayer && placementPos.getY() > maxY) {
                        serverPlayer.sendBuildLimitMessage(true, maxY);
                    }
                    break;
                }

                replacedState = level.getBlockState(placementPos);
                if (!replacedState.is(ModTags.ModBlockTags.SCAFFOLDING_BLOCKS)) {
                    if (replacedState.canBeReplaced(context)) {
                        return BlockPlaceContext.at(context, placementPos, direction);
                    }
                    break;
                }

                placementPos.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    horizontalDistance++;
                }
            }

            return null;
        }
    }
}