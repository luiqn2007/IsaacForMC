package lq2007.mcmod.isaacformc.isaac.prop.type;

import lq2007.mcmod.isaacformc.Isaac;
import lq2007.mcmod.isaacformc.isaac.prop.PropItem;
import lq2007.mcmod.isaacformc.isaac.prop.PropType;
import lq2007.mcmod.isaacformc.isaac.prop.PropTypes;
import lq2007.mcmod.isaacformc.isaac.property.Property;
import net.minecraft.util.ResourceLocation;

public class MutantSpider extends PropType {

    public MutantSpider() {
        super(new ResourceLocation(Isaac.ID, "mutant_spider"), false, 153);
    }

    @Override
    public void setProperty(PropItem item, Property property) {
        switch (property.type) {
            case TEAR_COUNT: property.add(3, 16);break;
            case SHOOT_DELAY: {
                if (!property.hasMark(PropTypes.THE_INNER_EYE)
                        && !property.hasMark(PropTypes.MUTANT_SPIDER)
                        && !property.hasMark(PropTypes.POLYPHEMUS)) {
                    property.mul(2.1f);
                    property.add(3);
                    property.mark(this);
                }
                break;
            }
        }
    }
}
