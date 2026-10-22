package me.theabab2333.harvestheritage.init;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfigs {
    // Server config
    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue SEED_SPEED_MAX = SERVER_BUILDER.comment("Maximum seed growth rate")
        .defineInRange("seed_speed_max", 31, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue OUTPUT_MAX = SERVER_BUILDER.comment("Maximum output count")
        .defineInRange("output_max", 31, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec SERVER_SPEC = SERVER_BUILDER.build();

    // Client config
    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SYNCED, ModConfigs.SERVER_SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ModConfigs.CLIENT_SPEC);
    }
}
