package me.theabab2333.harvestheritage.integration.jei.category;

import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.integration.jei.ModJeiPlugin;
import me.theabab2333.harvestheritage.integration.jei.util.JeiRenderHelper;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.util.StyleUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
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
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class HybridRecipeCategory extends AbstractRecipeCategory<RecipeHolder<HybridRecipe>> {

    private static final int MAX_SHOWN_COLUMN = 3;
    private static final int MAX_SHOWN_ROW = 4;

    private final IDrawable arrowDefault;


    public HybridRecipeCategory(IGuiHelper guiHelper) {
        super(
            ModJeiPlugin.HYBRID_TYPE.get(),
            Component.translatable("jei.harvestheritage.hybrid"),
            guiHelper.createDrawableItemLike(ModBlocks.CROP_STAND_BLOCK),
            180,
            74
        );

        this.arrowDefault = JeiRenderHelper.getArrowDefault(guiHelper);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<HybridRecipe> holder, IFocusGroup focuses) {
        HybridRecipe recipe = holder.value();
        List<ItemStack> inputStacks = recipe.getInputSeeds().stream().map(ItemStack::new).toList();
        List<ItemStack> outputStacks = recipe.getOutputSeeds().stream().map(ItemStack::new).toList();

        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(recipe.getInputSeedPacketStacks());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStacks(recipe.getOutputSeedPacketStacks());

        builder.addInputSlot(2, 30).setStandardSlotBackground().addItemStacks(inputStacks);
        builder.addInputSlot(58, 30).setStandardSlotBackground().addItemStacks(inputStacks.reversed());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 30, 30).setStandardSlotBackground().add(ModBlocks.CROP_STAND_BLOCK);

        for (ItemStack outputStack : outputStacks) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 0, 0)
                .setStandardSlotBackground()
                .add(outputStack)
                .addRichTooltipCallback((_, tooltip) -> tooltip.add(Component.translatable("jei.harvestheritage.hybrid.tooltip")
                    .withStyle(StyleUtil.colorFromRatio())));
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<HybridRecipe> holder, IFocusGroup focuses) {
        IRecipeSlotDrawablesView recipeSlots = builder.getRecipeSlots();
        List<IRecipeSlotDrawable> outputSlots = recipeSlots.getSlots(RecipeIngredientRole.OUTPUT);
        IScrollGridWidget scrollGridWidget = builder.addScrollGridWidget(outputSlots, MAX_SHOWN_COLUMN, MAX_SHOWN_ROW);
        scrollGridWidget.setPosition(108, 1, this.getWidth(), this.getHeight(), HorizontalAlignment.LEFT, VerticalAlignment.TOP);

        builder.addRecipeArrow().setPosition(77, 5);
        builder.addDrawable(arrowDefault, 8, 4);
    }
}
