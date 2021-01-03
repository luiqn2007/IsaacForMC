package lq2007.mcmod.isaacformc.common.isaac.prop;

import lq2007.mcmod.isaacformc.common.isaac.IsaacItem;
import lq2007.mcmod.isaacformc.common.util.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public abstract class PropType extends IsaacItem {

    private final String nameKey;
    private final String descriptionKey;
    private final boolean isActive;

    public PropType(ResourceLocation key, boolean isActive) {
        super(key);
        PropTypes.register(this);
        this.isActive = isActive;
        this.nameKey = key.getNamespace() + "." + key.getPath() + ".name";
        this.descriptionKey = key.getNamespace() + "." + key.getPath() + ".desc";
    }

    public IPropData createData() {
        return NoData.INSTANCE;
    }

    @Override
    public String getName() {
        return I18n.get(nameKey);
    }

    public String getNameKey() {
        return nameKey;
    }

    @Override
    public String getDescription() {
        return I18n.get(descriptionKey);
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public boolean onActive(PlayerEntity player, PropItem prop) {
        return false;
    }

    public void onPickUp(PlayerEntity player, PropItem prop) {

    }

    public boolean isActive() {
        return true;
    }
}
