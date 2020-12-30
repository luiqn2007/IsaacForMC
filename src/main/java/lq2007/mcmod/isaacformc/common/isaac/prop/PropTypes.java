package lq2007.mcmod.isaacformc.common.isaac.prop;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PropTypes {

    public static final PropType EMPTY = EmptyProp.EMPTY;

    private static final Map<ResourceLocation, PropType> PROPS = new HashMap<>();

    public static Optional<PropType> get(ResourceLocation key) {
        return Optional.ofNullable(PROPS.get(key));
    }

    public static PropType get(ResourceLocation key, PropType defaultValue) {
        return PROPS.getOrDefault(key, defaultValue);
    }

    public static void register(PropType prop) {
        ResourceLocation key = prop.key;
        if (PROPS.containsKey(key)) {
            throw new RuntimeException("Reduplicated Key " + key + "!");
        }
        PROPS.put(key, prop);
    }
}
