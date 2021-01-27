package lq2007.mcmod.isaacformc.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import lq2007.mcmod.isaacformc.client.model.ModelBobby;
import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.IsaacResourceManager;
import lq2007.mcmod.isaacformc.common.entity.EntityBobby;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;

public class RenderBobby extends EntityRenderer<EntityBobby> {

    private final ModelBobby model = new ModelBobby();

    protected RenderBobby(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityBobby entity) {
        return IsaacResourceManager.getEntityTexture(EntityBobby.NAME, EntityBobby.TYPE);
    }

    @Override
    public void render(EntityBobby entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        OBJModel model = OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(kc, false, false, false, false, false));
    }
}
