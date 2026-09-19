package me.theabab2333.harvestheritage.common.item;

import me.theabab2333.harvestheritage.api.item.IHasTooltips;
import me.theabab2333.harvestheritage.common.component.SeedComponent;
import me.theabab2333.harvestheritage.init.ModItems;
import me.theabab2333.harvestheritage.init.ModRecipes;
import me.theabab2333.harvestheritage.init.ModSeeds;
import me.theabab2333.harvestheritage.common.recipe.FindRecipe;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MagnifyingGlassItem extends Item implements IHasTooltips {
    public MagnifyingGlassItem(Properties properties) {
        properties.stacksTo(1);
        properties.component(
            DataComponents.EQUIPPABLE,
            Equippable.builder(ArmorType.HELMET.getSlot()).build()
        );
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            Vec3 eyePos = player.getEyePosition();
            Vec3 lookVec = player.getViewVector(1.0f);
            double reachDistance = 5.0;

            Vec3 endPos = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);

            AABB searchBox = new AABB(eyePos, endPos).inflate(1.0);

            List<Entity> entities = level.getEntities(player, searchBox, entity -> entity instanceof ItemEntity);

            for (Entity entity : entities) {
                if (entity instanceof ItemEntity itemEntity) {
                    if (level instanceof ServerLevel serverLevel) {
                        RecipeManager manager = serverLevel.recipeAccess();
                        var holders = manager.recipeMap().byType(ModRecipes.FIND_TYPE.get());
                        List<FindRecipe> matched = new ArrayList<>();
                        for (var holder : holders) {
                            FindRecipe recipe = holder.value();
                            Item item = itemEntity.getItem().getItem();
                            if (recipe.getIngredient().test(item.getDefaultInstance())) {
                                matched.add(recipe);
                            }
                        }
                        if (!matched.isEmpty()) {
                            var random = level.getRandom();
                            FindRecipe recipe = matched.get(random.nextInt(matched.size()));
                            List<ItemStackTemplate> list = recipe.getResult();
                            if (!list.isEmpty()) {
                                ItemStack itemStack = itemEntity.getItem().copy();
                                itemStack.shrink(1);
                                itemEntity.setItem(itemStack);
                                var result = list.get(random.nextInt(list.size())).create();
                                Containers.dropItemStack(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
                            }
                        } else {
                            // fallback for custom seeds from datapack seed_definitions
                            Item itemInEntity = itemEntity.getItem().getItem();
                            if (ModSeeds.ALL_SEED.containsKey(itemInEntity)) {
                                SeedComponent seedComponent = SeedUtil.getSeedComponent(itemInEntity);
                                DataComponentPatch patch = SeedUtil.createSeedComponentPatch(seedComponent);
                                ItemStack knownSeed = new ItemStack(ModItems.KNOWN_SEED, 1, patch);
                                ItemStack stack = itemEntity.getItem().copy();
                                stack.shrink(1);
                                itemEntity.setItem(stack);
                                Containers.dropItemStack(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), knownSeed);
                            }
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack) {
        return List.of(Component.translatable("item.harvestheritage.magnifying_glass.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
