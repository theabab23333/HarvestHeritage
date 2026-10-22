package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.item.GrassShearItem;
import me.theabab2333.harvestheritage.common.item.KnownSeedItem;
import me.theabab2333.harvestheritage.common.item.MagnifyingGlassItem;
import me.theabab2333.harvestheritage.common.item.ScaffoldingCropStandBlockItem;
import me.theabab2333.harvestheritage.common.item.SeedPacketItem;
import me.theabab2333.harvestheritage.common.item.UnknownSeedItem;
import me.theabab2333.harvestheritage.common.item.ZZZZItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HarvestHeritage.MOD_ID);

    public static final DeferredItem<Item> GRASS_SHEAR = ITEMS.registerItem("grass_shear", GrassShearItem::new);

    public static final DeferredItem<Item> MAGNIFYING_GLASS = ITEMS.registerItem("magnifying_glass", MagnifyingGlassItem::new);

    public static final DeferredItem<Item> GRAPE = ITEMS.registerSimpleItem(
        "grape", properties -> properties.food(
            Foods.APPLE
        ).compostable(ContextIntProviders.COMPOSTABLE_LOW)
    );

    public static final DeferredItem<Item> ZZZZ = ITEMS.registerItem("zzzz", ZZZZItem::new);

    public static final DeferredItem<Item> UNKNOWN_SEED = ITEMS.registerItem("unknown_seed", UnknownSeedItem::new);

    public static final DeferredItem<Item> KNOWN_SEED = ITEMS.registerItem("known_seed", KnownSeedItem::new);

    public static final DeferredItem<Item> SEED_PACKET = ITEMS.registerItem("seed_packet", SeedPacketItem::new);

    public static final DeferredItem<Item> FRIED_SEEDSACK = ITEMS.registerSimpleItem(
        "fried_seedsack", properties -> properties.food(
            new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).alwaysEdible().build(),
            Consumable.builder().consumeSeconds(0.1f).sound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.EMPTY)).build()
        )
    );

    public static final DeferredItem<Item> KFC = ITEMS.registerSimpleItem(
        "kfc", properties -> properties.food(
            new FoodProperties.Builder().nutrition(15).saturationModifier(2f).alwaysEdible().build(),
            Consumable.builder().consumeSeconds(1f).build()
        )
    );

    public static final DeferredItem<Item> SCAFFOLDING_CROP_STAND_BLOCK_ITEM = ITEMS.registerItem(
        "scaffolding_crop_stand_block",
        properties -> new ScaffoldingCropStandBlockItem(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK.get(), properties)
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
