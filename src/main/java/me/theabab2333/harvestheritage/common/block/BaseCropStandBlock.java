package me.theabab2333.harvestheritage.common.block;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.common.block.entity.BaseCropStandBlockEntity;
import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.common.item.GrassShearItem;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BaseCropStandBlock extends BaseEntityBlock {
    public BaseCropStandBlock(Properties properties) {
        properties.randomTicks();
        super(properties);
    }

    public static NonNullList<ItemStack> getSeedOutput(SeedPacketComponent component, Level level) {
        if (level.isClientSide()) return NonNullList.create();
        NonNullList<ItemStack> itemStacks = NonNullList.create();
        int output = component.output();
        RandomSource random = level.getRandom();

        int extraRolls = Math.max(0, output - 1);
        int count = 1 + extraRolls / 5;
        if (random.nextInt(5) < extraRolls % 5) {
            count++;
        }
        var seedInfo = SeedUtil.getSeedInfo(component.getSeed());
        if (seedInfo == null) return itemStacks;
        for (Holder<Item> resultItem : seedInfo.results()) {
            itemStacks.add(new ItemStack(resultItem, count));
        }
        return itemStacks;
    }

    // 哈哈，屎?!
    @Override
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

        switch (itemStack.getItem()) {
            case ISeedItem ignored -> seedPacketUseOn(blockEntity, itemStack);

            case GrassShearItem ignored -> shearGetSeedPacket(blockEntity, level, pos);

            default -> getOutput(blockEntity, level, pos);
        }

        return InteractionResult.SUCCESS;
    }

    public static void seedPacketUseOn(
        BaseCropStandBlockEntity blockEntity,
        ItemStack seedPacket
    ) {
        blockEntity.seedUseOn(seedPacket);
    }

    public static void shearGetSeedPacket(
        BaseCropStandBlockEntity blockEntity,
        Level level,
        BlockPos pos
    ) {
        SeedPacketComponent component = blockEntity.getSeedPacketComponent();
        if (component != null) {
            var patch = SeedUtil.createSeedPacketPatch(component);
            ItemStack result = new ItemStack(ModItems.SEED_PACKET, 1, patch);
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), result);
            blockEntity.setSeedPacketComponent(null);
            blockEntity.setStage(0);
            blockEntity.setChanged();
        }
    }

    public static void getOutput(
        BaseCropStandBlockEntity blockEntity,
        Level level,
        BlockPos pos
    ) {
        SeedPacketComponent component = blockEntity.getSeedPacketComponent();
        if (component != null) {
            var seedInfo = SeedUtil.getSeedInfo(component.getSeed());
            if (seedInfo != null && seedInfo.stage() == blockEntity.getStage()) {
                NonNullList<ItemStack> itemStacks = getSeedOutput(component, level);
                dropContents(level, pos, itemStacks);
                blockEntity.setStage(0);
                blockEntity.setChanged();
            }
        }
    }

    public static void dropContents(Level level, BlockPos pos, NonNullList<ItemStack> itemStacks) {
        Containers.dropContents(level, pos, itemStacks);
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
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return canSupportRigidBlock(level, below) || canSupportCenter(level, below, Direction.UP) || level.getBlockState(below).is(BlockTags.GROWS_CROPS);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BaseCropStandBlockEntity blockEntity = (BaseCropStandBlockEntity) level.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.tick(level, pos, state, random);
        }
    }
}
