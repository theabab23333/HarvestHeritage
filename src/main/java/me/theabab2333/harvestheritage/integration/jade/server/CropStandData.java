package me.theabab2333.harvestheritage.integration.jade.server;

import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record CropStandData(SeedPacketComponent seedPacketComponent, int stage) {
    public static final StreamCodec<RegistryFriendlyByteBuf, CropStandData> STREAM_CODEC = StreamCodec.composite(
        SeedPacketComponent.STREAM_CODEC,
        CropStandData::seedPacketComponent,
        ByteBufCodecs.INT,
        CropStandData::stage,
        CropStandData::new
    );
}
