package lq2007.mcmod.isaacmod.client.ister;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ISTERItemModel implements IBakedModel {

    private final IBakedModel model;
    private final boolean allowThird;

    public ISTERItemModel(IBakedModel model, boolean allowThird) {
        this.model = model;
        this.allowThird = allowThird;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return model.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return model.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return model.isGui3d();
    }

    @Override
    public boolean isSideLit() {
        return model.isSideLit();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return model.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return model.getOverrides();
    }

    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        return (allowThird || cameraTransformType.isFirstPerson()) ? this : model.handlePerspective(cameraTransformType, mat);
    }
}
