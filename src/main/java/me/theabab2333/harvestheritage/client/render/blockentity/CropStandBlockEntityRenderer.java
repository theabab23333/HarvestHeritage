package me.theabab2333.harvestheritage.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.theabab2333.harvestheritage.block.entity.BaseCropStandBlockEntity;
import me.theabab2333.harvestheritage.block.entity.CropStandStandBlockEntity;
import me.theabab2333.harvestheritage.client.render.blockentity.state.CropStandBlockEntityRenderState;
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
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class CropStandBlockEntityRenderer implements BlockEntityRenderer<CropStandStandBlockEntity, CropStandBlockEntityRenderState> {

    private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();

    private final BlockModelResolver blockModelResolver;

    public CropStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();
    }

    @Override
    public CropStandBlockEntityRenderState createRenderState() {
        return new CropStandBlockEntityRenderState();
    }

    // make idea happy~
    @Override
    @SuppressWarnings("ConstantValue")
    public void extractRenderState(
        CropStandStandBlockEntity blockEntity,
        CropStandBlockEntityRenderState state,
        float partialTicks,
        Vec3 cameraPosition,
        ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress
    ) {
        state.block.clear();
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        state.seedPacketComponent = blockEntity.getSeedPacketComponent();
        if (state.seedPacketComponent == null) return;
        SeedComponent seedComponent = state.seedPacketComponent.seedComponent();
        if (seedComponent == null) return;

        state.stage = blockEntity.getStage();
        int renderStage = getRenderStage(blockEntity, seedComponent);

        BlockState blockState = Blocks.WHEAT.defaultBlockState().setValue(CropBlock.AGE, renderStage);
        this.blockModelResolver.update(state.block, blockState, BLOCK_DISPLAY_CONTEXT);
    }

    public static int getRenderStage(BaseCropStandBlockEntity blockEntity, SeedComponent seedComponent) {
        int maxStage = 7;
        int stateStage = blockEntity.getStage();
        Item seedItem = seedComponent.getSeed();
        var seedInfo = ModSeeds.ALL_SEED.get(seedItem);
        int seedStage = seedInfo != null ? seedInfo.stage() : 0;

        int renderStage;
        if (seedStage == 0) {
            renderStage = 0;
        } else if (seedStage >= stateStage) {
            if (seedStage == stateStage) {
                renderStage = maxStage;
            } else {
                renderStage = stateStage + (maxStage - stateStage) * stateStage / seedStage;
            }
        } else {
            renderStage = seedStage + (maxStage - seedStage) * seedStage / stateStage;
        }
        return renderStage;
    }

    @Override
    public void submit(
        CropStandBlockEntityRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState cameraRenderState
    ) {
        if (state.block.isEmpty()) return;
        poseStack.pushPose();
        state.block.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
