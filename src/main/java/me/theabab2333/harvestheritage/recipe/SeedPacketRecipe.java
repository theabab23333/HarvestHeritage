package me.theabab2333.harvestheritage.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import me.theabab2333.harvestheritage.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModDataComponents;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModRecipes;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.NormalCraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.TransmuteRecipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

@Getter
public class SeedPacketRecipe extends NormalCraftingRecipe {

    public static final MapCodec<SeedPacketRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(
        Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
        CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
        Ingredient.CODEC.fieldOf("known_seed").forGetter(SeedPacketRecipe::getKnownSeed),
        Ingredient.CODEC.fieldOf("accept_paper").forGetter(SeedPacketRecipe::getAcceptPaper),
        ItemStackTemplate.CODEC.fieldOf("result").forGetter(SeedPacketRecipe::getResult)
    ).apply(i, SeedPacketRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SeedPacketRecipe> STREAM_CODEC = StreamCodec.composite(
        Recipe.CommonInfo.STREAM_CODEC,
        o -> o.commonInfo,
        CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
        o -> o.bookInfo,
        Ingredient.CONTENTS_STREAM_CODEC,
        SeedPacketRecipe::getKnownSeed,
        Ingredient.CONTENTS_STREAM_CODEC,
        SeedPacketRecipe::getAcceptPaper,
        ItemStackTemplate.STREAM_CODEC,
        SeedPacketRecipe::getResult,
        SeedPacketRecipe::new
    );

    @Getter
    private final Ingredient knownSeed;
    @Getter
    private final Ingredient acceptPaper;
    @Getter
    private final ItemStackTemplate result;

    public SeedPacketRecipe(
        Recipe.CommonInfo commonInfo,
        CraftingRecipe.CraftingBookInfo bookInfo,
        Ingredient knownSeed,
        Ingredient acceptPaper,
        ItemStackTemplate result
    ) {
        super(commonInfo, bookInfo);
        this.knownSeed = knownSeed;
        this.acceptPaper = acceptPaper;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2) {
            return false;
        }
        SeedComponent target = this.result.get(ModDataComponents.SEED_COMPONENT.get());
        if (target == null) return false;

        boolean hasPaper = false;
        boolean hasSeed = false;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack itemStack = input.getItem(slot);
            if (!itemStack.isEmpty()) {
                if (this.acceptPaper.test(itemStack)) {
                    hasPaper = true;
                } else if (this.knownSeed.test(itemStack)
                           && itemStack.has(ModDataComponents.SEED_COMPONENT.get())) {
                    SeedComponent seedComponent = itemStack.get(ModDataComponents.SEED_COMPONENT.get());
                    if (seedComponent != null && seedComponent.seed().value().equals(target.getSeed())) {
                        hasSeed = true;
                    }
                } else {
                    return false;
                }
            }
        }

        return hasPaper && hasSeed;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack itemStack = input.getItem(slot);
            if (!itemStack.isEmpty()) {
                if (this.knownSeed.test(itemStack)) {
                    return TransmuteRecipe.createWithOriginalComponents(this.result, itemStack);
                }
            }
        }

        return this.result.create();
    }

    @Override
    public List<RecipeDisplay> display() {
        DataComponentPatch patch = this.result.components();

        return List.of(new ShapelessCraftingRecipeDisplay(
            List.of(
                new SlotDisplay.ItemStackSlotDisplay(new ItemStackTemplate(ModItems.KNOWN_SEED, patch)),
                this.acceptPaper.display()
            ),
            new SlotDisplay.ItemStackSlotDisplay(this.result),
            new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
        ));
    }

    @Override
    public RecipeSerializer<SeedPacketRecipe> getSerializer() {
        return ModRecipes.SEED_PACKET_SERIALIZERS.get();
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.create(List.of(knownSeed, acceptPaper));
    }
}
