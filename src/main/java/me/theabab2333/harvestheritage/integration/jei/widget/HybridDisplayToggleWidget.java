package me.theabab2333.harvestheritage.integration.jei.widget;

import com.mojang.blaze3d.platform.InputConstants;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

import java.util.function.BooleanSupplier;

public class HybridDisplayToggleWidget implements IRecipeWidget, IJeiInputHandler {
    private static final int SIZE = 16;

    private final int x;
    private final int y;
    private final IDrawable itemIcon;
    private final IDrawable seedPacketIcon;
    private final BooleanSupplier showingSeedPackets;
    private final Runnable onToggle;

    public HybridDisplayToggleWidget(
        int x,
        int y,
        IDrawable itemIcon,
        IDrawable seedPacketIcon,
        BooleanSupplier showingSeedPackets,
        Runnable onToggle
    ) {
        this.x = x;
        this.y = y;
        this.itemIcon = itemIcon;
        this.seedPacketIcon = seedPacketIcon;
        this.showingSeedPackets = showingSeedPackets;
        this.onToggle = onToggle;
    }

    @Override
    public ScreenPosition getPosition() {
        return new ScreenPosition(
            this.x,
            this.y
        );
    }

    @Override
    public ScreenRectangle getScreenRectangle() {
        return new ScreenRectangle(
            this.x,
            this.y,
            SIZE,
            SIZE
        );
    }

    @Override
    public ScreenRectangle getArea() {
        return this.getScreenRectangle();
    }

    @Override
    public void drawWidget(
        GuiGraphicsExtractor graphics,
        double mouseX,
        double mouseY
    ) {
        boolean hovered = this.isInside(mouseX, mouseY);
        boolean showSeedPackets = this.showingSeedPackets.getAsBoolean();
        int background = showSeedPackets ? 0xFF3F6B3F : 0xFF555555;

        if (hovered) {
            background = showSeedPackets ? 0xFF528A52 : 0xFF777777;
        }

        graphics.fill(0, 0, SIZE, SIZE, 0xFF202020);
        graphics.fill(1, 1, SIZE - 1, SIZE - 1, background);

        (showSeedPackets ? this.seedPacketIcon : this.itemIcon).draw(graphics);
    }

    @Override
    public void getTooltip(
        ITooltipBuilder tooltip,
        double mouseX,
        double mouseY
    ) {
        if (this.isInside(mouseX, mouseY)) {
            tooltip.add(
                Component.translatable(
                    this.showingSeedPackets.getAsBoolean()
                    ? "jei.harvestheritage.hybrid.show_items"
                    : "jei.harvestheritage.hybrid.show_seed_packets"
                )
            );
        }
    }

    @Override
    public boolean handleInput(
        double mouseX,
        double mouseY,
        IJeiUserInput input
    ) {
        if (!this.isInside(mouseX, mouseY)) {
            return false;
        }

        InputConstants.Key key = input.getKey();
        if (key.getType() != InputConstants.Type.MOUSE || key.getValue() != InputConstants.MOUSE_BUTTON_LEFT) {
            return false;
        }

        if (input.isSimulate()) {
            return true;
        }

        this.onToggle.run();
        return true;
    }

    private boolean isInside(double mouseX, double mouseY) {
        return mouseX >= 0 && mouseX < SIZE && mouseY >= 0 && mouseY < SIZE;
    }
}
