package me.theabab2333.harvestheritage.datagen.lang;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class ENUSProvider extends LanguageProvider {

    public ENUSProvider(PackOutput output) {
        super(output, HarvestHeritage.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.GRASS_SHEAR, "Grass Shear");
        addItem(ModItems.UNKNOWN_SEED, "Unknown Seed");
        addItem(ModItems.MAGNIFYING_GLASS, "Magnifying Glass");
        addItem(ModItems.KNOWN_SEED, "Known Seed");
        addItem(ModItems.SEED_PACKET, "Seed Packet");
        addItem(ModItems.GRAPE, "Grape");
        addItem(ModItems.ZZZZ, "Chrome Ball");
        addItem(ModItems.SCAFFOLDING_CROP_STAND_BLOCK_ITEM, "Hanging crop racks");
        addItem(ModItems.FRIED_SEEDSACK, "Fried Seed Sack");
        addItem(ModItems.KFC, "Crazy Thursday");
        addBlock(ModBlocks.ACTIVAOR_BLOCK, "Strange Activator");
        addBlock(ModBlocks.TEST_BLOCK, "Test Block");
        addBlock(ModBlocks.CROP_STAND_BLOCK, "Crop Stand");
        addBlock(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK, "Hanging crop racks");
        add(
            "item.harvestheritage.zzzz.tooltip",
            "It's so weird. Chrome Ball?!"
        );
        add("item.harvestheritage.magnifying_glass.tooltip", "Can you see crop attributes and seeds...?");
        add("modmenu.nameTranslation.harvestheritage", "Harvest Heritage");
        add("creativetab.harvestheritage.seed_packet", "Harvest Heritage：Seed Packet");
        add("gui.harvestheritage.tooltip.shift", "Hold down [Shift] to view information");
        add(
            "item.harvestheritage.unknown_seed.tooltip",
            "Maybe you need to throw it on the ground and look at it with a magnifying glass..."
        );
        add("item.harvestheritage.grass_shear.tooltip", "Try using this to destroy some grass?");
        add("item.harvestheritage.seed.tooltip.fail", "Sorry, this item does not have a seed component");
        add("item.harvestheritage.seed.tooltip.seed", "Seed: %s");
        add("item.harvestheritage.seed.tooltip.stage", "Seed Growth Stage：%s");
        add("item.harvestheritage.seed_packet.tooltip.result", "Output: %s");
        add("item.harvestheritage.seed_packet.tooltip.speed", "Growth Speed: %s");
        add("item.harvestheritage.seed_packet.tooltip.output", "Output Amount: %s");
        add("block.harvestheritage.crop_stand.tooltip.stage", "Current Growth Stage：%s");
        add("block.harvestheritage.crop_stand.tooltip.1", "Crop stand, can generally be planted on farmland");
        add("block.harvestheritage.crop_stand.tooltip.2", "Right-click the crop stand with a seed packet to plant crops");
        add(
            "block.harvestheritage.crop_stand.tooltip.3",
            "When crops mature, use grass shears to get seeds, or right-click directly to harvest the produce"
        );
        add(
            "block.harvestheritage.crop_stand.tooltip.4",
            "When crops are mature, if there are other mature crop stands two blocks away in the north, south, east, or west directions, crossbreeding may occur at the empty crop stand in the middle..."
        );
        add("block.harvestheritage.crop_stand.tooltip.5", "Of course, you can also breed within the same type");
        add(
            "block.harvestheritage.crop_stand.tooltip.6",
            "Crop attributes depend on both crop stands; there is a chance of increase and a chance of decrease"
        );
        add("block.harvestheritage.scaffolding_crop_stand.tooltip.1", "Right-click scaffolding with a seed packet to convert");
        add(
            "block.harvestheritage.scaffolding_crop_stand.tooltip.2",
            "Cannot crossbreed, but can be harvested. Suitable for large-scale planting; recommended a few blocks above the ground"
        );
        add("block.harvestheritage.activator.tooltip.1", "Random ticks are about to accelerate");
        add("block.harvestheritage.activator.tooltip.2", "Use a redstone signal to activate");
        add("jei.harvestheritage.find", "Let me see see");
        add("jei.harvestheritage.hybrid", "Hybrid");
        add("jei.harvestheritage.hybrid.tooltip", "Will appear in seed packet form!");
        add("jei.harvestheritage.seed_output", "Seed Output");
        add("advancement.harvestheritage.kfc.title", "Crazy Crazy Thursday");
        add("advancement.harvestheritage.kfc.description", "Eat KFC");
        add("advancement.harvestheritage.fride_seedsack.title", "Snacking Spectator");
        add("advancement.harvestheritage.fride_seedsack.description", "Are melon seeds really melons...");
        add("advancement.harvestheritage.max_seed_packet.title", "Peak Performance");
        add(
            "advancement.harvestheritage.max_seed_packet.description",
            "You have obtained a seed bag with speed and output reaching their limits through luck and perseverance.\nPerhaps you should put down the game and go for a walk outside.\nOr continue crossbreeding..."
        );

        add("advancement.harvestheritage.magnifying_glass.title", "Detective");
        add("advancement.harvestheritage.magnifying_glass.description", "You found a brilliant point...(wear it on your head?)");
        add("advancement.harvestheritage.welcome.title", "Harvest: Heritage");
        add("advancement.harvestheritage.welcome.description", "Welcome to Harvest: Heritage!");
        add("jade.harvestheritage.crop_stand.no_seed", "No Seed");
        add("jade.harvestheritage.crop_stand.stage", "Stage : %s");
    }
}
