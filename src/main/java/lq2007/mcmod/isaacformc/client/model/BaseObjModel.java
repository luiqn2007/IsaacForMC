package lq2007.mcmod.isaacformc.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lq2007.mcmod.isaacformc.common.IsaacResourceManager;
import lq2007.mcmod.isaacformc.common.entity.EnumEntityType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;

import javax.annotation.Nullable;

public class BaseObjModel<T extends Entity> extends EntityModel<T> {

    private final OBJModel model;

    public BaseObjModel(String modelName, EnumEntityType type) {
        textureWidth = 512;
        textureHeight = 512;
        model = OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(
                IsaacResourceManager.getEntityModel(modelName, type),
                false, false, false, false, null
        ));
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        for (IModelGeometryPart part : model.getParts()) {
            OBJModel.ModelGroup group = (OBJModel.ModelGroup) part;

        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

    }
}
