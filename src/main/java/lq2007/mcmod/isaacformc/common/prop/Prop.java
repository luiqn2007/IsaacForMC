package lq2007.mcmod.isaacformc.common.prop;

import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.Props;
import lq2007.mcmod.isaacformc.common.util.serializer.PropSerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Objects;

@Serializer(PropSerializer.class)
public class Prop extends CapabilityProvider<Prop> implements INBTSerializable<CompoundNBT> {

    public static final Prop EMPTY = new Prop(Props.EMPTY);

    public AbstractPropType type;

    public Prop(AbstractPropType type) {
        super(Prop.class);
        ICapabilityProvider capabilities = type.initCapabilities();
        gatherCapabilities(capabilities);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderOnFoundation(float partialTicks, com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                   net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                   int combinedLightIn, int combinedOverlayIn) {
        type.renderOnFoundation(this, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("_type", type.key.toString());
        CompoundNBT caps = serializeCaps();
        if (caps != null) {
            nbt.put("_caps", caps);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("_caps", Constants.NBT.TAG_COMPOUND)) {
            deserializeCaps(nbt.getCompound("_caps"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prop prop = (Prop) o;
        return this.type.equals(prop.type) && areCapsCompatible(prop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, getCapabilities());
    }
}
