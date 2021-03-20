package lq2007.mcmod.isaacmod.common.prop;

import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacmod.common.prop.type.EmptyProp;
import lq2007.mcmod.isaacmod.common.prop.type.PropRegister;
import lq2007.mcmod.isaacmod.common.util.serializer.ISerializer;
import lq2007.mcmod.isaacmod.common.util.serializer.Serializer;
import lq2007.mcmod.isaacmod.common.util.serializer.Serializers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.CapabilityProvider;

import java.util.Objects;

@Serializer(Prop.Serializer.class)
public class Prop extends CapabilityProvider<Prop> {

    public static final Prop EMPTY = new Prop(EmptyProp.EMPTY);

    public AbstractPropType type;

    public Prop(AbstractPropType type) {
        super(Prop.class);
        gatherCapabilities(type.initCapabilities());
    }

    @OnlyIn(Dist.CLIENT)
    public void renderOnFoundation(float partialTicks, com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                   net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                   int combinedLightIn, int combinedOverlayIn) {
        type.renderOnFoundation(this, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
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

    public static class Serializer implements ISerializer<Prop> {

        @Override
        public Prop read(PacketBuffer buffer) {
            AbstractPropType type = Serializers.getPacketReader(AbstractPropType.class, false).read(buffer);
            if (type != EmptyProp.EMPTY) {
                Prop prop = new Prop(type);
                CompoundNBT cap = buffer.readCompoundTag();
                if (cap != null) {
                    prop.deserializeCaps(cap);
                }
            }
            return Prop.EMPTY;
        }

        @Override
        public PacketBuffer write(Prop item, PacketBuffer buffer) {
            Serializers.getPacketWriter(AbstractPropType.class, false).write(item.type, buffer);
            if (item != EMPTY) {
                CompoundNBT cap = item.serializeCaps();
                buffer.writeCompoundTag(cap);
            }
            return buffer;
        }

        @Override
        public Prop read(CompoundNBT nbt, String key) {
            CompoundNBT data = nbt.getCompound(key);
            AbstractPropType type = Serializers.getNBTReader(AbstractPropType.class).read(data, "type");
            if (type != EmptyProp.EMPTY) {
                Prop prop = new Prop(type);
                prop.deserializeCaps(nbt.getCompound("data"));
                return prop;
            }
            return Prop.EMPTY;
        }

        @Override
        public CompoundNBT write(CompoundNBT nbt, String key, Prop item) {
            CompoundNBT data = new CompoundNBT();
            if (item != Prop.EMPTY) {
                Serializers.getNBTWriter(AbstractPropType.class).write(data, "type", item.type);
                CompoundNBT caps = item.serializeCaps();
                if (caps != null) {
                    data.put("data", caps);
                }
                nbt.put(key, data);
            }
            return nbt;
        }
    }
}
