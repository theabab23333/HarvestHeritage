package me.theabab2333.harvestheritage.init;

import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModSeeds {
    public record SeedInfo(List<Item> results, int stage, Block block) {
        public SeedInfo(List<Item> results, int stage) {
            this(results, stage, Blocks.AIR);
        }
    }

    public static Map<Item, SeedInfo> ALL_SEED = new LinkedHashMap<>();

    public static Map<Item, SeedInfo> CROP_SEED = new LinkedHashMap<>();
    public static Map<Item, SeedInfo> ANIMAL_SEED = new LinkedHashMap<>();
    public static Map<Item, SeedInfo> MOB_SEED = new LinkedHashMap<>();
    public static Map<Item, SeedInfo> MATERIAL_SEED = new LinkedHashMap<>();
    public static Map<Item, SeedInfo> SPECIAL_SEED = new LinkedHashMap<>();

    public static Map<Item, SeedInfo> MISC_SEED = new LinkedHashMap<>();

    static {
        // common
        CROP_SEED.put(Items.WHEAT, new SeedInfo(List.of(Items.WHEAT), 3));
        CROP_SEED.put(Items.BEETROOT, new SeedInfo(List.of(Items.BEETROOT), 3));
        CROP_SEED.put(Items.MELON_SLICE, new SeedInfo(List.of(Items.MELON), 3));
        CROP_SEED.put(Items.PUMPKIN_PIE, new SeedInfo(List.of(Items.PUMPKIN), 3));
        CROP_SEED.put(Items.SWEET_BERRIES, new SeedInfo(List.of(Items.SWEET_BERRIES), 3));
        CROP_SEED.put(Items.GLOW_BERRIES, new SeedInfo(List.of(Items.GLOW_BERRIES), 3));
        CROP_SEED.put(Items.CHORUS_FRUIT, new SeedInfo(List.of(Items.CHORUS_FRUIT), 3));
        CROP_SEED.put(Items.CARROT, new SeedInfo(List.of(Items.CARROT), 3));
        CROP_SEED.put(Items.POTATO, new SeedInfo(List.of(Items.POTATO), 3));
        CROP_SEED.put(Items.SUGAR_CANE, new SeedInfo(List.of(Items.SUGAR_CANE), 3));
        CROP_SEED.put(Items.BAMBOO, new SeedInfo(List.of(Items.BAMBOO), 3));
        CROP_SEED.put(Items.SEA_PICKLE, new SeedInfo(List.of(Items.SEA_PICKLE), 3));
        CROP_SEED.put(Items.KELP, new SeedInfo(List.of(Items.KELP), 3));
        CROP_SEED.put(Items.APPLE, new SeedInfo(List.of(Items.OAK_LOG, Items.APPLE), 4));

        // animal
        ANIMAL_SEED.put(Items.CHICKEN, new SeedInfo(List.of(Items.CHICKEN, Items.FEATHER, Items.EGG), 4));
        ANIMAL_SEED.put(Items.BEEF, new SeedInfo(List.of(Items.BEEF, Items.LEATHER), 4));
        ANIMAL_SEED.put(Items.PORKCHOP, new SeedInfo(List.of(Items.PORKCHOP), 4));
        ANIMAL_SEED.put(Items.MUTTON, new SeedInfo(List.of(Items.MUTTON), 4));
        ANIMAL_SEED.put(Items.RABBIT, new SeedInfo(List.of(Items.RABBIT, Items.RABBIT_FOOT), 4));
        ANIMAL_SEED.put(Items.SALMON, new SeedInfo(List.of(Items.SALMON), 4));
        ANIMAL_SEED.put(Items.COD, new SeedInfo(List.of(Items.COD), 4));
        ANIMAL_SEED.put(Items.TROPICAL_FISH, new SeedInfo(List.of(Items.TROPICAL_FISH), 4));
        ANIMAL_SEED.put(Items.PUFFERFISH, new SeedInfo(List.of(Items.PUFFERFISH), 4));
        ANIMAL_SEED.put(Items.INK_SAC, new SeedInfo(List.of(Items.INK_SAC, Items.GLOW_INK_SAC), 4));
        ANIMAL_SEED.put(Items.ARMADILLO_SCUTE, new SeedInfo(List.of(Items.ARMADILLO_SCUTE), 4));
        ANIMAL_SEED.put(Items.TURTLE_SCUTE, new SeedInfo(List.of(Items.TURTLE_SCUTE), 4));
        ANIMAL_SEED.put(Items.HONEYCOMB, new SeedInfo(List.of(Items.HONEYCOMB, Items.HONEY_BOTTLE), 4));

        // mob
        MOB_SEED.put(Items.BLAZE_ROD, new SeedInfo(List.of(Items.BLAZE_ROD), 4));
        MOB_SEED.put(Items.BREEZE_ROD, new SeedInfo(List.of(Items.BREEZE_ROD), 4));
        MOB_SEED.put(Items.ENDER_PEARL, new SeedInfo(List.of(Items.ENDER_PEARL), 4));
        MOB_SEED.put(Items.ECHO_SHARD, new SeedInfo(List.of(Items.ECHO_SHARD), 4));
        MOB_SEED.put(Items.SLIME_BALL, new SeedInfo(List.of(Items.SLIME_BALL), 4));
        MOB_SEED.put(Items.GUNPOWDER, new SeedInfo(List.of(Items.GUNPOWDER), 4));
        MOB_SEED.put(Items.PRISMARINE_CRYSTALS, new SeedInfo(List.of(Items.PRISMARINE_CRYSTALS, Items.PRISMARINE_SHARD), 4));
        MOB_SEED.put(Items.SPIDER_EYE, new SeedInfo(List.of(Items.SPIDER_EYE), 4));
        MOB_SEED.put(Items.MAGMA_CREAM, new SeedInfo(List.of(Items.MAGMA_CREAM), 4));
        MOB_SEED.put(Items.GHAST_TEAR, new SeedInfo(List.of(Items.GHAST_TEAR), 4));
        MOB_SEED.put(Items.PHANTOM_MEMBRANE, new SeedInfo(List.of(Items.PHANTOM_MEMBRANE), 4));
        MOB_SEED.put(Items.ROTTEN_FLESH, new SeedInfo(List.of(Items.ROTTEN_FLESH), 4));
        MOB_SEED.put(Items.BONE, new SeedInfo(List.of(Items.BONE), 4));
        MOB_SEED.put(Items.STRING, new SeedInfo(List.of(Items.STRING), 4));
        MOB_SEED.put(Items.COOKED_PORKCHOP, new SeedInfo(List.of(Items.COOKED_PORKCHOP, Items.GOLD_NUGGET), 4)); // piglin
        MOB_SEED.put(Items.SHULKER_SHELL, new SeedInfo(List.of(Items.SHULKER_SHELL), 5));

        // material
        MATERIAL_SEED.put(Items.FLINT, new SeedInfo(List.of(Items.FLINT), 3));
        MATERIAL_SEED.put(Items.COAL, new SeedInfo(List.of(Items.COAL), 4));
        MATERIAL_SEED.put(Items.IRON_INGOT, new SeedInfo(List.of(Items.IRON_INGOT), 5));
        MATERIAL_SEED.put(Items.GOLD_INGOT, new SeedInfo(List.of(Items.GOLD_INGOT), 5));
        MATERIAL_SEED.put(Items.AMETHYST_SHARD, new SeedInfo(List.of(Items.AMETHYST_SHARD), 5));
        MATERIAL_SEED.put(Items.QUARTZ, new SeedInfo(List.of(Items.QUARTZ), 5));
        MATERIAL_SEED.put(Items.LAPIS_LAZULI, new SeedInfo(List.of(Items.LAPIS_LAZULI), 5));
        MATERIAL_SEED.put(Items.REDSTONE, new SeedInfo(List.of(Items.REDSTONE), 5));
        MATERIAL_SEED.put(Items.GLOWSTONE_DUST, new SeedInfo(List.of(Items.GLOWSTONE_DUST), 5));
        MATERIAL_SEED.put(Items.EMERALD, new SeedInfo(List.of(Items.EMERALD), 5));
        MATERIAL_SEED.put(Items.DIAMOND, new SeedInfo(List.of(Items.DIAMOND), 5));
        MATERIAL_SEED.put(Items.NETHERITE_SCRAP, new SeedInfo(List.of(Items.ANCIENT_DEBRIS), 6));

        // special
        SPECIAL_SEED.put(Items.ENDER_EYE, new SeedInfo(List.of(Items.ENDER_EYE), 5));

        // all
        ALL_SEED.putAll(CROP_SEED);
        ALL_SEED.putAll(ANIMAL_SEED);
        ALL_SEED.putAll(MOB_SEED);
        ALL_SEED.putAll(MATERIAL_SEED);
        ALL_SEED.putAll(MISC_SEED);
    }

    public static void registerSeed(Item item, SeedInfo info, String category) {
        switch (category) {
            case "crop" -> CROP_SEED.put(item, info);
            case "animal" -> ANIMAL_SEED.put(item, info);
            case "mob" -> MOB_SEED.put(item, info);
            case "material" -> MATERIAL_SEED.put(item, info);
            case "special" -> SPECIAL_SEED.put(item, info);
            case "misc" -> MISC_SEED.put(item, info);
            default -> throw new IllegalArgumentException("Unknown seed category: " + category);
        }
        ALL_SEED.put(item, info);
    }

    public static List<ItemStack> getSeedPackets() {
        List<ItemStack> list = new ArrayList<>();
        for (var entry : ALL_SEED.entrySet()) {
            DataComponentPatch patch = SeedUtil.createSeedComponentPatch(entry.getKey(), entry.getValue());
            list.add(new ItemStack(ModItems.SEED_PACKET, 1, patch));
        }
        return list;
    }
}
