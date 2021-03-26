package lq2007.mcmod.isaacmod.client.ister;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PropItemRender extends ItemStackTileEntityRenderer {

    private int degree = 0;

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType type, MatrixStack matrix,
                               IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (degree == 360) {
            degree = 0;
        }
        degree++;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, null, null);
        matrix.push();
        matrix.translate(0.5F, 0.5F, 0.5F);
        float xOffset = -1 / 32f;
        float zOffset = 0;
        matrix.translate(-xOffset, 0, -zOffset);
        matrix.rotate(Vector3f.YP.rotationDegrees(degree));
        matrix.translate(xOffset, 0, zOffset);
        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.NONE, false, matrix, buffer, combinedLight, combinedOverlay, ibakedmodel.getBakedModel());
        matrix.pop();
    }
}
