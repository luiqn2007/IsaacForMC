package lq2007.mcmod.isaacformc.isaac.prop;

import lq2007.mcmod.isaacformc.common.Isaac;
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

public abstract class PropType extends IsaacItem {

    private final String nameKey;
    private final String descriptionKey;
    private final boolean isActive;

    public PropType(ResourceLocation key, boolean isActive, EnumRoom... rooms) {
        this(key, EnumIsaacVersion.MOD, isActive, 0, rooms);
    }

    public PropType(String name, EnumIsaacVersion version, boolean isActive, int id, EnumRoom... rooms) {
        this(new ResourceLocation(Isaac.ID, name), version, isActive, id, rooms);
    }

    protected PropType(ResourceLocation key, EnumIsaacVersion version, boolean isActive, int id, EnumRoom... rooms) {
        super(key, id, version, rooms);
        PropTypes.register(this);
        this.isActive = isActive;
        this.nameKey = key.getNamespace() + "." + key.getPath() + ".name";
        this.descriptionKey = key.getNamespace() + "." + key.getPath() + ".desc";
    }

    public IPropData createData() {
        return IPropData.NO_DATA;
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

    public boolean onActive(LivingEntity entity, PropItem prop) {
        return false;
    }

    public void onPickup(LivingEntity entity, PropItem item) {}

    public void onRemove(LivingEntity entity, PropItem item) {}

    public boolean isActive() {
        return isActive;
    }

    @OnlyIn(Dist.CLIENT)
    public void renderOnFoundation(PropItem item, float partialTicks,
                                   com.mojang.blaze3d.matrix.MatrixStack matrixStackIn,
                                   net.minecraft.client.renderer.IRenderTypeBuffer bufferIn,
                                   int combinedLightIn, int combinedOverlayIn) {

    }
}
