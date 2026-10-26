package me.theabab2333.harvestheritage.util;

import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.common.seed.requirement.SeedBlockRequirement;
import me.theabab2333.harvestheritage.init.ModConfigs;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.seed.ModSeeds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import org.jspecify.annotations.Nullable;

import java.util.List;

// 像粪坑
public class SeedUtil {
    public static Component getSeedName(Item item) {
        return item.getDefaultInstance().getComponents().getOrDefault(DataComponents.ITEM_NAME, CommonComponents.EMPTY);
    }

    public static String getPath(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public static @Nullable SeedInfo getSeedInfo(Item item) {
        return getSeedInfo(item.builtInRegistryHolder);
    }

    public static @Nullable SeedInfo getSeedInfo(Holder<Item> item) {
        return ModSeeds.getSeedInfo(item);
    }

    public static @Nullable SeedPacketComponent getSeedPacketComponent(ItemStack itemStack) {
        return itemStack.get(ModDataComponents.SEED_PACKET_COMPONENT);
    }

    public static DataComponentPatch createSeedPacketPatch(SeedPacketComponent component) {
        return DataComponentPatch.builder().set(ModDataComponents.SEED_PACKET_COMPONENT.get(), component).build();
    }

    public static DataComponentPatch createSeedPacketPatch(Holder<Item> seedItem) {
        return createSeedPacketPatch(SeedPacketComponent.of(seedItem));
    }

    public static ItemStack getSeedPacket(Holder<Item> seedItem) {
        return new ItemStack(ModItems.SEED_PACKET, 1, createSeedPacketPatch(seedItem));
    }

    public static ItemStack getSeedPacket(Item seedItem) {
        return getSeedPacket(seedItem.builtInRegistryHolder);
    }

    public static boolean matchesGrowBlocks(
        LevelReader level,
        BlockPos pos,
        List<SeedBlockRequirement> blocks
    ) {
        for (int i = 0; i < blocks.size(); i++) {
            var state = level.getBlockState(pos.below(i + 1));
            if (!blocks.get(i).matches(state)) return false;
        }
        return true;
    }

    public static int rollStat(RandomSource random, int val1, int val2, int min, int max) {
        int minVal = Math.min(val1, val2);
        int avgVal = (val1 + val2) / 2;
        double roll = random.nextDouble();
        if (roll < 0.05) {
            return Math.max(min, minVal - random.nextInt(2) - 1);
        } else if (roll < 0.8) {
            return Math.min(max, avgVal + random.nextInt(2) + 1);
        } else {
            return avgVal;
        }
    }

    public static Holder<Item> rollSeed(RandomSource random, Holder<Item> seed1, Holder<Item> seed2, List<Holder<Item>> hybrids) {
        if (random.nextDouble() < 0.2) {
            return random.nextBoolean() ? seed1 : seed2;
        }
        if (!hybrids.isEmpty()) {
            return hybrids.get(random.nextInt(hybrids.size()));
        }
        return random.nextBoolean() ? seed1 : seed2;
    }

    public static SeedPacketComponent mergeSeedPackets(
        RandomSource random,
        SeedPacketComponent comp1,
        SeedPacketComponent comp2,
        List<Holder<Item>> hybrids
    ) {
        int maxSpeed = ModConfigs.SEED_SPEED_MAX.getAsInt();
        int outputMax = ModConfigs.OUTPUT_MAX.getAsInt();
        int speed = rollStat(random, comp1.speed(), comp2.speed(), 1, maxSpeed);
        int output = rollStat(random, comp1.output(), comp2.output(), 1, outputMax);
        Holder<Item> seed = rollSeed(random, comp1.seed(), comp2.seed(), hybrids);
        return new SeedPacketComponent(seed, speed, output);
    }
}
