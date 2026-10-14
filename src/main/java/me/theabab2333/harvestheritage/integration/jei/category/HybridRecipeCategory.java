package me.theabab2333.harvestheritage.integration.jei.category;

import me.theabab2333.harvestheritage.common.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.integration.jei.ModJeiPlugin;
import me.theabab2333.harvestheritage.integration.jei.util.JeiRenderHelper;
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
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

// 妈的怎么看怎么像屎
public class HybridRecipeCategory extends AbstractRecipeCategory<RecipeHolder<HybridRecipe>> {

    private final IDrawable cropStand;

    public HybridRecipeCategory(IGuiHelper guiHelper) {
        super(
            ModJeiPlugin.HYBRID_TYPE.get(),
            Component.translatable("jei.harvestheritage.hybrid"),
            guiHelper.createDrawableItemLike(ModBlocks.CROP_STAND_BLOCK),
            180,
            74
        );

        this.cropStand = JeiRenderHelper.getCropStand(guiHelper);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<HybridRecipe> holder, IFocusGroup focuses) {
        HybridRecipe recipe = holder.value();
        List<ItemStack> inputStacks = recipe.getInputSeeds().stream().map(ItemStack::new).toList();

        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(recipe.getInputSeedPacketStacks());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStacks(recipe.getOutputSeedPacketStacks());

        builder.addInputSlot(2, 30).setStandardSlotBackground().add(inputStacks.getFirst());
        builder.addInputSlot(58, 30).setStandardSlotBackground().add(inputStacks.getLast());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 30, 30).setStandardSlotBackground().add(ModBlocks.CROP_STAND_BLOCK);

        List<Holder<Item>> outputStacks = recipe.getOutputSeeds();
        for (Holder<Item> itemHolder : outputStacks) {
            builder.addSlot(RecipeIngredientRole.OUTPUT)
                .add(itemHolder.value())
                .addRichTooltipCallback((_, tooltip) -> tooltip.add(Component.translatable("jei.harvestheritage.hybrid.tooltip")
                    .withStyle(StyleUtil.colorFromRatio())));
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<HybridRecipe> holder, IFocusGroup focuses) {
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

        builder.addRecipeArrowWidget().setPosition(77, 5);
        builder.addDrawableWidget(this.cropStand).setPosition(8, 4);
    }
}
