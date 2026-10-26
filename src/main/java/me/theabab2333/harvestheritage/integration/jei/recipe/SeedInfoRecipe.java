package me.theabab2333.harvestheritage.integration.jei.recipe;

import me.theabab2333.harvestheritage.common.seed.SeedInfo;
import me.theabab2333.harvestheritage.common.seed.requirement.SeedBlockRequirement;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record SeedInfoRecipe(ItemStack seedPacket, SeedInfo seedInfo) {
    public List<Holder<Item>> getResults() {
        return this.seedInfo
            .results()
            .stream()
            .toList();
    }

    public List<SeedBlockRequirement> getBlockRequirements() {
        return this.seedInfo.needBlocks();
    }
}
