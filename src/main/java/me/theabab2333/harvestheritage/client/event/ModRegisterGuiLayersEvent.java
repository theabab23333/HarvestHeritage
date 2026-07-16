package me.theabab2333.harvestheritage.client.event;

import com.mojang.blaze3d.platform.Window;
import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.block.entity.BaseCropStandBlockEntity;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.component.SeedPacketComponent;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import org.joml.Vector2ic;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = HarvestHeritage.MODID, value = Dist.CLIENT)
public class ModRegisterGuiLayersEvent {
    @SubscribeEvent
    public static void onRegister(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
            HarvestHeritage.of("tooltip"), (graphicsExtractor, deltaTracker) -> {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.options.hideGui) return;
                LocalPlayer player = minecraft.player;
                Level level = minecraft.level;
                if (player == null || minecraft.isPaused() || level == null) return;
                Item glassItem = ModItems.MAGNIFYING_GLASS.asItem();
                if (!player.getMainHandItem().is(glassItem) && !player.getOffhandItem()
                    .is(glassItem) && !player.getItemBySlot(ArmorType.HELMET.getSlot()).is(glassItem)) {
                    return;
                }

                Window window = Minecraft.getInstance().getWindow();
                int guiScaledWidth = window.getGuiScaledWidth();
                int guiScaledHeight = window.getGuiScaledHeight();

                HitResult hitResult = minecraft.hitResult;
                if (hitResult == null) return;
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                    BlockState blockState = level.getBlockState(blockPos);
                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                    if (blockState.is(Blocks.AIR) || blockEntity == null) return;
                    renderCropStandTooltip(graphicsExtractor, blockEntity, guiScaledWidth, guiScaledHeight);
                }
            }
        );
    }

    public static void renderCropStandTooltip(
        GuiGraphicsExtractor graphicsExtractor,
        BlockEntity blockEntity,
        int screenWidth,
        int screenHeight
    ) {
        if (blockEntity instanceof BaseCropStandBlockEntity cropStandBlockEntity) {
            SeedPacketComponent packetComponent = cropStandBlockEntity.getSeedPacketComponent();
            if (packetComponent != null) {
                int tooltipX = screenWidth / 2;
                int tooltipY = screenHeight / 2;
                Font font = Minecraft.getInstance().font;
                SeedComponent seedComponent = packetComponent.seedComponent();
                ItemStack itemStack = seedComponent.getSeed().getDefaultInstance();
                List<Component> tooltips = new ArrayList<>();


                tooltips.add(Component.translatable(
                        "item.harvestheritage.seed.tooltip.seed",
                        SeedUtil.getSeedName(itemStack.getItem())
                    )
                    .withStyle(ChatFormatting.GREEN));

                var seedInfo = ModSeeds.ALL_SEED.get(itemStack.getItem());
                if (seedInfo != null) {
                    tooltips.add(Component.translatable("item.harvestheritage.seed.tooltip.stage", seedInfo.stage())
                        .withStyle(ChatFormatting.LIGHT_PURPLE));
                }
                tooltips.add(Component.translatable("block.harvestheritage.crop_stand.tooltip.stage", cropStandBlockEntity.getStage())
                    .withStyle(ChatFormatting.DARK_PURPLE));
                tooltips.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.speed", packetComponent.speed())
                    .withStyle(ChatFormatting.BLUE));
                tooltips.add(Component.translatable("item.harvestheritage.seed_packet.tooltip.output", packetComponent.output())
                    .withStyle(ChatFormatting.GOLD));


                renderTooltip(graphicsExtractor, tooltips, itemStack, font, tooltipX, tooltipY);
            }
        }
    }

    private static void renderTooltip(
        GuiGraphicsExtractor graphicsExtractor,
        List<Component> tooltips,
        ItemStack itemStack,
        Font font,
        int x,
        int y
    ) {
        ClientTooltipPositioner tooltipPositioner = DefaultTooltipPositioner.INSTANCE;
        List<ClientTooltipComponent> components = tooltips.stream()
            .map(Component::getVisualOrderText)
            .map(ClientTooltipComponent::create)
            .toList();
        if (components.isEmpty()) return;

        int width = 0;
        int height;
        if (components.size() == 1) {
            height = -2;
        } else {
            height = 0;
        }

        for (ClientTooltipComponent component : components) {
            width = Math.max(component.getWidth(font), width);
            height += component.getHeight(font);
        }

        Vector2ic vector2ic = tooltipPositioner.positionTooltip(
            graphicsExtractor.guiWidth(),
            graphicsExtractor.guiHeight(),
            x,
            y,
            width,
            height
        );
        int vector2icX = vector2ic.x() + 16;
        int vector2icY = vector2ic.y();
        graphicsExtractor.pose().pushMatrix();
        graphicsExtractor.fillGradient(vector2icX - 6, vector2icY - 6, vector2icX + width + 6, vector2icY + 74, 0x55383838, 0x552C2C2C);
        graphicsExtractor.fill(
            vector2icX - 4,
            vector2icY - 4,
            vector2icX + width + 4,
            vector2icY + 72,
            0x55202020
        ); // 0x55383838 0x55202020
        graphicsExtractor.item(itemStack, vector2icX, vector2icY);
        vector2icY += 16;
        for (int i = 0; i < components.size(); i++) {
            ClientTooltipComponent clientTooltipComponent = components.get(i);
            clientTooltipComponent.extractText(graphicsExtractor, font, vector2icX, vector2icY + (i * 10));
        }
        graphicsExtractor.pose().popMatrix();
    }
}
