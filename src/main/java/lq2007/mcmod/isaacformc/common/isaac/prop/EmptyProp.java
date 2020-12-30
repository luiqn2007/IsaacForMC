package lq2007.mcmod.isaacformc.common.isaac.prop;

import lq2007.mcmod.isaacformc.common.block.TileFoundation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class EmptyProp implements IProp {

    public static final EmptyProp EMPTY = new EmptyProp();
    private static final ResourceLocation KEY = new ResourceLocation("isaac", "empty");

    private EmptyProp() { }

    @Override
    public String getNameKey() {
        return "";
    }

    @Override
    public String getDescriptionKey() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public ResourceLocation getKey() {
        return KEY;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderOnFoundation(TileFoundation tile, float partialTicks, com.mojang.blaze3d.matrix.MatrixStack matrix,
                                   net.minecraft.client.renderer.IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) { }
}
