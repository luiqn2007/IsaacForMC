package lq2007.mcmod.isaacformc.common.prop.type;

import com.google.common.collect.Lists;
import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.prop.Prop;
import lq2007.mcmod.isaacformc.common.util.serializer.ISerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.ResourceLocationSerializer;
import lq2007.mcmod.isaacformc.common.util.serializer.Serializer;
import lq2007.mcmod.isaacformc.isaac.IsaacElement;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

@Serializer(AbstractPropType.Serializer.class)
public abstract class AbstractPropType extends IsaacElement {

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
        PropRegister.register(this);
        this.rooms = Lists.newArrayList(rooms);
        this.charge = charge;
        this.nameKey = key.getNamespace() + ".prop." + key.getPath() + ".name";
        this.descriptionKey = key.getNamespace() + ".prop." + key.getPath() + ".desc";
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent(getNameKey());
    }

    /**
     * Get the prop's language key
     * @return key
     */
    public String getNameKey() {
        return nameKey;
    }

    @Override
    public ITextComponent getDescription() {
        return new TranslationTextComponent(getDescriptionKey());
    }

    /**
     * Get the prop's description language key
     * @return key
     */
    public String getDescriptionKey() {
        return descriptionKey;
    }

    /**
     * Get the room contains the prop
     * @return rooms or pools
     */
    public List<EnumPropPools> spawnRoom() {
        return rooms;
    }

    /**
     * True if some active prop can keep active state, like Notched Axe and Breath of Life
     * @see <a herf="http://isaac.huijiwiki.com/wiki/%E9%93%81%E9%95%90">Notched Axe</a>
     * @see <a herf="http://isaac.huijiwiki.com/wiki/%E7%94%9F%E5%91%BD%E7%9A%84%E6%B0%94%E6%81%AF">Breath of Life</a>
     * @return true if the prop can keep active state
     */
    public boolean canActiveKeeping(LivingEntity entity, Prop prop) {
        return false;
    }

    /**
     * Call when the active prop is in active state
     * @param entity entity use the prop
     * @param prop the active prop
     * @param keeping true if the prop is keep using
     */
    public void onActive(LivingEntity entity, Prop prop, boolean keeping) { }

    /**
     * Call when the prop is picked up by entity
     * @param entity entity
     * @param item prop
     * @return true if the prop can be picked up by the entity
     */
    public boolean onPickup(LivingEntity entity, Prop item) {
        return true;
    }

    /**
     * Call when the prop is removed from the entity
     * @param entity entity
     * @param item prop
     * @param removeRecord true if remove record
     */
    public void onRemove(LivingEntity entity, Prop item, boolean removeRecord) {
    }

    /**
     * Return true if the prop is an active prop.
     */
    public boolean isActive() {
        return charge > 0;
    }

    /**
     * Return false if an entity can hold only one prop or only one is effective.
     */
    public boolean isExclusive() {
        return isActive();
    }

    /**
     * Return a capability provider bind to the prop
     */
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

    /**
     * Return the prop's some tags from its annotation. It may be empty if the prop has no tag or no annotation
     */
    public EnumPropTags[] getTags() {
        PropTag tag = getClass().getAnnotation(PropTag.class);
        if (tag == null) {
            return new EnumPropTags[0];
        } else {
            return tag.value();
        }
    }

    public static class Serializer implements ISerializer<AbstractPropType> {

        @Override
        public AbstractPropType read(PacketBuffer buffer) {
            ResourceLocation key = ResourceLocationSerializer.INSTANCE.read(buffer);
            return PropRegister.get(key, PropRegister.EMPTY);
        }

        @Override
        public PacketBuffer write(AbstractPropType item, PacketBuffer buffer) {
            return ResourceLocationSerializer.INSTANCE.write(item.key, buffer);
        }

        @Override
        public AbstractPropType read(CompoundNBT nbt, String key) {
            ResourceLocation name = ResourceLocationSerializer.INSTANCE.read(nbt, key);
            return PropRegister.get(name, PropRegister.EMPTY);
        }

        @Override
        public CompoundNBT write(CompoundNBT nbt, String key, AbstractPropType item) {
            return ResourceLocationSerializer.INSTANCE.write(nbt, key, item.key);
        }
    }
}
