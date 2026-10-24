package me.theabab2333.harvestheritage.mixin.client;

import me.theabab2333.harvestheritage.api.item.ISeedItem;
import me.theabab2333.harvestheritage.util.SeedUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    /**
     * 去死吧 SeedPacketItemModel! 我用迷信了哈哈!
     * 之前那套 SeedPacketItemModel 是 AI 写的，很屎，现在换成这样子看起来似乎好多了
     * 即便只在 Gui 里面...
     */
    @Inject(
        method = "item("
                 + "Lnet/minecraft/world/entity/LivingEntity;"
                 + "Lnet/minecraft/world/level/Level;"
                 + "Lnet/minecraft/world/item/ItemStack;III"
                 + ")V",
        at = @At(value = "TAIL")
    )
    protected void renderGuiItem(
        LivingEntity owner,
        Level level,
        ItemStack itemStack,
        int x,
        int y,
        int seed,
        CallbackInfo ci
    ) {
        if (itemStack.getItem() instanceof ISeedItem seedItem) {
            var holder = seedItem.seed(itemStack);
            var seedInfo = SeedUtil.getSeedInfo(holder);

            var guiGraphicsExtractor = (GuiGraphicsExtractor) (Object) this;

            if (seedInfo != null) {
                guiGraphicsExtractor.item(
                    owner,
                    level,
                    new ItemStack(seedInfo.seed().value()),
                    x,
                    y,
                    seed
                );
            }
//            } else {
//                guiGraphicsExtractor.item(
//                    owner,
//                    level,
//                    new ItemStack(Items.BARRIER),
//                    x,
//                    y,
//                    seed
//                );
//            }
        }
    }
}
