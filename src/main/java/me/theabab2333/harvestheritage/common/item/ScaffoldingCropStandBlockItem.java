package me.theabab2333.harvestheritage.common.item;

import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

public class ScaffoldingCropStandBlockItem extends ScaffoldingBlockItem {
    public ScaffoldingCropStandBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @Nullable BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        return super.updatePlacementContext(context);
    }
}
