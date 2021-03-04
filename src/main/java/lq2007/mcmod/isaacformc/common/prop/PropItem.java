package lq2007.mcmod.isaacformc.common.prop;

import lq2007.mcmod.isaacformc.common.network.IPacketWriter;
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

public class PropItem extends CapabilityProvider<PropItem> implements INBTSerializable<CompoundNBT>, IPacketWriter {

    public static final PropItem EMPTY = new PropItem(Props.EMPTY);

    public AbstractPropType prop;

    public static PropItem fromNbt(@Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains("_prop", Constants.NBT.TAG_STRING)) {
            AbstractPropType type = Props.get(new ResourceLocation(nbt.getString("_prop")), null);
            if (type != null) {
                PropItem item = new PropItem(type);
                item.deserializeNBT(nbt.getCompound("_data"));
                return item;
            }
        }
        return PropItem.EMPTY;
    }

    public static PropItem fromPacket(PacketBuffer buffer) {
        AbstractPropType type = Props.get(buffer.readResourceLocation(), null);
        if (type != null) {
            return new PropItem(type, buffer);
        }
        return PropItem.EMPTY;
    }

    protected PropItem(AbstractPropType prop) {
        this();
        ICapabilityProvider capabilities = prop.initCapabilities();
        gatherCapabilities(capabilities);
    }

    protected PropItem() {
        super(PropItem.class);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderOnFoundation(float partialTicks, com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                   net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                   int combinedLightIn, int combinedOverlayIn) {
        prop.renderOnFoundation(this, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("_type", prop.key.toString());
        nbt.put("_data", data.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        data.deserializeNBT(nbt.getCompound("_data"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropItem propItem = (PropItem) o;
        return prop.equals(propItem.prop) && data.equals(propItem.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prop, data);
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeResourceLocation(prop.key);
        data.write(buffer);
    }
}
