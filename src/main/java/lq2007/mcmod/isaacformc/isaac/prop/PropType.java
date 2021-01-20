package lq2007.mcmod.isaacformc.isaac.prop;

import lq2007.mcmod.isaacformc.isaac.IsaacItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropTypes;
import lq2007.mcmod.isaacformc.isaac.prop.data.IPropData;
import lq2007.mcmod.isaacformc.common.util.I18n;
import lq2007.mcmod.isaacformc.isaac.property.Property;
import lq2007.mcmod.isaacformc.isaac.tear.EnumSpecialTearType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class PropType extends IsaacItem {

    private final String nameKey;
    private final String descriptionKey;
    private final boolean isActive;

    public PropType(ResourceLocation key, boolean isActive) {
        this(key, isActive, 0);
    }

    public PropType(ResourceLocation key, boolean isActive, int id) {
        super(key, id);
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

    public void setProperty(PropItem item, Property property) {}

    public void setSpecialTear(List<EnumSpecialTearType> types) { }

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
