package me.theabab2333.harvestheritage.integration.guideme;

import guideme.Guide;
import guideme.GuideBuilder;
import guideme.GuideItemSettings;
import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.api.integration.Integration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Optional;

@Integration("guideme")
public class ModGuideMEPlugin {
    public static GuideBuilder GUIDE_BUILDER = Guide.builder(HarvestHeritage.of("guide"));
    private static final GuideItemSettings GUIDE_ITEM_SETTINGS = new GuideItemSettings(
        Optional.of(Component.translatable("harvestheritage.guideme.guide_book")),
        List.of(
            Component.translatable("harvestheritage.guideme.guide_book.tooltip").withStyle(ChatFormatting.DARK_GRAY)
        ),
        Optional.empty()
    );

    public void apply() {
        var guide = GUIDE_BUILDER.itemSettings(GUIDE_ITEM_SETTINGS)
            .folder("guide")
            .build();
    }
}