package lq2007.mcmod.isaacformc.common.prop.type;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.util.serializer.PropTypeSerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializer;
import lq2007.mcmod.isaacformc.common.util.serializer.network.IPacketSerializer;
import lq2007.mcmod.isaacformc.isaac.IsaacElement;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;
import java.util.List;

@Serializer(PropTypeSerializer.class)
public abstract class AbstractPropType extends IsaacElement implements IPacketSerializer<Prop> {

    private final String nameKey;
    private final String descriptionKey;
    private final int charge;
    private final List<EnumPropPools> rooms;

    public AbstractPropType(ResourceLocation key, EnumPropPools... rooms) {
        this(key, -1, 0, rooms);
    }

    public AbstractPropType(String name, int id, EnumPropPools... rooms) {
        this(new ResourceLocation(Isaac.ID, name), id, 0, rooms);
    }

    public AbstractPropType(ResourceLocation key, int charge, EnumPropPools... rooms) {
        this(key, -1, charge, rooms);
    }

    public AbstractPropType(String name, int id, int charge, EnumPropPools... rooms) {
        this(new ResourceLocation(Isaac.ID, name), id, charge, rooms);
    }

    protected AbstractPropType(ResourceLocation key, int id, int charge, EnumPropPools... rooms) {
        super(key, id);
        Props.register(this);
        this.rooms = Lists.newArrayList(rooms);
        this.charge = charge;
        this.nameKey = key.getNamespace() + ".prop." + key.getPath() + ".name";
        this.descriptionKey = key.getNamespace() + ".prop." + key.getPath() + ".desc";
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent(getNameKey());
    }

    public String getNameKey() {
        return nameKey;
    }

    @Override
    public ITextComponent getDescription() {
        return new TranslationTextComponent(getDescriptionKey());
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public List<EnumPropPools> spawnRoom() {
        return rooms;
    }

    public void onActiveStart(LivingEntity entity, Prop prop) {
    }

    public void onActiveFinished(LivingEntity entity, Prop prop) {
    }

    public final void onPickup(LivingEntity entity, Prop item) {
        if (!entity.world.isRemote) {
//            PickupPropItemEvent event = new PickupPropItemEvent(entity, item);
//            if (!Isaac.MOD.eventBus.post(event)) {
//                onPickup(entity, event.getProp(), item);
//            }
        }
    }

    public void onPickup(LivingEntity entity, Prop item, Prop itemBeforeEvent) {
//        IsaacCapabilities.getPropData(entity).pickupProp(item);
    }

    public void onRemove(LivingEntity entity, Prop item, ImmutableList<Prop> removedItems) {
//        IsaacCapabilities.getPropData(entity).removeProp(item);
    }

    public boolean isActive() {
        return charge > 0;
    }

    public boolean isExclusive() {
        return isActive();
    }

    @Nullable
    public ICapabilityProvider initCapabilities() {
        return null;
    }

    // todo implement render
    @OnlyIn(Dist.CLIENT)
    public void renderOnFoundation(Prop item, float partialTicks,
                                   com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                   net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                   int combinedLightIn, int combinedOverlayIn) {
    }

    public EnumPropTags[] getTags() {
        PropTag tag = getClass().getAnnotation(PropTag.class);
        if (tag == null) {
            return new EnumPropTags[0];
        } else {
            return tag.value();
        }
    }
}
