package lq2007.mcmod.isaacformc.isaac.prop;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.common.event.PickupPropItemEvent;
import lq2007.mcmod.isaacformc.isaac.IsaacElement;
import lq2007.mcmod.isaacformc.isaac.prop.data.IPropData;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class PropType<T extends IPropData> extends IsaacElement {

    private final String nameKey;
    private final String descriptionKey;
    private final boolean isActive;
    private final List<EnumPropPools> rooms;

    public PropType(ResourceLocation key, boolean isActive, EnumPropPools... rooms) {
        this(key, isActive, 0, rooms);
    }

    public PropType(String name, boolean isActive, int id, EnumPropPools... rooms) {
        this(new ResourceLocation(Isaac.ID, name), isActive, id, rooms);
    }

    protected PropType(ResourceLocation key, boolean isActive, int id, EnumPropPools... rooms) {
        super(key, id);
        PropTypes.register(this);
        this.rooms = Lists.newArrayList(rooms);
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

    public List<EnumPropPools> spawnRoom() {
        return rooms;
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

    public void onRemove(LivingEntity entity, PropItem item, ImmutableList<PropItem> removedItems) {
        IsaacCapabilities.getPropData(entity).removeProp(item);
    }

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
