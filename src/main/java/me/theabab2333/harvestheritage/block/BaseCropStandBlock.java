package me.theabab2333.harvestheritage.block;

import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.block.entity.BaseCropStandBlockEntity;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.item.GrassShearItem;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BaseCropStandBlock extends Block implements EntityBlock, IHasTooltips {
    public BaseCropStandBlock(Properties properties) {
        properties.randomTicks();
        super(properties);
    }

    @Override
    protected BlockState updateShape(
        BlockState state,
        LevelReader level,
        ScheduledTickAccess ticks,
        BlockPos pos,
        Direction directionToNeighbour,
        BlockPos neighbourPos,
        BlockState neighbourState,
        RandomSource random
    ) {
        return !state.canSurvive(level, pos)
               ? Blocks.AIR.defaultBlockState()
               : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BaseCropStandBlockEntity blockEntity = (BaseCropStandBlockEntity) level.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.tick(level, pos, state, random);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return belowState.is(Blocks.FARMLAND);
    }

    @Override
    @SuppressWarnings("ConstantValue")
    protected InteractionResult useItemOn(
        ItemStack itemStack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hitResult
    ) {
        BaseCropStandBlockEntity blockEntity = (BaseCropStandBlockEntity) level.getBlockEntity(pos);
        if (blockEntity == null) return InteractionResult.FAIL;
        SeedPacketComponent component = blockEntity.getSeedPacketComponent();

        switch (itemStack.getItem()) {
            case ISeedItem seedItem -> {
                blockEntity.seedUseOn(itemStack);
                return InteractionResult.SUCCESS;
            }

            case GrassShearItem grassShearItem -> {
                if (component == null) return InteractionResult.FAIL;
                DataComponentPatch patch = DataComponentPatch.builder()
                    .set(ModDataComponents.SEED_PACKET_COMPONENT.get(), component)
                    .build();

                ItemStack result = new ItemStack(ModItems.SEED_PACKET, 1, patch);
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), result);

                blockEntity.setSeedPacketComponent(null);
                blockEntity.setStage(0);
                blockEntity.setChanged();
                return InteractionResult.SUCCESS;
            }

            default -> {
                if (!itemStack.isEmpty()) return InteractionResult.FAIL;
                if (component == null) return InteractionResult.FAIL;

                var seedInfo = SeedUtil.getSeedInfo(component.seedComponent().seed().value());
                if (seedInfo != null && seedInfo.stage() == blockEntity.getStage()) {
                    NonNullList<ItemStack> itemStacks = getSeedOutput(component, level);
                    this.dropContents(level, pos, itemStacks);
                    blockEntity.setStage(0);
                    blockEntity.setChanged();
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.FAIL;
            }
        }
    }

    public static NonNullList<ItemStack> getSeedOutput(SeedPacketComponent component, Level level) {
        if (level.isClientSide()) return NonNullList.create();
        NonNullList<ItemStack> itemStacks = NonNullList.create();
        int output = component.output();
        RandomSource random = level.getRandom();
        int count = random.nextInt(output) + 1;
        var seedInfo = SeedUtil.getSeedInfo(component.seedComponent().seed().value());
        for (Item resultItem : seedInfo.results()) {
            itemStacks.add(new ItemStack(resultItem, count));
        }
        return itemStacks;
    }

    public void dropContents(Level level, BlockPos pos, NonNullList<ItemStack> itemStacks) {
        Containers.dropContents(level, pos, itemStacks);
    }
}
