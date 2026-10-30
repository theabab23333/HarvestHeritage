package me.theabab2333.harvestheritage.datagen.loot;

import me.theabab2333.harvestheritage.init.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootProvider extends BlockLootSubProvider {
    public ModBlockLootProvider(
        LootTableSubProvider.Context output
    ) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
    }

    @Override
    protected void generate() {
        // ModBlocks.BLOCKS.getEntries().forEach(holder -> this.dropSelf(holder.get()));

        this.dropSelf(ModBlocks.CROP_STAND_BLOCK.get());
        this.dropSelf(ModBlocks.TEST_BLOCK.get());
        this.dropSelf(ModBlocks.ACTIVAOR_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
            .map(holder -> (Block) holder.value())
            .toList();
    }
}