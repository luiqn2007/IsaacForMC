package lq2007.mcmod.isaacmod.client.item;

import com.mojang.datafixers.util.Pair;
import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.common.prop.Prop;
import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacmod.common.prop.type.EmptyProp;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class PropModel implements IModelGeometry<PropModel> {

    public static final ResourceLocation LOADER_ID = new ResourceLocation(Isaac.ID, "prop");

    public static final Map<AbstractPropType, PropModel> MODELS = new HashMap<>();

    public static PropModel getOrCreate(AbstractPropType type) {
        return MODELS.computeIfAbsent(type, PropModel::new);
    }

    public static PropModel getOrCreate(int type) {
        return getOrCreate(Isaac.PROPS.get(type, EmptyProp.EMPTY));
    }

    public static PropModel getOrCreate(String type) {
        return getOrCreate(Isaac.PROPS.get(new ResourceLocation(type), EmptyProp.EMPTY));
    }

    // minimal Z offset to prevent depth-fighting
    private static final float NORTH_Z_COVER = 7.496f / 16f;
    private static final float SOUTH_Z_COVER = 8.504f / 16f;
    private static final float NORTH_Z_FLUID = 7.498f / 16f;
    private static final float SOUTH_Z_FLUID = 8.502f / 16f;

    private final AbstractPropType type;

    public PropModel(AbstractPropType type) {
        this.type = type;
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        return null;
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        return null;
    }
}
