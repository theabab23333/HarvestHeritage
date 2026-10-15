package me.theabab2333.harvestheritage.init.seed;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModSeeds {
    private static final List<SeedDefinition> BUILT = new ArrayList<>();

    // Plants
    public static final SeedDefinition WHEAT = seed("wheat", Items.WHEAT)
        .stage(3)
        .result(Items.WHEAT)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition BEETROOT = seed("beetroot", Items.BEETROOT)
        .stage(3)
        .result(Items.BEETROOT)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition MELON_SLICE = seed("melon_slice", Items.MELON_SLICE)
        .stage(3)
        .result(Items.MELON)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition PUMPKIN_PIE = seed("pumpkin_pie", Items.PUMPKIN_PIE)
        .stage(3)
        .result(Items.PUMPKIN)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition SWEET_BERRIES = seed("sweet_berries", Items.SWEET_BERRIES)
        .stage(3)
        .result(Items.SWEET_BERRIES)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition GLOW_BERRIES = seed("glow_berries", Items.GLOW_BERRIES)
        .stage(3)
        .result(Items.GLOW_BERRIES)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition CHORUS_FRUIT = seed("chorus_fruit", Items.CHORUS_FRUIT)
        .stage(3)
        .result(Items.CHORUS_FRUIT)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition CARROT = seed("carrot", Items.CARROT)
        .stage(3)
        .result(Items.CARROT)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition POTATO = seed("potato", Items.POTATO)
        .stage(3)
        .result(Items.POTATO)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition SUGAR_CANE = seed("sugar_cane", Items.SUGAR_CANE)
        .stage(3)
        .result(Items.SUGAR_CANE)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition BAMBOO = seed("bamboo", Items.BAMBOO)
        .stage(3)
        .result(Items.BAMBOO)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition SEA_PICKLE = seed("sea_pickle", Items.SEA_PICKLE)
        .stage(3)
        .result(Items.SEA_PICKLE)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition KELP = seed("kelp", Items.KELP)
        .stage(3)
        .result(Items.KELP)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition APPLE = seed("apple", Items.APPLE)
        .stage(4)
        .result(Items.OAK_LOG)
        .result(Items.APPLE)
        .crop()
        .packet()
        .build();
    public static final SeedDefinition GRAPE = seed("grape", ModItems.GRAPE)
        .stage(3)
        .result(ModItems.GRAPE)
        .crop()
        .build();
    // Animals
    public static final SeedDefinition CHICKEN = seed("chicken", Items.CHICKEN)
        .stage(4)
        .result(Items.CHICKEN)
        .result(Items.FEATHER)
        .result(Items.EGG)
        .packet()
        .build();
    public static final SeedDefinition BEEF = seed("beef", Items.BEEF)
        .stage(4)
        .result(Items.BEEF)
        .result(Items.LEATHER)
        .packet()
        .build();
    public static final SeedDefinition PORKCHOP = seed("porkchop", Items.PORKCHOP)
        .stage(4)
        .result(Items.PORKCHOP)
        .packet()
        .build();
    public static final SeedDefinition MUTTON = seed("mutton", Items.MUTTON)
        .stage(4)
        .result(Items.MUTTON)
        .packet()
        .build();
    public static final SeedDefinition RABBIT = seed("rabbit", Items.RABBIT)
        .stage(4)
        .result(Items.RABBIT)
        .result(Items.RABBIT_FOOT)
        .packet()
        .build();
    public static final SeedDefinition SALMON = seed("salmon", Items.SALMON)
        .stage(4)
        .result(Items.SALMON)
        .packet()
        .build();
    public static final SeedDefinition COD = seed("cod", Items.COD)
        .stage(4)
        .result(Items.COD)
        .packet()
        .build();
    public static final SeedDefinition TROPICAL_FISH = seed("tropical_fish", Items.TROPICAL_FISH)
        .stage(4)
        .result(Items.TROPICAL_FISH)
        .packet()
        .build();
    public static final SeedDefinition PUFFERFISH = seed("pufferfish", Items.PUFFERFISH)
        .stage(4)
        .result(Items.PUFFERFISH)
        .packet()
        .build();
    public static final SeedDefinition INK_SAC = seed("ink_sac", Items.INK_SAC)
        .stage(4)
        .result(Items.INK_SAC)
        .result(Items.GLOW_INK_SAC)
        .packet()
        .build();
    public static final SeedDefinition ARMADILLO_SCUTE = seed("armadillo_scute", Items.ARMADILLO_SCUTE)
        .stage(4)
        .result(Items.ARMADILLO_SCUTE)
        .packet()
        .build();
    public static final SeedDefinition TURTLE_SCUTE = seed("turtle_scute", Items.TURTLE_SCUTE)
        .stage(4)
        .result(Items.TURTLE_SCUTE)
        .packet()
        .build();
    public static final SeedDefinition HONEYCOMB = seed("honeycomb", Items.HONEYCOMB)
        .stage(4)
        .result(Items.HONEYCOMB)
        .result(Items.HONEY_BOTTLE)
        .packet()
        .build();
    // Mobs
    public static final SeedDefinition BLAZE_ROD = seed("blaze_rod", Items.BLAZE_ROD)
        .stage(4)
        .result(Items.BLAZE_ROD)
        .build();
    public static final SeedDefinition BREEZE_ROD = seed("breeze_rod", Items.BREEZE_ROD)
        .stage(4)
        .result(Items.BREEZE_ROD)
        .build();
    public static final SeedDefinition ENDER_PEARL = seed("ender_pearl", Items.ENDER_PEARL)
        .stage(4)
        .result(Items.ENDER_PEARL)
        .build();
    public static final SeedDefinition ECHO_SHARD = seed("echo_shard", Items.ECHO_SHARD)
        .stage(4)
        .result(Items.ECHO_SHARD)
        .build();
    public static final SeedDefinition SLIME_BALL = seed("slime_ball", Items.SLIME_BALL)
        .stage(4)
        .result(Items.SLIME_BALL)
        .build();
    public static final SeedDefinition GUNPOWDER = seed("gunpowder", Items.GUNPOWDER)
        .stage(4)
        .result(Items.GUNPOWDER)
        .build();
    public static final SeedDefinition PRISMARINE_CRYSTALS = seed("prismarine_crystals", Items.PRISMARINE_CRYSTALS)
        .stage(4)
        .result(Items.PRISMARINE_CRYSTALS)
        .result(Items.PRISMARINE_SHARD)
        .build();
    public static final SeedDefinition SPIDER_EYE = seed("spider_eye", Items.SPIDER_EYE)
        .stage(4)
        .result(Items.SPIDER_EYE)
        .build();
    public static final SeedDefinition MAGMA_CREAM = seed("magma_cream", Items.MAGMA_CREAM)
        .stage(4)
        .result(Items.MAGMA_CREAM)
        .build();
    public static final SeedDefinition GHAST_TEAR = seed("ghast_tear", Items.GHAST_TEAR)
        .stage(4)
        .result(Items.GHAST_TEAR)
        .build();
    public static final SeedDefinition PHANTOM_MEMBRANE = seed("phantom_membrane", Items.PHANTOM_MEMBRANE)
        .stage(4)
        .result(Items.PHANTOM_MEMBRANE)
        .build();
    public static final SeedDefinition ROTTEN_FLESH = seed("rotten_flesh", Items.ROTTEN_FLESH)
        .stage(4)
        .result(Items.ROTTEN_FLESH)
        .build();
    public static final SeedDefinition BONE = seed("bone", Items.BONE)
        .stage(4)
        .result(Items.BONE)
        .build();
    public static final SeedDefinition STRING = seed("string", Items.STRING)
        .stage(4)
        .result(Items.STRING)
        .build();
    public static final SeedDefinition COOKED_PORKCHOP = seed("cooked_porkchop", Items.COOKED_PORKCHOP)
        .stage(4)
        .result(Items.COOKED_PORKCHOP)
        .result(Items.GOLD_NUGGET)
        .build();
    public static final SeedDefinition SHULKER_SHELL = seed("shulker_shell", Items.SHULKER_SHELL)
        .stage(5)
        .result(Items.SHULKER_SHELL)
        .build();
    // Materials
    public static final SeedDefinition FLINT = seed("flint", Items.FLINT)
        .stage(3)
        .result(Items.FLINT)
        .build();
    public static final SeedDefinition COAL = seed("coal", Items.COAL)
        .stage(4)
        .result(Items.COAL)
        .build();
    public static final SeedDefinition IRON_INGOT = seed("iron_ingot", Items.IRON_INGOT)
        .stage(5)
        .result(Items.IRON_INGOT)
        .build();
    public static final SeedDefinition COPPER_INGOT = seed("copper_ingot", Items.COPPER_INGOT)
        .stage(5)
        .result(Items.COPPER_INGOT)
        .build();
    public static final SeedDefinition GOLD_INGOT = seed("gold_ingot", Items.GOLD_INGOT)
        .stage(5)
        .result(Items.GOLD_INGOT)
        .build();
    public static final SeedDefinition AMETHYST_SHARD = seed("amethyst_shard", Items.AMETHYST_SHARD)
        .stage(5)
        .result(Items.AMETHYST_SHARD)
        .build();
    public static final SeedDefinition QUARTZ = seed("quartz", Items.QUARTZ)
        .stage(5)
        .result(Items.QUARTZ)
        .build();
    public static final SeedDefinition LAPIS_LAZULI = seed("lapis_lazuli", Items.LAPIS_LAZULI)
        .stage(5)
        .result(Items.LAPIS_LAZULI)
        .build();
    public static final SeedDefinition REDSTONE = seed("redstone", Items.REDSTONE)
        .stage(5)
        .result(Items.REDSTONE)
        .build();
    public static final SeedDefinition GLOWSTONE_DUST = seed("glowstone_dust", Items.GLOWSTONE_DUST)
        .stage(5)
        .result(Items.GLOWSTONE_DUST)
        .build();
    public static final SeedDefinition EMERALD = seed("emerald", Items.EMERALD)
        .stage(5)
        .result(Items.EMERALD)
        .build();
    public static final SeedDefinition DIAMOND = seed("diamond", Items.DIAMOND)
        .stage(5)
        .result(Items.DIAMOND)
        .build();
    public static final SeedDefinition NETHERITE_SCRAP = seed("netherite_scrap", Items.NETHERITE_SCRAP)
        .stage(6)
        .result(Items.ANCIENT_DEBRIS)
        .build();
    public static final SeedDefinition ENDER_EYE = seed("ender_eye", Items.ENDER_EYE)
        .stage(5)
        .result(Items.ENDER_EYE)
        .build();

    public static List<Holder<Item>> SEEDS = List.of();
    public static Map<Holder<Item>, SeedInfo> BY_SEED = Map.of();
    private static @Nullable RegistryAccess REGISTRY_ACCESS;
    private static @Nullable List<ItemStack> SEED_PACKETS;

    static {
        validateDeclarations();
    }

    private static SeedDefinition.Builder seed(String name, ItemLike seed) {
        return SeedDefinition.seed(name, seed, BUILT::add);
    }

    public static List<SeedDefinition> definitions() {
        return List.copyOf(BUILT);
    }

    public static List<SeedDefinition> cropSeeds() {
        return BUILT.stream().filter(SeedDefinition::isCrop).toList();
    }

    public static List<SeedDefinition> packetSeeds() {
        return BUILT.stream().filter(SeedDefinition::isPacket).toList();
    }

    public static List<Holder<Item>> getAllSeeds() {
        return SEEDS;
    }

    public static @Nullable SeedInfo getSeedInfo(Holder<Item> seed) {
        return BY_SEED.get(seed);
    }

    public static @Nullable SeedInfo getSeedInfo(ResourceKey<SeedInfo> key) {
        RegistryAccess access = REGISTRY_ACCESS;
        if (access == null) return null;
        return access.lookup(ModSeedRegistry.SEED_REGISTRY).map(registry -> registry.getValue(key)).orElse(null);
    }

    public static boolean isSeed(Holder<Item> seed) {
        return BY_SEED.containsKey(seed);
    }

    public static synchronized List<ItemStack> getSeedPackets() {
        if (SEED_PACKETS == null) {
            SEED_PACKETS = SEEDS.stream().map(SeedUtil::getSeedPacket).toList();
        }
        return SEED_PACKETS;
    }

    public static synchronized void invalidateSeedPackets() {
        SEED_PACKETS = null;
    }

    public static void rebuild(RegistryAccess registries) {
        List<SeedInfo> definitions = registries.lookup(ModSeedRegistry.SEED_REGISTRY)
            .map(registry -> registry.listElements()
                .sorted(Comparator.comparing(holder -> holder.key().identifier()))
                .map(Holder.Reference::value)
                .toList())
            .orElseGet(List::of);

        Map<Holder<Item>, SeedInfo> lookup = new LinkedHashMap<>();
        for (SeedInfo definition : definitions) {
            Holder<Item> seed = definition.seed();
            if (lookup.putIfAbsent(seed, definition) != null) {
                HarvestHeritage.LOGGER.warn("Duplicate seed definition for {}, ignoring the later one", seed.getRegisteredName());
            }
        }

        BY_SEED = Collections.unmodifiableMap(lookup);
        SEEDS = List.copyOf(lookup.keySet());
        REGISTRY_ACCESS = registries;
        invalidateSeedPackets();
    }

    private static void validateDeclarations() {
        Map<ResourceKey<SeedInfo>, SeedDefinition> keys = new LinkedHashMap<>();
        Map<Holder<Item>, SeedDefinition> seeds = new LinkedHashMap<>();
        for (SeedDefinition definition : BUILT) {
            SeedDefinition duplicateKey = keys.putIfAbsent(definition.key(), definition);
            if (duplicateKey != null) {
                throw new IllegalStateException("Duplicate seed key: " + definition.key().identifier());
            }

            SeedDefinition duplicateSeed = seeds.putIfAbsent(definition.seed(), definition);
            if (duplicateSeed != null) {
                throw new IllegalStateException("Duplicate seed item: " + definition.seed().getRegisteredName());
            }
        }
    }
}
