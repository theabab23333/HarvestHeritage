package me.theabab2333.harvestheritage.integration.jei.util;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.minecraft.resources.Identifier;

public class JeiTextures {
    public static final Identifier CROP_STAND = texture("crop_stand");
    public static final Identifier BIG_SLOT = texture("big_slot");

    private static Identifier texture(String path) {
        return HarvestHeritage.of("textures/jei/" + path + ".png");
    }
}
