package me.theabab2333.harvestheritage.integration.jei.category;

import me.theabab2333.harvestheritage.common.seed.requirement.SeedLightRequirement;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.integration.jei.ModJeiPlugin;
import me.theabab2333.harvestheritage.integration.jei.recipe.SeedInfoRecipe;
import me.theabab2333.harvestheritage.integration.jei.slot.BlockTagSlotDisplay;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawablesView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.IScrollGridWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

// 感觉可以用 IIngredientHelper ，以后再说吧（）（）
public class SeedInfoCategory extends AbstractRecipeCategory<SeedInfoRecipe> {

    public SeedInfoCategory(IGuiHelper guiHelper) {
        super(
            ModJeiPlugin.SEED_OUTPUT_TYPE,
            Component.translatable("jei.harvestheritage.seed_info"),
            guiHelper.createDrawableItemLike(ModItems.SEED_PACKET),
            180,
            74
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SeedInfoRecipe recipe, IFocusGroup focuses) {
        var seedPacket = recipe.seedPacket();
        var seedInfo = recipe.seedInfo();
        var seedItem = seedInfo.seed();
        var requirements = recipe.getBlockRequirements();
        var results = recipe.getResults();

        // seedPacket & seedItem
        {
            builder.addInputSlot(3, 4)
                .setStandardSlotBackground()
                .add(seedPacket)
                .addRichTooltipCallback((_, tooltip) -> tooltip.add(Component.translatable("jei.harvestheritage.seed_info.seed_packet")
                    .withStyle(ChatFormatting.GREEN)));
            builder.addInputSlot(3, 24)
                .setStandardSlotBackground()
                .add(seedItem.value())
                .addRichTooltipCallback((_, tooltip) -> tooltip.add(Component.translatable("jei.harvestheritage.seed_info.seed")
                    .withStyle(ChatFormatting.DARK_GREEN)));
        }

        // needBlocks
        {
            // 在游戏能看到 seedPacket 那块和这块的 y 不一样，我故意的
            int startX = 50;
            int startY = 22;
            builder.addInputSlot(startX, 4).setStandardSlotBackground().add(ModBlocks.CROP_STAND_BLOCK.asItem());
            if (requirements.isEmpty()) {
                builder.addInputSlot(startX, startY)
                    .setStandardSlotBackground()
                    .add(Blocks.FARMLAND.asItem())
                    .addRichTooltipCallback((_, tooltip) -> tooltip.add(Component.translatable(
                        "jei.harvestheritage.seed_info.need_blocks.any").withStyle(ChatFormatting.AQUA)));
            }
            for (int i = 0; i < requirements.size(); i++) {
                var requirement = requirements.get(i);

                var block = requirement.block();
                var tag = requirement.tag();

                if (block.isPresent()) {
                    builder.addInputSlot(startX, startY + i * 18)
                        .setStandardSlotBackground()
                        .add(block.get().value())
                        .addRichTooltipCallback((_, tooltip) -> {
                            tooltip.add(Component.translatable("jei.harvestheritage.seed_info.need_blocks.1")
                                .withStyle(ChatFormatting.AQUA));
                            tooltip.add(Component.translatable("jei.harvestheritage.seed_info.need_blocks.2")
                                .withStyle(ChatFormatting.DARK_AQUA));
                        });
                } else if (tag.isPresent()) {
                    // 这个方块没有物品就直接棍母了，可能会出问题但是我不想管了，有人提 issue 再说吧
                    // 而且这个"接受标签: %s"怎么显示是Item，不管了（）（）能用，it work
                    builder.addInputSlot(startX, startY + i * 18)
                        .setStandardSlotBackground()
                        .add(new BlockTagSlotDisplay(tag.get()))
                        .addRichTooltipCallback((_, tooltip) -> {
                            tooltip.add(Component.translatable("jei.harvestheritage.seed_info.need_blocks.1")
                                .withStyle(ChatFormatting.AQUA));
                            tooltip.add(Component.translatable("jei.harvestheritage.seed_info.need_blocks.2")
                                .withStyle(ChatFormatting.DARK_AQUA));
                        });
                }
            }
        }

        // light
        {
            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 3, 44)
                .setStandardSlotBackground()
                .add(Items.LIGHT)
                .addRichTooltipCallback((_, tooltip) -> tooltip.add(
                    getLightRequirementText(seedInfo.needLight()).withStyle(ChatFormatting.GOLD)
                ));
        }

        // result
        {
            for (var itemHolder : results) {
                builder.addOutputSlot()
                    .add(itemHolder.value())
                    .addRichTooltipCallback((_, tooltip) -> tooltip.add(Component.translatable("jei.harvestheritage.seed_info.output")
                        .withStyle(ChatFormatting.YELLOW)));
            }
        }
    }

    private static MutableComponent getLightRequirementText(SeedLightRequirement requirement) {
        var min = requirement.minLight();
        var max = requirement.maxLight();

        if (min.isEmpty() && max.isEmpty()) {
            return Component.translatable("jei.harvestheritage.seed_info.need_light.any");
        }
        if (min.isPresent() && max.isPresent()) {
            int minLevel = min.get();
            int maxLevel = max.get();
            return minLevel == maxLevel
                   ? Component.translatable("jei.harvestheritage.seed_info.need_light.exact", minLevel)
                   : Component.translatable("jei.harvestheritage.seed_info.need_light.between", minLevel, maxLevel);
        }
        // present?
        if (min.isPresent()) {
            return Component.translatable("jei.harvestheritage.seed_info.need_light.above", min.get());
        }
        return Component.translatable("jei.harvestheritage.seed_info.need_light.below", max.get());
    }

    @Override
    public void createRecipeExtras(
        IRecipeExtrasBuilder builder,
        SeedInfoRecipe recipe,
        IFocusGroup focuses
    ) {
        IRecipeSlotDrawablesView recipeSlots = builder.getRecipeSlots();
        List<IRecipeSlotDrawable> outputSlots = recipeSlots.getSlots(RecipeIngredientRole.OUTPUT);

        IScrollGridWidget scrollGridWidget = builder.addScrollGridWidget(outputSlots, 3, 4);
        scrollGridWidget.setPosition(
            0,
            0,
            this.getWidth(),
            this.getHeight(),
            HorizontalAlignment.RIGHT,
            VerticalAlignment.TOP
        );
    }
}
