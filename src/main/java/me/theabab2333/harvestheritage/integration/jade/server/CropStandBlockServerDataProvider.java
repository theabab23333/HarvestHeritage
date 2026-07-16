package me.theabab2333.harvestheritage.integration.jade.server;

import me.theabab2333.harvestheritage.block.entity.BaseCropStandBlockEntity;
import me.theabab2333.harvestheritage.integration.jade.ModJadePlugin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.StreamServerDataProvider;

public class CropStandBlockServerDataProvider implements StreamServerDataProvider<BlockAccessor, CropStandData> {

    public static final CropStandBlockServerDataProvider INSTANCE = new CropStandBlockServerDataProvider();

    public CropStandBlockServerDataProvider() {

    }

    @Override
    @SuppressWarnings("ConstantValue")
    public @Nullable CropStandData streamData(BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof BaseCropStandBlockEntity blockEntity) {
            var component = blockEntity.getSeedPacketComponent();
            if (component == null) return null;
            return new CropStandData(component, blockEntity.getStage());
        } else {
            return null;
        }
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CropStandData> streamCodec() {
        return CropStandData.STREAM_CODEC;
    }

    @Override
    public Identifier getUid() {
        return ModJadePlugin.CROP_STAND_BLOCK_DATA_PROVIDER;
    }
}
