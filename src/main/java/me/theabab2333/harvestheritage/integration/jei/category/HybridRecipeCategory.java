package me.theabab2333.harvestheritage.integration.jei.category;

import me.theabab2333.harvestheritage.common.recipe.HybridRecipe;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.integration.jei.ModJeiPlugin;
import me.theabab2333.harvestheritage.integration.jei.util.JeiRenderHelper;
import me.theabab2333.harvestheritage.integration.jei.widget.HybridDisplayToggleWidget;
import me.theabab2333.harvestheritage.util.SeedUtil;
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// stupid
public class HybridRecipeCategory extends AbstractRecipeCategory<RecipeHolder<HybridRecipe>> {
    private static final String INPUT_FIRST_SLOT = "hybrid_input_first";
    private static final String INPUT_LAST_SLOT = "hybrid_input_last";
    private static final String OUTPUT_SLOT_PREFIX = "hybrid_output_";

    private final IDrawable cropStand;
    private final IGuiHelper guiHelper;
    private final Map<Identifier, HybridDisplayMode> displayModes = new HashMap<>();

    public HybridRecipeCategory(IGuiHelper guiHelper) {
        super(
            ModJeiPlugin.HYBRID_TYPE.get(),
            Component.translatable("jei.harvestheritage.hybrid"),
            guiHelper.createDrawableItemLike(ModBlocks.CROP_STAND_BLOCK),
            180,
            74
        );

        this.cropStand = JeiRenderHelper.getCropStand(guiHelper);
        this.guiHelper = guiHelper;
    }

    @Override
    public void setRecipe(
        IRecipeLayoutBuilder builder,
        RecipeHolder<HybridRecipe> holder,
        IFocusGroup focuses
    ) {
        HybridRecipe recipe = holder.value();
        List<ItemStack> inputStacks = recipe.getInputSeeds().stream().map(ItemStack::new).toList();

        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(recipe.getInputSeedPacketStacks());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStacks(recipe.getOutputSeedPacketStacks());

        builder.addInputSlot(2, 30).setSlotName(INPUT_FIRST_SLOT).setStandardSlotBackground().add(inputStacks.getFirst());
        builder.addInputSlot(58, 30).setSlotName(INPUT_LAST_SLOT).setStandardSlotBackground().add(inputStacks.getLast());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 30, 30).setStandardSlotBackground().add(ModBlocks.CROP_STAND_BLOCK);

        List<Holder<Item>> outputStacks = recipe.getOutputSeeds();
        for (int i = 0; i < outputStacks.size(); i++) {
            Holder<Item> itemHolder = outputStacks.get(i);
            builder.addSlot(RecipeIngredientRole.OUTPUT)
                .setSlotName(OUTPUT_SLOT_PREFIX + i)
                .add(itemHolder.value())
                .addRichTooltipCallback((_, tooltip) -> tooltip.add(Component.translatable("jei.harvestheritage.hybrid.tooltip")
                    .withStyle(StyleUtil.colorFromRatio())));
        }
    }

    @Override
    public void createRecipeExtras(
        IRecipeExtrasBuilder builder,
        RecipeHolder<HybridRecipe> holder,
        IFocusGroup focuses
    ) {
        // scroll
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

        // button
        this.addDisplayToggle(builder, holder);
    }

    private void addDisplayToggle(
        IRecipeExtrasBuilder builder,
        RecipeHolder<HybridRecipe> holder
    ) {
        List<SlotPacket> slotPackets = this.createSlotPackets(
            holder.value(),
            builder.getRecipeSlots().getSlots()
        );

        HybridDisplayToggleWidget toggle = new HybridDisplayToggleWidget(
            2, 54,
            this.guiHelper.createDrawableItemLike(ModItems.KNOWN_SEED),
            this.guiHelper.createDrawableItemLike(ModItems.SEED_PACKET),
            () -> this.getDisplayMode(holder) == HybridDisplayMode.SEED_PACKET,
            () -> this.toggleDisplayMode(holder, slotPackets)
        );

        builder.addWidget(toggle);
        builder.addInputHandler(toggle);
    }

    @Override
    public void onDisplayedIngredientsUpdate(
        RecipeHolder<HybridRecipe> holder,
        List<IRecipeSlotDrawable> recipeSlots,
        IFocusGroup focuses
    ) {
        HybridDisplayMode mode = this.getDisplayMode(holder);
        if (mode == HybridDisplayMode.SEED) {
            return;
        }

        this.applyDisplayMode(this.createSlotPackets(holder.value(), recipeSlots), mode);
    }

    private void toggleDisplayMode(
        RecipeHolder<HybridRecipe> holder,
        List<SlotPacket> slotPackets
    ) {
        HybridDisplayMode nextMode = this.getDisplayMode(holder).toggle();
        this.displayModes.put(holder.id().identifier(), nextMode);
        this.applyDisplayMode(slotPackets, nextMode);
    }

    private HybridDisplayMode getDisplayMode(RecipeHolder<HybridRecipe> holder) {
        return this.displayModes.getOrDefault(holder.id().identifier(), HybridDisplayMode.SEED);
    }

    private void applyDisplayMode(
        List<SlotPacket> slotPackets,
        HybridDisplayMode mode
    ) {
        for (SlotPacket slotPacket : slotPackets) {
            slotPacket.slot().clearDisplayOverrides();
            if (mode == HybridDisplayMode.SEED_PACKET) {
                slotPacket.slot()
                    .createDisplayOverrides()
                    .add(SeedUtil.getSeedPacket(slotPacket.seed()));
            }
        }
    }

    private List<SlotPacket> createSlotPackets(
        HybridRecipe recipe,
        List<IRecipeSlotDrawable> recipeSlots
    ) {
        List<SlotPacket> slotPackets = new ArrayList<>();
        List<Holder<Item>> inputSeeds = recipe.getInputSeeds();

        if (!inputSeeds.isEmpty()) {
            this.addSlotPacket(slotPackets, recipeSlots, INPUT_FIRST_SLOT, inputSeeds.getFirst());
            this.addSlotPacket(slotPackets, recipeSlots, INPUT_LAST_SLOT, inputSeeds.getLast());
        }

        List<Holder<Item>> outputSeeds = recipe.getOutputSeeds();
        for (int i = 0; i < outputSeeds.size(); i++) {
            this.addSlotPacket(slotPackets, recipeSlots, OUTPUT_SLOT_PREFIX + i, outputSeeds.get(i));
        }

        return slotPackets;
    }

    private void addSlotPacket(
        List<SlotPacket> slotPackets,
        List<IRecipeSlotDrawable> recipeSlots,
        String slotName,
        Holder<Item> seed
    ) {
        this.findSlot(recipeSlots, slotName)
            .ifPresent(
                slot -> slotPackets.add(
                    new SlotPacket(
                        slot,
                        seed
                    )
                )
            );
    }

    private Optional<IRecipeSlotDrawable> findSlot(
        List<IRecipeSlotDrawable> recipeSlots,
        String slotName
    ) {
        for (IRecipeSlotDrawable slot : recipeSlots) {
            if (slot.getSlotName().filter(slotName::equals).isPresent()) {
                return Optional.of(slot);
            }
        }
        return Optional.empty();
    }

    private enum HybridDisplayMode {
        SEED,
        SEED_PACKET;

        private HybridDisplayMode toggle() {
            return this == SEED ? SEED_PACKET : SEED;
        }
    }

    private record SlotPacket(IRecipeSlotDrawable slot, Holder<Item> seed) {
    }
}
