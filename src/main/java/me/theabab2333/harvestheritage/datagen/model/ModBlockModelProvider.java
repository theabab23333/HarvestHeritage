package me.theabab2333.harvestheritage.datagen.model;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;

import java.util.function.Consumer;

import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;

public class ModBlockModelProvider {
    public static void registerModels(BlockModelGenerators blockModels) {
        Consumer<BlockModelDefinitionGenerator> blockStateOutput = blockModels.blockStateOutput;

        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.ACTIVAOR_BLOCK.get());

        blockStateOutput.accept(
            MultiPartGenerator
                .multiPart(
                    ModBlocks.CROP_STAND_BLOCK.get()
                )
                .with(
                    plainVariant(
                        HarvestHeritage.of("block/crop_stand_block")
                    )
                )
        );
    }
}
