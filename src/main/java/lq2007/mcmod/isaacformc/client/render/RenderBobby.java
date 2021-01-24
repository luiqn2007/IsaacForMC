package lq2007.mcmod.isaacformc.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import lq2007.mcmod.isaacformc.client.model.ModelBobby;
import lq2007.mcmod.isaacformc.common.entity.EntityBobby;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderBobby extends EntityRenderer<EntityBobby> {

    private static final ResourceLocation TEXTURE = new ResourceLocation();
    private final ModelBobby model = new ModelBobby();

    protected RenderBobby(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityBobby entity) {
        return TEXTURE;
    }

    @Override
    public void render(EntityBobby entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
