package me.theabab2333.harvestheritage.common.block.entity;

import lombok.Getter;
import lombok.Setter;
import me.theabab2333.harvestheritage.common.block.BaseCropStandBlock;
import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.common.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCropStandBlockEntity extends BlockEntity {
    public BaseCropStandBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Getter
    @Nullable
    protected SeedPacketComponent seedPacketComponent;
    @Getter
    @Setter
    protected int stage = 0;

    public void seedUseOn(ItemStack itemStack) {
        if (level == null || level.isClientSide()) {
            return;
        }

        if (this.seedPacketComponent != null) {
            return;
        }

        SeedPacketComponent component = SeedUtil.getSeedPacketComponent(itemStack);
        if (component == null) {
            return;
        }

        this.seedPacketComponent = component;
        this.stage = 0;

        itemStack.shrink(1);
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.seedPacketComponent = input.read("seed_packet_component", SeedPacketComponent.CODEC).orElse(null);
        this.stage = input.getIntOr("stage", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (this.seedPacketComponent != null) {
            output.store("seed_packet_component", SeedPacketComponent.CODEC, this.seedPacketComponent);
        }
        output.putInt("stage", this.stage);
    }

    public void setSeedPacketComponent(@Nullable SeedPacketComponent seedPacketComponent) {
        this.seedPacketComponent = seedPacketComponent;
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void tick(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (this.seedPacketComponent == null) return;

        Holder<Item> seedItem = this.seedPacketComponent.seed();
        var seedInfo = SeedUtil.getSeedInfo(seedItem);
        if (seedInfo == null) {
            return;
        }

        if (this.matches(seedInfo, level, pos)) {
            int needStage = seedInfo.stage();
            if (this.stage == needStage) {
                this.find(level, pos);
                return;
            }

            int speed = this.seedPacketComponent.speed();
            if (random.nextInt(3) < speed) {
                this.stage++;
                setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    public boolean matches(
        SeedInfo seedInfo,
        ServerLevel level,
        BlockPos pos
    ) {
        // blocks
        if (!SeedUtil.matchesGrowBlocks(level, pos, seedInfo.needBlocks())) {
            return false;
        }
        // light
        if (!seedInfo.needLight().matches(level.getMaxLocalRawBrightness(pos))) {
            return false;
        }

        return true;
    }

    public void find(ServerLevel level, BlockPos pos) {
        SeedPacketComponent currentComponent = this.seedPacketComponent;
        if (currentComponent == null) {
            return;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos pos1 = pos.relative(direction, 1);
            BlockState state1 = level.getBlockState(pos1);
            if (state1.getBlock() instanceof BaseCropStandBlock) {
                BaseCropStandBlockEntity be1 = (BaseCropStandBlockEntity) level.getBlockEntity(pos1);
                if (be1 == null) {
                    continue;
                }
                if (be1.getSeedPacketComponent() == null) {
                    BlockPos pos2 = pos.relative(direction, 2);
                    BlockState state2 = level.getBlockState(pos2);
                    if (state2.getBlock() instanceof BaseCropStandBlock) {
                        BaseCropStandBlockEntity be2 = (BaseCropStandBlockEntity) level.getBlockEntity(pos2);
                        if (be2 == null) {
                            continue;
                        }
                        SeedPacketComponent component = be2.getSeedPacketComponent();
                        if (component == null) {
                            continue;
                        }
                        var be2Info = SeedUtil.getSeedInfo(component.seed());
                        if (be2Info != null && be2Info.stage() == be2.stage) {
                            this.hybrid(be1, currentComponent, component, level);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void hybrid(
        BaseCropStandBlockEntity cropStandBlock,
        SeedPacketComponent component1,
        SeedPacketComponent component2,
        ServerLevel level
    ) {
        var holders = level.recipeAccess().recipeMap().byType(ModRecipes.HYBRID_TYPE.get());
        if (holders.isEmpty()) {
            return;
        }

        Item item1 = component1.getSeed();
        Item item2 = component2.getSeed();

        List<Holder<Item>> allOutputs = new ArrayList<>();

        for (RecipeHolder<HybridRecipe> holder : holders) {
            HybridRecipe recipe = holder.value();
            List<Holder<Item>> inputList = recipe.inputSeeds();
            if (inputList.size() == 2) {
                Item a = inputList.get(0).value();
                Item b = inputList.get(1).value();
                if ((item1 == a || item1 == b) && (item2 == a || item2 == b) && item1 != item2) {
                    allOutputs.addAll(recipe.outputSeeds());
                }
            }
        }

        List<Holder<Item>> seeds = new ArrayList<>(allOutputs);
        seeds.add(component1.seed());
        seeds.add(component2.seed());

        cropStandBlock.setSeedPacketComponent(
            SeedUtil.mergeSeedPackets(level.getRandom(), component1, component2, seeds)
        );
    }
}
