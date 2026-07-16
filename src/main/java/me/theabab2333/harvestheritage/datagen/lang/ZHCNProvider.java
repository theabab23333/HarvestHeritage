package me.theabab2333.harvestheritage.datagen.lang;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class ZHCNProvider extends LanguageProvider {
    public ZHCNProvider(PackOutput output) {
        super(output, HarvestHeritage.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.GRASS_SHEAR, "除草剪");
        addItem(ModItems.UNKNOWN_SEED, "未知种子");
        addItem(ModItems.MAGNIFYING_GLASS, "放大镜");
        addItem(ModItems.KNOWN_SEED, "已知种子");
        addItem(ModItems.SEED_PACKET, "种子袋");
        addItem(ModItems.GRAPE, "葡萄");
        addItem(ModItems.ZZZZ, "土球");
        addItem(ModItems.SCAFFOLDING_CROP_STAND_BLOCK_ITEM, "悬挂式作物架");
        addItem(ModItems.FRIED_SEEDSACK, "瓜子袋");
        addItem(ModItems.KFC, "疯狂星期四");
        addBlock(ModBlocks.ACTIVAOR_BLOCK, "奇异催生器");
        addBlock(ModBlocks.TEST_BLOCK, "测试方块");
        addBlock(ModBlocks.CROP_STAND_BLOCK, "作物架");
        addBlock(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK, "悬挂式作物架");
        add("modmenu.nameTranslation.harvestheritage", "收获：遗产");
        add("creativetab.harvestheritage.seed_packet", "收获：遗产 种子袋");
        add("gui.harvestheritage.tooltip.shift", "按住[Shift]以查看信息");
        add("item.harvestheritage.unknown_seed.tooltip", "或许需要丢在地上用放大镜看看...");
        add("item.harvestheritage.zzzz.tooltip", "太怪了...... Chrome Ball?!");
        add("item.harvestheritage.magnifying_glass.tooltip", "能看作物属性和种子欸...?");
        add("item.harvestheritage.grass_shear.tooltip", "用这个破坏点草试试?");
        add("item.harvestheritage.seed.tooltip.fail", "抱歉，这个物品没有种子组件");
        add("item.harvestheritage.seed.tooltip.seed", "种子：%s");
        add("item.harvestheritage.seed.tooltip.stage", "生长阶段：%s");
        add("item.harvestheritage.seed_packet.tooltip.result", "产出：%s");
        add("item.harvestheritage.seed_packet.tooltip.speed", "生长速度：%s");
        add("item.harvestheritage.seed_packet.tooltip.output", "产出数量：%s");
        add("block.harvestheritage.crop_stand.tooltip.1", "作物架，能种在耕地上");
        add("block.harvestheritage.crop_stand.tooltip.2", "种子袋右键作物架能种植作物");
        add("block.harvestheritage.crop_stand.tooltip.3", "作物成熟时使用除草剪能获取种子，直接右键能收获产物");
        add(
            "block.harvestheritage.crop_stand.tooltip.4",
            "作物成熟时，如果东南西北四个方向第二格方块有其他成熟的作物架，可能会在中间空的作物架处发生杂交..."
        );
        add("block.harvestheritage.crop_stand.tooltip.5", "当然，也可以种内繁殖");
        add("block.harvestheritage.crop_stand.tooltip.6", "作物属性根据双方作物架而定，有概率增长也有概率衰减");
        add("block.harvestheritage.scaffolding_crop_stand.tooltip.1", "种子袋右键脚手架转换");
        add("block.harvestheritage.scaffolding_crop_stand.tooltip.2", "不能杂交，但是能收获，适合大范围种植，推荐离地几格");
        add("block.harvestheritage.crop_stand.tooltip.stage", "当前生长阶段：%s");
        add("block.harvestheritage.activator.tooltip.1", "随机刻要加速了");
        add("block.harvestheritage.activator.tooltip.2", "使用红石信号以激活");
        add("jei.harvestheritage.find", "让我看看");
        add("jei.harvestheritage.hybrid", "杂交");
        add("jei.harvestheritage.hybrid.tooltip", "将以种子袋形式出战!");
        add("jei.harvestheritage.seed_output", "种子产出");
        add("advancement.harvestheritage.kfc.title", "疯狂疯狂星期四");
        add("advancement.harvestheritage.kfc.description", "食用疯狂星期四");
        add("advancement.harvestheritage.fride_seedsack.title", "吃瓜群众");
        add("advancement.harvestheritage.fride_seedsack.description", "瓜子也是瓜吗...");
        add("advancement.harvestheritage.max_seed_packet.title", "登峰造极");
        add(
            "advancement.harvestheritage.max_seed_packet.description",
            "你用运气与毅力获得了速度和产出均达到极限的种子袋\n或许你该放下游戏,去外面走走\n亦或者继续杂交..."
        );
        add("advancement.harvestheritage.magnifying_glass.title", "大侦探");
        add("advancement.harvestheritage.magnifying_glass.description", "你发现了一个华点...(戴头上?)");
        add("advancement.harvestheritage.welcome.title", "收获：遗产");
        add("advancement.harvestheritage.welcome.description", "欢迎来到收获：遗产！");
        add("jade.harvestheritage.crop_stand.no_seed", "这个作物架似乎没有种植作物");
        add("jade.harvestheritage.crop_stand.stage", "生长阶段 : %s");
    }
}
