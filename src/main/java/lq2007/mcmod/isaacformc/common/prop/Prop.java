package lq2007.mcmod.isaacformc.common.prop;

import lq2007.mcmod.isaacformc.common.network.IPacketWriteable;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacformc.common.prop.type.Props;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.Objects;

public class Prop extends CapabilityProvider<Prop> implements INBTSerializable<CompoundNBT>, IPacketWriteable {

    public static final Prop EMPTY = new Prop(Props.EMPTY);

    public AbstractPropType type;

    public static Prop fromNbt(@Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains("_prop", Constants.NBT.TAG_STRING)) {
            AbstractPropType type = Props.get(new ResourceLocation(nbt.getString("_prop")), null);
            if (type != null) {
                Prop item = new Prop(type);
                item.deserializeNBT(nbt);
                return item;
            }
        }
        return Prop.EMPTY;
    }

    public static Prop fromPacket(PacketBuffer buffer) {
        AbstractPropType type = Props.get(buffer.readResourceLocation(), null);
        if (type != null) {
            return type.read(new Prop(), buffer);
        }
        return Prop.EMPTY;
    }

    public Prop(AbstractPropType type) {
        this();
        ICapabilityProvider capabilities = type.initCapabilities();
        gatherCapabilities(capabilities);
    }

    private Prop() {
        super(Prop.class);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderOnFoundation(float partialTicks, com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                   net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                   int combinedLightIn, int combinedOverlayIn) {
        type.renderOnFoundation(this, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeResourceLocation(type.key);
        type.write(this, buffer);
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
