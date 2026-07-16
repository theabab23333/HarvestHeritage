package me.theabab2333.harvestheritage.integration.jade.client;

import me.theabab2333.harvestheritage.integration.jade.ModJadePlugin;
import me.theabab2333.harvestheritage.integration.jade.server.CropStandBlockServerDataProvider;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
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
    @SuppressWarnings("ConstantValue")
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig config) {
        var data = CropStandBlockServerDataProvider.INSTANCE.streamData(blockAccessor);

        if (data == null) {
            this.noSeed(tooltip);
            return;
        }

        IThemeHelper theme = IThemeHelper.get();
        var seedPacketComponent = data.seedPacketComponent();
        var seedComponent = seedPacketComponent.seedComponent();
        if (seedComponent == null) {
            this.noSeed(tooltip);
            return;
        }
        var seed = seedComponent.getSeed();
        var seedInfo = SeedUtil.getSeedInfo(seed);
        int stage = data.stage();

        MutableComponent value = Component.translatable("jade.fraction", stage, seedInfo.stage());
        tooltip.add(JadeUI.item(seed.getDefaultInstance()));
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

    @Override
    public Identifier getUid() {
        return ModJadePlugin.CROP_STAND_BLOCK_DATA_PROVIDER;
    }

    private void noSeed(ITooltip tooltip) {
        tooltip.add(
            Component.translatable(
                "jade.harvestheritage.crop_stand.no_seed"
            ).withStyle(ChatFormatting.RED)
        );
    }
}
