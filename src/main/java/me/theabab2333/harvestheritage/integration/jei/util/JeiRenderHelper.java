package me.theabab2333.harvestheritage.integration.jei.util;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;

public class JeiRenderHelper {
    public static IDrawable getArrowDefault(IGuiHelper helper) {
        return helper.drawableBuilder(JeiTextures.CROP_STAND, 0, 0, 60, 60)
            .setTextureSize(60, 60)
            .build();
    }
}
