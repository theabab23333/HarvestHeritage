package me.theabab2333.harvestheritage.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.theabab2333.harvestheritage.block.entity.ScaffoldingCropStandBlockEntity;
import me.theabab2333.harvestheritage.client.render.blockentity.state.ScaffoldingCropStandBlockEntityRenderState;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModSeeds;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ScaffoldingCropStandBlockEntityRenderer
    implements BlockEntityRenderer<ScaffoldingCropStandBlockEntity, ScaffoldingCropStandBlockEntityRenderState> {

    private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();

    private final BlockModelResolver blockModelResolver;

    public ScaffoldingCropStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();
    }

    @Override
    public ScaffoldingCropStandBlockEntityRenderState createRenderState() {
        return new ScaffoldingCropStandBlockEntityRenderState();
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void extractRenderState(
        ScaffoldingCropStandBlockEntity blockEntity,
        ScaffoldingCropStandBlockEntityRenderState state,
        float partialTicks,
        Vec3 cameraPosition,
        ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress
    ) {
        state.block.clear();
        state.block1.clear();
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        state.seedPacketComponent = blockEntity.getSeedPacketComponent();
        if (state.seedPacketComponent == null) return;
        SeedComponent seedComponent = state.seedPacketComponent.seedComponent();
        if (seedComponent == null) return;

        state.stage = blockEntity.getStage();

        BlockState azaleaState = Blocks.AZALEA.defaultBlockState();
        this.blockModelResolver.update(state.block, azaleaState, BLOCK_DISPLAY_CONTEXT);

        Item seedItem = seedComponent.getSeed();
        var seedInfo = ModSeeds.ALL_SEED.get(seedItem);
        if (seedInfo != null && state.stage == seedInfo.stage()) {
            BlockState sporeState = Blocks.SPORE_BLOSSOM.defaultBlockState();
            this.blockModelResolver.update(state.block1, sporeState, BLOCK_DISPLAY_CONTEXT);
        }
    }

    @Override
    public void submit(
        ScaffoldingCropStandBlockEntityRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState cameraRenderState
    ) {
        if (!state.block.isEmpty()) {
            poseStack.pushPose();
            poseStack.scale(0.8F, 0.8F, 0.8F);
            poseStack.translate(0.125F, 0, 0.125F);
            state.block.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }

        if (!state.block1.isEmpty()) {
            poseStack.pushPose();
            poseStack.scale(0.625F, 0.625F, 0.625F);
            poseStack.translate(0.3F, -1, 0.3F);
            state.block1.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, -0);
            poseStack.popPose();
        }
    }
}
