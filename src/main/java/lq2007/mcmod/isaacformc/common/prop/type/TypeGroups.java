package lq2007.mcmod.isaacformc.common.prop.type;

import lq2007.mcmod.isaacformc.common.capability.IIsaacPropData;
import lq2007.mcmod.isaacformc.common.capability.IIsaacProperty;
import lq2007.mcmod.isaacformc.common.prop.type.ab.BloodOfTheMartyr;
import lq2007.mcmod.isaacformc.common.prop.type.ab.CricketsHead;
import lq2007.mcmod.isaacformc.common.prop.type.ab.TheInnerEye;

import java.util.UUID;

public class TypeGroups {

    /**
     * <p>Cricket's Head, Magic Mushroom, Blood of the Martyr.</p>
     * <p>The Book of Belial: not implement this interface but check it.</p>
     * @see CricketsHead
     * @see MagicMushroom
     * @see BloodOfTheMartyr
     * @see TheBookOfBelial
     */
    public static final UUID DAMAGE_FIX_0 = UUID.fromString("d846b0ac-9adc-a4e5-7b43-9f55d75a9542");

    /**
     * The Inner Eye, Mutant Spider, Polyphemus
     * @see TheInnerEye
     * @see MutantSpider
     * @see Polyphemus
     */
    public interface ShootDelay0 {
        default void apply(IIsaacPropData propData, IIsaacProperty property) {
            // 射速延迟 +3 *210%
            property.shootDelay(property.shootDelay() + 3);
            property.shootDelayMultiple(property.shootDelayMultiple() * 2.1F /* 210% */);
        }
    }
}
