package lq2007.mcmod.isaacformc.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lq2007.mcmod.isaacformc.client.util.obj.OBJModel;
import lq2007.mcmod.isaacformc.common.IsaacResourceManager;
import lq2007.mcmod.isaacformc.common.entity.EnumEntityType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

public class BaseObjModel<T extends Entity> extends EntityModel<T> {

    public final OBJModel model;
    public final ResourceLocation texture;

    public BaseObjModel(String name, EnumEntityType type) {
        textureWidth = 512;
        textureHeight = 512;
        model = OBJModel.read(IsaacResourceManager.getEntityModel(name, type));
        texture = IsaacResourceManager.getEntityTexture(name, type);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        bufferIn.color(red, green, blue, alpha);
        matrixStackIn.push();
        matrixStackIn.rotate(new Quaternion(0, model.entityIn.rotationYaw, 0, false));
        model.renderAll(texture);
        matrixStackIn.pop();
    }
}
