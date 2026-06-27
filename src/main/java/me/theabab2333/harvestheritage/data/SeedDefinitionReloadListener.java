package me.theabab2333.harvestheritage.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModSeeds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeedDefinitionReloadListener extends SimplePreparableReloadListener<Map<Identifier, JsonElement>> {

    private static final String DIRECTORY = "harvestheritage_seeds";

    @Override
    protected Map<Identifier, JsonElement> prepare(ResourceManager manager, ProfilerFiller profiler) {
        Map<Identifier, JsonElement> map = new HashMap<>();
        FileToIdConverter converter = FileToIdConverter.json(DIRECTORY);

        for (var entry : converter.listMatchingResources(manager).entrySet()) {
            Identifier location = entry.getKey();
            Identifier id = converter.fileToId(location);

            try (Reader reader = entry.getValue().openAsReader()) {
                JsonElement json = JsonParser.parseReader(reader);
                if (json.isJsonObject()) {
                    map.put(id, json);
                } else {
                    HarvestHeritage.LOGGER.warn("Invalid seed definition (not a JSON object) in: {}", location);
                }
            } catch (Exception e) {
                HarvestHeritage.LOGGER.warn("Failed to read seed definition {}: {}", location, e.getMessage());
            }
        }

        return map;
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> entries, ResourceManager manager, ProfilerFiller profiler) {
        if (entries.isEmpty()) return;

        int count = 0;
        for (var entry : entries.entrySet()) {
            Identifier id = entry.getKey();

            try {
                JsonObject obj = entry.getValue().getAsJsonObject();

                // seed item
                String seedStr = obj.get("seed").getAsString();
                Identifier seedId = Identifier.parse(seedStr);
                Item seedItem = BuiltInRegistries.ITEM.get(seedId).map(Holder::value).orElse(Items.AIR);
                if (seedItem == Items.AIR) {
                    HarvestHeritage.LOGGER.warn("Unknown seed item '{}' in seed definition: {}", seedStr, id);
                    continue;
                }

                // category
                String category = obj.get("category").getAsString();
                if (!isValidCategory(category)) {
                    HarvestHeritage.LOGGER.warn("Invalid category '{}' in seed definition: {}", category, id);
                    category = "misc";
                }

                // results
                List<Item> results = new ArrayList<>();
                for (var resultElem : obj.get("results").getAsJsonArray()) {
                    String resultStr = resultElem.getAsString();
                    Identifier resultId = Identifier.parse(resultStr);
                    Item resultItem = BuiltInRegistries.ITEM.get(resultId).map(Holder::value).orElse(Items.AIR);
                    if (resultItem == Items.AIR) {
                        HarvestHeritage.LOGGER.warn("Unknown result item '{}' in seed definition: {}", resultStr, id);
                        continue;
                    }
                    results.add(resultItem);
                }

                if (results.isEmpty()) {
                    HarvestHeritage.LOGGER.warn("No valid results in seed definition: {}", id);
                    continue;
                }

                // stage
                int stage = obj.get("stage").getAsInt();
                if (stage < 1) {
                    HarvestHeritage.LOGGER.warn("Stage must be >= 1 in seed definition: {}", id);
                    continue;
                }

                // need_block
                Block needBlock = Blocks.AIR;
                if (obj.has("need_block") && !obj.get("need_block").getAsString().isEmpty()) {
                    String needBlockStr = obj.get("need_block").getAsString();
                    Identifier needBlockId = Identifier.parse(needBlockStr);
                    needBlock = BuiltInRegistries.BLOCK.get(needBlockId).map(Holder::value).orElse(Blocks.AIR);
                }

                ModSeeds.SeedInfo info = new ModSeeds.SeedInfo(results, stage, needBlock);
                ModSeeds.registerSeed(seedItem, info, category);
                count++;

            } catch (Exception e) {
                HarvestHeritage.LOGGER.warn("Failed to parse seed definition {}: {}", id, e.getMessage());
            }
        }

        if (count > 0) {
            HarvestHeritage.LOGGER.info("Loaded {} custom seed definitions from datapacks", count);
        }
    }

    private static boolean isValidCategory(String category) {
        return switch (category) {
            case "crop", "animal", "mob", "material", "special", "misc" -> true;
            default -> false;
        };
    }
}
