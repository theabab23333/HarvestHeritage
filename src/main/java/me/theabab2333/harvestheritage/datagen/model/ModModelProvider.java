package me.theabab2333.harvestheritage.datagen.model;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, HarvestHeritage.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ModBlockModelProvider.registerModels(blockModels);
        ModItemModelProvider.registerModels(itemModels);
    }
}
