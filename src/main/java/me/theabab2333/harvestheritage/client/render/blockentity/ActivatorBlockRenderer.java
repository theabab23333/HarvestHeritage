package me.theabab2333.harvestheritage.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.theabab2333.harvestheritage.common.block.ActivatorBlock;
import me.theabab2333.harvestheritage.common.block.entity.ActivatorBlockEntity;
import me.theabab2333.harvestheritage.client.render.blockentity.state.ActivatorBlockRenderState;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ActivatorBlockRenderer implements BlockEntityRenderer<ActivatorBlockEntity, ActivatorBlockRenderState> {

    private final ItemModelResolver itemModelResolver;

    public ActivatorBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public ActivatorBlockRenderState createRenderState() {
        return new ActivatorBlockRenderState();
    }

    @Override
    public void extractRenderState(
        ActivatorBlockEntity blockEntity,
        ActivatorBlockRenderState state,
        float partialTicks,
        Vec3 cameraPosition,
        ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress
    ) {
        state.block.clear();
        state.itemModel.clear();
        if (!blockEntity.getBlockState().getValue(ActivatorBlock.LIT)) return;
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        Level level = blockEntity.getLevel();
        if (level == null) return;

        state.animTime = (level.getGameTime() % 360000L) + partialTicks;

        ItemStack stack = new ItemStack(ModItems.ZZZZ.get());

        this.itemModelResolver.updateForTopItem(state.itemModel, stack, ItemDisplayContext.FIXED, level, null, 0);
    }

    @Override
    public void submit(
        ActivatorBlockRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState cameraRenderState
    ) {
        if (!state.block.isEmpty()) {
            poseStack.pushPose();
            state.block.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }

        if (state.itemModel.isEmpty()) return;

        float t = state.animTime;

        poseStack.pushPose();
        poseStack.translate(0.5, 1.75, 0.5);

        // 26.3 的 PoseStack 只接受 Matrix4fc / Transformation，旋转改用 rotateDegrees
        poseStack.rotateDegrees(Axis.XP, t * 2F);
        poseStack.rotateDegrees(Axis.YP, t * 2F);
        poseStack.rotateDegrees(Axis.ZP, t);

        float s = (float) Math.sin(t * 0.02F);
        poseStack.scale(-s, -s, -s);

        state.itemModel.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
