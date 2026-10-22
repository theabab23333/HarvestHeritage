package me.theabab2333.harvestheritage.datagen.tag;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, lookupProvider, HarvestHeritage.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.ModBlockTags.CAN_SHEAR)
            .add(Blocks.SHORT_GRASS.builtInRegistryHolder.key())
            .add(Blocks.SHORT_DRY_GRASS.builtInRegistryHolder.key())
            .add(Blocks.TALL_GRASS.builtInRegistryHolder.key())
            .add(Blocks.TALL_DRY_GRASS.builtInRegistryHolder.key())
            .add(Blocks.FERN.builtInRegistryHolder.key());
        tag(ModTags.ModBlockTags.SCAFFOLDING_BLOCKS)
            .add(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.key())
            .add(Blocks.SCAFFOLDING.builtInRegistryHolder.key());
    }
}
