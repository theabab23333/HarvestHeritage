package me.theabab2333.harvestheritage.client.render.blockentity.state;

import me.theabab2333.harvestheritage.common.component.SeedPacketComponent;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class CropStandBlockEntityRenderState extends BlockEntityRenderState {
    public SeedPacketComponent seedPacketComponent;
    public int stage;
    public BlockModelRenderState block = new BlockModelRenderState();
}
