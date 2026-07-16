package me.theabab2333.harvestheritage.integration.jade;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.block.BaseCropStandBlock;
import me.theabab2333.harvestheritage.integration.jade.client.CropStandBlockComponentProvider;
import me.theabab2333.harvestheritage.integration.jade.server.CropStandBlockServerDataProvider;
import net.minecraft.resources.Identifier;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModJadePlugin implements IWailaPlugin {

    public static final Identifier CROP_STAND_BLOCK_DATA_PROVIDER = HarvestHeritage.of("crop_stand_block_data_provider");

    @Override
    public void register(IWailaCommonRegistration reg) {
        reg.registerBlockDataProvider(CropStandBlockServerDataProvider.INSTANCE, BaseCropStandBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(CropStandBlockComponentProvider.INSTANCE, BaseCropStandBlock.class);
    }
}
