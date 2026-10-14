package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.block.entity.ActivatorBlockEntity;
import me.theabab2333.harvestheritage.common.block.entity.CropStandStandBlockEntity;
import me.theabab2333.harvestheritage.common.block.entity.ScaffoldingCropStandBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE,
        HarvestHeritage.MOD_ID
    );

    public static final Supplier<BlockEntityType<CropStandStandBlockEntity>> CROP_STAND_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
        "crop_stand_block_entity",
        () -> new BlockEntityType<>(CropStandStandBlockEntity::new, ModBlocks.CROP_STAND_BLOCK.get())
    );

    public static final Supplier<BlockEntityType<ScaffoldingCropStandBlockEntity>> SCAFFOLDING_CROP_STAND_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
        "scaffolding_crop_stand_block_entity",
        () -> new BlockEntityType<>(ScaffoldingCropStandBlockEntity::new, ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.get())
    );

    public static final Supplier<BlockEntityType<ActivatorBlockEntity>> ACTIVATOR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
        "activator_block_entity",
        () -> new BlockEntityType<>(ActivatorBlockEntity::new, ModBlocks.ACTIVAOR_BLOCK.get())
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
