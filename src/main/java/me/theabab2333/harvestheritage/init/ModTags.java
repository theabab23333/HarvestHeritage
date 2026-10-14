package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    private static TagKey<Item> createItemTag(String name) {
        return ItemTags.create(Identifier.fromNamespaceAndPath(HarvestHeritage.MOD_ID, name));
    }

    private static TagKey<Block> createBlockTag(String name) {
        return BlockTags.create(Identifier.fromNamespaceAndPath(HarvestHeritage.MOD_ID, name));
    }

    public static class ModItemTags {
        public static TagKey<Item> CHROME_BALL = ItemTags.create(Identifier.fromNamespaceAndPath("c", "chromeballs"));
    }

    public static class ModBlockTags {
        public static TagKey<Block> CAN_SHEAR = createBlockTag("can_shear");
        public static TagKey<Block> SCAFFOLDING_BLOCKS = createBlockTag("scaffolding_blocks");
    }
}
