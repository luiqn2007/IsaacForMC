package lq2007.mcmod.isaacmod.client.ter;

import com.mojang.blaze3d.matrix.MatrixStack;
import lq2007.mcmod.isaacmod.common.block.TileFoundation;
import lq2007.mcmod.isaacmod.common.prop.Prop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TerFoundation extends TileEntityRenderer<TileFoundation> {

    public TerFoundation(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileFoundation tileFoundation, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        if (tileFoundation.hasProp()) {
            Prop prop = tileFoundation.getProp();
            prop.type.render(prop, 1, matrixStackIn, bufferIn);
        }
        matrixStackIn.pop();
    }
}
