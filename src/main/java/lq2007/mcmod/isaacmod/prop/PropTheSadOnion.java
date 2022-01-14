package lq2007.mcmod.isaacmod.prop;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.UUID;

/**
 * 伤心洋葱
 * The Sad Onion
 *
 * 射速+0.70
 */
public class PropTheSadOnion extends AbstractPropType {

    public static final UUID MODIFIER = UUID.fromString("a5fc6e35-3836-4c5d-b869-ab1e7806e359");

    PropTheSadOnion() {
        super(false, true);
    }

    @Override
    public void onApply(World world, PlayerEntity player, Prop prop) {
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.ATTACK_SPEED);
        Objects.requireNonNull(attribute, "Attack speed is unsupported");
        attribute.applyNonPersistentModifier(new AttributeModifier(MODIFIER, "mod_isaac_sad_onion", 0.7, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public void onRemove(World world, PlayerEntity player, Prop prop) {
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.ATTACK_SPEED);
        Objects.requireNonNull(attribute, "Attack speed is unsupported");
        attribute.removeModifier(MODIFIER);
    }
}
