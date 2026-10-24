package me.theabab2333.harvestheritage.common.block;

import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import me.theabab2333.harvestheritage.common.block.entity.ActivatorBlockEntity;
import me.theabab2333.harvestheritage.init.ModBlockEntities;
import me.theabab2333.harvestheritage.util.StyleUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ActivatorBlock extends BaseEntityBlock implements IHasTooltips {
    public ActivatorBlock(Properties properties) {
        properties.noOcclusion();
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LIT, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    protected void neighborChanged(
        BlockState state,
        Level level,
        BlockPos pos,
        Block block,
        @Nullable Orientation orientation,
        boolean movedByPiston
    ) {
        if (!level.isClientSide()) {
            boolean isLit = state.getValue(LIT);
            if (isLit != level.hasNeighborSignal(pos)) {
                if (isLit) {
                    level.scheduleTick(pos, this, 4);
                } else {
                    level.setBlock(pos, state.cycle(LIT), 2);
                }
            }
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT) && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.cycle(LIT), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ActivatorBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(
            type,
            ModBlockEntities.ACTIVATOR_BLOCK_ENTITY.get(),
            ActivatorBlockEntity::tick
        );
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        return List.of(
            Component.translatable("block.harvestheritage.activator.tooltip.1").setStyle(StyleUtil.colorFromRatio()),
//            StyleUtil.charByCharComponent(Component.translatable("block.harvestheritage.activator.tooltip.2"), 0xf093fb, 0xf5576c)
            Component.translatable("block.harvestheritage.activator.tooltip.2").withStyle(ChatFormatting.DARK_GRAY)
        );
    }
}
