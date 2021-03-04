package lq2007.mcmod.isaacformc.isaac.suit;

import com.google.common.collect.Lists;
import lq2007.mcmod.isaacformc.common.Isaac;
import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IsaacCapabilities;
import lq2007.mcmod.isaacformc.isaac.IsaacElement;
import lq2007.mcmod.isaacformc.common.prop.type.AbstractPropType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public abstract class SuitType extends IsaacElement {

    private final String nameKey;
    private final String descriptionKey;
    public final List<AbstractPropType> types;

    public SuitType(ResourceLocation key, int id, AbstractPropType... contains) {
        super(key, id);
        this.types = Lists.newArrayList(contains);
        this.nameKey = key.getNamespace() + ".suit." + key.getPath() + ".name";
        this.descriptionKey = key.getNamespace() + ".suit." + key.getPath() + ".desc";
    }

    public SuitType(String key, int id, AbstractPropType... contains) {
        this(new ResourceLocation(Isaac.ID, key), id, contains);
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

    public boolean checkEntity(LivingEntity entity) {
        IIsaacPropData data = IsaacCapabilities.getPropData(entity);
        int count = 0;
        for (AbstractPropType type : types) {
            if (data.contains(type)) {
                count++;
                if (count >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public void onRealized(LivingEntity entity) {
        // todo: bind to entity
    }

    public void onUpdate(LivingEntity entity) {}
}
