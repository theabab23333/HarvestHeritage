package me.theabab2333.harvestheritage.datagen.lang;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

// 如果有国外有人能帮我翻译的话，我就能把这里删了，然后把语言文件放 resources 里面(哭哭,全是机翻)
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class ENUSProvider extends LanguageProvider {

    public ENUSProvider(PackOutput output) {
        super(output, HarvestHeritage.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        itemAndBlocks();
        toolTips();
        advancement();
        others();

        jei();
        jade();
        guideme();
    }

    private void itemAndBlocks() {
        addItem(ModItems.GRASS_SHEAR, "Grass Shear");
        addItem(ModItems.UNKNOWN_SEED, "Unknown Seed");
        addItem(ModItems.MAGNIFYING_GLASS, "Magnifying Glass");
        addItem(ModItems.KNOWN_SEED, "Known Seed");
        addItem(ModItems.SEED_PACKET, "Seed Packet");
        addItem(ModItems.GRAPE, "Grape");
        addItem(ModItems.ZZZZ, "Chrome Ball");
        addItem(ModItems.FRIED_SEEDSACK, "Fried Seed Sack");
        addItem(ModItems.KFC, "Crazy Thursday");
        addBlock(ModBlocks.ACTIVAOR_BLOCK, "Strange Activator");
        addBlock(ModBlocks.TEST_BLOCK, "Test Block");
        addBlock(ModBlocks.CROP_STAND_BLOCK, "Crop Stand");
    }

    private void toolTips() {
        add("gui.harvestheritage.tooltip.shift", "Hold down [Shift] to view information");

        add(
            "item.harvestheritage.unknown_seed.tooltip",
            "Maybe you need to throw it on the ground and look at it with a magnifying glass..."
        );
        add(
            "item.harvestheritage.zzzz.tooltip",
            "It's so weird. Chrome Ball?!"
        );
        add("item.harvestheritage.magnifying_glass.tooltip", "Can you see crop attributes and seeds...?");
        add("item.harvestheritage.grass_shear.tooltip", "Try using this to destroy some grass?");
        add("item.harvestheritage.seed.tooltip.fail", "Sorry, this item does not have a seed component");
        add("item.harvestheritage.seed.tooltip.seed", "Seed: %s");
        add("item.harvestheritage.seed.tooltip.stage", "Seed Growth Stage：%s");
        add("item.harvestheritage.seed_packet.tooltip.result", "Output: %s");
        add("item.harvestheritage.seed_packet.tooltip.speed", "Growth Speed: %s");
        add("item.harvestheritage.seed_packet.tooltip.output", "Output Amount: %s");
        add("block.harvestheritage.activator.tooltip.1", "Random ticks are about to accelerate");
        add("block.harvestheritage.activator.tooltip.2", "Use a redstone signal to activate");
        add("block.harvestheritage.crop_stand.tooltip.light", "Light: %s");
    }

    private void advancement() {
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
    }

    private void others() {
        add("modmenu.nameTranslation.harvestheritage", "Harvest Heritage");
        add("creativetab.harvestheritage.seed_packet", "Harvest Heritage：Seed Packet");
    }

    private void jei() {
        add("jei.harvestheritage.hybrid", "Hybrid");
        add("jei.harvestheritage.hybrid.tooltip", "Will appear in seed packet form!");
        add("jei.harvestheritage.seed_info", "Seed Info");
        add("jei.harvestheritage.seed_info.seed_packet", "This is a seed packet");
        add("jei.harvestheritage.seed_info.seed", "This is the seed (item) corresponding to the seed packet");
        add("jei.harvestheritage.seed_info.need_blocks.1", "This is the block needed for crop growth");
        add("jei.harvestheritage.seed_info.need_blocks.2", "Needs to be arranged in order");
        add("jei.harvestheritage.seed_info.need_blocks.any", "This crop's growth is unrelated to the block below");
        add("jei.harvestheritage.seed_info.need_light.above", "Light level %s or above");
        add("jei.harvestheritage.seed_info.need_light.any", "No light requirement");
        add("jei.harvestheritage.seed_info.need_light.below", "Light level %s or below");
        add("jei.harvestheritage.seed_info.need_light.between", "Light level between %s and %s");
        add("jei.harvestheritage.seed_info.need_light.exact", "Light level is %s");
        add("jei.harvestheritage.seed_info.output", "This is the item produced after the crop matures");
        add("jei.harvestheritage.find", "Let me see see");
        add("jei.harvestheritage.hybrid.show_items", "Display items");
        add("jei.harvestheritage.hybrid.show_seed_packets", "Display seed packets");
    }

    private void jade() {
        add("jade.harvestheritage.crop_stand.no_seed", "No Seed");
        add("jade.harvestheritage.crop_stand.stage", "Stage : %s");
        add("config.jade.plugin_harvestheritage.crop_stand_block_data_provider", "Crop Stand State");
    }

    private void guideme() {
        add("harvestheritage.guideme.guide_book", "Harvest Heritage Guide Book");
        add("harvestheritage.guideme.guide_book.tooltip", "Hope this helps.");
    }
}
