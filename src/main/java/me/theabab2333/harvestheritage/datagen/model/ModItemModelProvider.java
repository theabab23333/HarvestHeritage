package me.theabab2333.harvestheritage.datagen.model;

import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;

public class ModItemModelProvider {
    public static void registerModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.GRASS_SHEAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNKNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KNOWN_SEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GRAPE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ZZZZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FRIED_SEEDSACK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SEED_PACKET.get(), ModelTemplates.FLAT_ITEM);
        magnifyingGlass(itemModels);
        kfc(itemModels);
    }

    private static void magnifyingGlass(ItemModelGenerators itemModels) {
        itemModels.itemModelOutput.accept(
            ModItems.MAGNIFYING_GLASS.get(),
            ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModItems.MAGNIFYING_GLASS.asItem()))
        );
    }

    private static void kfc(ItemModelGenerators itemModels) {
        var kfcModel = itemModels.generateLayeredItem(
            ModItems.KFC.get(),
            new Material(Identifier.fromNamespaceAndPath("harvestheritage", "item/seed_packet")),
            new Material(Identifier.fromNamespaceAndPath("minecraft", "item/cooked_chicken"))
        );
        itemModels.itemModelOutput.accept(ModItems.KFC.get(), ItemModelUtils.plainModel(kfcModel));
    }
}
