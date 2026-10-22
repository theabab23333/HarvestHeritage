package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

// 我没招了，一旦模组环境复杂，这里的物品栏顺序就会乱掉，我不知道怎么回事，希望以后能解决吧
public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        HarvestHeritage.MOD_ID
    );

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> COMMON_TAB = CREATIVE_MODE_TABS.register(
        "common_tab",
        () -> CreativeModeTab.builder()
            .displayItems((_, output) -> getItem().forEach(output::accept))
            .icon(() -> new ItemStack(ModItems.GRASS_SHEAR.get()))
            .title(Component.translatable("modmenu.nameTranslation.harvestheritage"))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build()
    );

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> SEED_TAB = CREATIVE_MODE_TABS.register(
        "seed_tab",
        () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsBefore(COMMON_TAB.key())
            .icon(() -> new ItemStack(ModItems.SEED_PACKET.get()))
            .title(Component.translatable("creativetab.harvestheritage.seed_packet"))
            .displayItems((_, output) -> ModSeeds.getSeedPackets().forEach(output::accept))
            .build()
    );

    public static List<ItemLike> getItem() {
        List<ItemLike> items = new ArrayList<>();
        ModItems.ITEMS.getEntries().forEach(holder -> items.add(holder.get()));
        return items;
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
