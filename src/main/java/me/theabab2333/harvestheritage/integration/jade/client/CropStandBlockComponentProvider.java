package me.theabab2333.harvestheritage.integration.jade.client;

import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.integration.jade.ModJadePlugin;
import me.theabab2333.harvestheritage.integration.jade.server.CropStandBlockServerDataProvider;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.api.ui.JadeUI;

public class CropStandBlockComponentProvider implements IBlockComponentProvider {

    public static final CropStandBlockComponentProvider INSTANCE = new CropStandBlockComponentProvider();

    public CropStandBlockComponentProvider() {

    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig config) {
        var data = CropStandBlockServerDataProvider.INSTANCE.streamData(blockAccessor);

        tooltip.add(
            Component.translatable(
                "block.harvestheritage.crop_stand.tooltip.light",
                blockAccessor.getLevel().getMaxLocalRawBrightness(blockAccessor.getPosition())
            )
        );

        if (data == null) {
            this.noSeed(tooltip, blockAccessor, config);
            return;
        }

        var seedPacketComponent = data.seedPacketComponent();
        var seedInfo = SeedUtil.getSeedInfo(seedPacketComponent.seed());
        var stage = data.stage();

        if (seedInfo == null) {
            this.noSeed(tooltip, blockAccessor, config);
        } else {
            this.hasSeed(tooltip, blockAccessor, config, seedPacketComponent, seedInfo, stage);
        }
    }

    @Override
    public Identifier getUid() {
        return ModJadePlugin.CROP_STAND_BLOCK_DATA_PROVIDER;
    }

    private void hasSeed(
        ITooltip tooltip,
        BlockAccessor blockAccessor,
        IPluginConfig config,
        SeedPacketComponent seedPacketComponent,
        SeedInfo seedInfo,
        int stage
    ) {

        IThemeHelper theme = IThemeHelper.get();

        MutableComponent value = Component.translatable("jade.fraction", stage, seedInfo.stage());
        tooltip.add(JadeUI.item(new ItemStack(seedInfo.seed())));
        tooltip.add(
            Component.translatable(
                "jade.harvestheritage.crop_stand.stage",
                stage == seedInfo.stage() ? theme.success(value) : theme.info(value)
            )
        );
        tooltip.add(
            Component.translatable(
                "item.harvestheritage.seed_packet.tooltip.speed", seedPacketComponent.speed()
            )
        );
        tooltip.add(
            Component.translatable(
                "item.harvestheritage.seed_packet.tooltip.output", seedPacketComponent.output()
            )
        );
    }

    private void noSeed(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig config) {
        tooltip.add(
            Component.translatable(
                "jade.harvestheritage.crop_stand.no_seed"
            ).withStyle(ChatFormatting.RED)
        );
    }
}
