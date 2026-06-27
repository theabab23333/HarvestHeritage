package me.theabab2333.harvestheritage.integration.jei.category;

import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.integration.jei.ModJeiPlugin;
import me.theabab2333.harvestheritage.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.util.SeedUtil;
import me.theabab2333.harvestheritage.util.StyleUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class HybridRecipeCategory extends AbstractRecipeCategory<RecipeHolder<HybridRecipe>> {

    public HybridRecipeCategory(IGuiHelper guiHelper) {
        super(
            ModJeiPlugin.HYBRID_TYPE.get(),
            Component.translatable("jei.harvestheritage.hybrid"),
            guiHelper.createDrawableItemLike(ModBlocks.CROP_STAND_BLOCK),
            162,
            60
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<HybridRecipe> holder, IFocusGroup focuses) {
        HybridRecipe recipe = holder.value();
        List<ItemStack> inputStacks = recipe.getInputSeeds().stream()
            .map(ItemStack::new)
            .toList();
        List<ItemStack> outputStacks = recipe.getOutputSeeds().stream()
            .map(ItemStack::new)
            .toList();

        // 为JEI配方匹配构建不可见种子包物品
        List<ItemStack> inputSeedPackets = inputStacks.stream()
            .map(stack -> {
                var info = ModSeeds.ALL_SEED.get(stack.getItem());
                if (info != null) {
                    var patch = SeedUtil.createSeedComponentPatch(stack.getItem(), info);
                    return new ItemStack(ModItems.SEED_PACKET, 1, patch);
                }
                return ItemStack.EMPTY;
            })
            .filter(stack -> !stack.isEmpty())
            .toList();
        List<ItemStack> outputSeedPackets = recipe.getOutputSeeds().stream()
            .map(h -> {
                var comp = SeedUtil.getSeedComponent(h.value());
                DataComponentPatch patch = DataComponentPatch.builder()
                    .set(ModDataComponents.SEED_COMPONENT.get(), comp)
                    .build();
                return new ItemStack(ModItems.SEED_PACKET, 1, patch);
            })
            .toList();

        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(inputSeedPackets);
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStacks(outputSeedPackets);

        builder
            .addInputSlot(52, 1)
            .setStandardSlotBackground()
            .addItemStacks(inputStacks);

        builder
            .addSlot(RecipeIngredientRole.RENDER_ONLY, 74, 1)
            .add(ModBlocks.CROP_STAND_BLOCK);

        builder
            .addInputSlot(96, 1)
            .setStandardSlotBackground()
            .addItemStacks(inputStacks.reversed());

        int perRow = 9;
        int slotSize = 18;
        int count = outputStacks.size();
        int startX = count < perRow ? (getWidth() - count * slotSize) / 2 + 2 : 2;
        for (int i = 0; i < count; i++) {
            builder
                .addSlot(RecipeIngredientRole.OUTPUT, startX + (i % perRow) * slotSize, 25 + (i / perRow) * slotSize)
                .setStandardSlotBackground()
                .add(outputStacks.get(i))
                .addRichTooltipCallback(
                    (_, tooltip) ->
                        tooltip.add(Component.translatable("jei.harvestheritage.hybrid.tooltip").withStyle(StyleUtil.colorFromRatio()))
                );
        }
    }
}
