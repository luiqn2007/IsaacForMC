package lq2007.mcmod.isaacformc.isaac.prop;

import com.google.common.collect.ImmutableList;
import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.event.PickupPropItemEvent;
import lq2007.mcmod.isaacformc.isaac.EnumIsaacVersion;
import lq2007.mcmod.isaacformc.isaac.IsaacItem;
import lq2007.mcmod.isaacformc.isaac.prop.data.IPropData;
import lq2007.mcmod.isaacformc.isaac.room.EnumRoom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class PropType<T extends IPropData> extends IsaacItem {

    private final String nameKey;
    private final String descriptionKey;
    private final boolean isActive;

    public PropType(ResourceLocation key, boolean isActive, EnumRoom... rooms) {
        this(key, isActive, 0, rooms);
    }

    public PropType(String name, boolean isActive, int id, EnumRoom... rooms) {
        this(new ResourceLocation(Isaac.ID, name), isActive, id, rooms);
    }

    protected PropType(ResourceLocation key, boolean isActive, int id, EnumRoom... rooms) {
        super(key, id, rooms);
        PropTypes.register(this);
        this.isActive = isActive;
        this.nameKey = key.getNamespace() + ".prop." + key.getPath() + ".name";
        this.descriptionKey = key.getNamespace() + ".prop." + key.getPath() + ".desc";
    }

    abstract protected T createData();

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

    public void onActiveStart(LivingEntity entity, PropItem prop) { }

    public void onActiveFinished(LivingEntity entity, PropItem prop) { }

    public final void onPickup(LivingEntity entity, PropItem item) {
        if (!entity.world.isRemote) {
            PickupPropItemEvent event = new PickupPropItemEvent(entity, item);
            if (!Isaac.MOD.eventBus.post(event)) {
                onPickup(entity, event.getProp(), item);
            }
        }
    }

    public void onPickup(LivingEntity entity, PropItem item, PropItem itemBeforeEvent) {
        IsaacCapabilities.getPropData(entity).pickupProp(item);
    }

    public void onRemove(LivingEntity entity, PropItem item, ImmutableList<PropItem> removedItems) {}

    public boolean isActive() {
        return isActive;
    }

    // todo implement render
    @OnlyIn(Dist.CLIENT)
    public void renderOnFoundation(PropItem item, float partialTicks,
                                   com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                   net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                   int combinedLightIn, int combinedOverlayIn) { }

    public EnumPropTags[] getTags() {
        PropTag tag = getClass().getAnnotation(PropTag.class);
        if (tag == null) {
            return new EnumPropTags[0];
        } else {
            return tag.value();
        }
    }
}
