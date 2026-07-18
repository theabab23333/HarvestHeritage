package me.theabab2333.harvestheritage.client.render.item;

import com.google.common.base.Suppliers;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import me.theabab2333.harvestheritage.api.item.ISeedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static net.minecraft.client.renderer.item.CuboidItemModelWrapper.computeExtents;

// AI-generated 我不会渲染，有没有人写写
// 现在能用但是 seedItem 如果是 BlockItem 的话会出现部分情况不正常渲染的问题，其他问题可能还有我也不知道（）（）
public record SeedPacketItemModel(
    QuadCollection quads, List<ItemTintSource> tints, ModelRenderProperties properties, Matrix4fc matrix4fc, Supplier<Vector3fc[]> extents
) implements ItemModel {

    @Override
    public void update(
        ItemStackRenderState renderState,
        ItemStack stack,
        ItemModelResolver resolver,
        ItemDisplayContext context,
        @Nullable ClientLevel level,
        @Nullable ItemOwner owner,
        int i
    ) {
        if (!(stack.getItem() instanceof ISeedItem seedItem)) {
            return;
        }

        renderState.appendModelIdentityElement(this);

        ItemStackRenderState.LayerRenderState layer = renderState.newLayer();
        IntList intList = layer.tintLayers();
        intList.add(-1);
        layer.setExtents(extents);
        properties.applyToLayer(layer, context);
        layer.prepareQuadList().addAll(quads.getAll());
        renderState.appendModelIdentityElement(intList.getInt(0));

        Holder<Item> holder = seedItem.seed(stack);
        if (holder.value() == Items.AIR) return;

        if (context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            ItemStackRenderState.LayerRenderState seedLayer = renderState.newLayer();
            IntList seedInts = seedLayer.tintLayers();
            seedInts.add(-1);
            seedLayer.setExtents(extents);
            properties.applyToLayer(seedLayer, context);
            seedLayer.setLocalTransform(new Matrix4f().translate(0.0f, 0.0f, 0.05f));
            try {
                var baseQuads = quads.getAll();
                if (!baseQuads.isEmpty()) {
                    var baseQuad = baseQuads.get(0);
                    addSeedQuad(seedLayer, holder.value(), baseQuad.direction(), baseQuad.materialInfo());
                }
            } catch (Exception ignored) {
            }
            renderState.appendModelIdentityElement(seedInts.getInt(0));
        } else {
            resolver.appendItemLayers(renderState, new ItemStack(holder), context, level, owner, i);
        }
    }

    private static void addSeedQuad(
        ItemStackRenderState.LayerRenderState layer, Item seedItem,
        Direction direction, BakedQuad.MaterialInfo baseMat
    ) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(seedItem);
        Identifier textureId = itemId.withPath("item/" + itemId.getPath());

        AtlasManager atlasManager = Minecraft.getInstance().getAtlasManager();
        TextureAtlasSprite sprite = atlasManager.get(new SpriteId(TextureAtlas.LOCATION_BLOCKS, textureId));

        if (sprite.contents().name().equals(MissingTextureAtlasSprite.getLocation())) {
            sprite = atlasManager.get(new SpriteId(TextureAtlas.LOCATION_ITEMS, textureId));
            if (sprite.contents().name().equals(MissingTextureAtlasSprite.getLocation())) {
                return;
            }
        }

        float margin = (1.0f - 12.0f / 16.0f) / 2.0f;
        Vector3f p0 = new Vector3f(margin, margin, 0.55f);
        Vector3f p1 = new Vector3f(1.0f - margin, margin, 0.6f);
        Vector3f p2 = new Vector3f(1.0f - margin, 1.0f - margin, 0.6f);
        Vector3f p3 = new Vector3f(margin, 1.0f - margin, 0.6f);

        long uv0 = packUV(sprite.getU(0.0f), sprite.getV(0.0f));
        long uv1 = packUV(sprite.getU(1.0f), sprite.getV(0.0f));
        long uv2 = packUV(sprite.getU(1.0f), sprite.getV(1.0f));
        long uv3 = packUV(sprite.getU(0.0f), sprite.getV(1.0f));

        BakedQuad.MaterialInfo materialInfo = new BakedQuad.MaterialInfo(
            sprite,
            baseMat.layer(),
            baseMat.itemRenderType(),
            -1,
            baseMat.shade(),
            baseMat.lightEmission(),
            baseMat.ambientOcclusion()
        );

        BakedQuad seedQuad = new BakedQuad(
            p0, p1, p2, p3,
            uv3, uv2, uv1, uv0,
            direction,
            materialInfo
        );

        layer.prepareQuadList().add(seedQuad);
    }

    private static long packUV(float u, float v) {
        return (long) Float.floatToRawIntBits(u) << 32 | (Float.floatToRawIntBits(v) & 0xFFFFFFFFL);
    }

    public record Unbaked(Identifier model, List<ItemTintSource> tints, Optional<Transformation> transformation)
        implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Identifier.CODEC.fieldOf("model").forGetter(Unbaked::model),
            ItemTintSources.CODEC.listOf().optionalFieldOf("tints", List.of()).forGetter(Unbaked::tints),
            Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").forGetter(Unbaked::transformation)
        ).apply(inst, Unbaked::new));

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc matrix4fc) {
            ModelBaker baker = context.blockModelBaker();
            ResolvedModel resolvedModel = baker.getModel(this.model);
            TextureSlots slots = resolvedModel.getTopTextureSlots();
            List<BakedQuad> baseModelQuads = resolvedModel.bakeTopGeometry(slots, baker, BlockModelRotation.IDENTITY).getAll();
            Supplier<Vector3fc[]> extents = Suppliers.memoize(() -> computeExtents(baseModelQuads));

            return new SeedPacketItemModel(
                resolvedModel.bakeTopGeometry(slots, baker, BlockModelRotation.IDENTITY),
                this.tints,
                ModelRenderProperties.fromResolvedModel(baker, resolvedModel, slots),
                Transformation.compose(matrix4fc, this.transformation),
                extents
            );
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(model);
        }
    }
}
