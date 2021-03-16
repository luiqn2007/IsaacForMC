package lq2007.mcmod.isaacformc.common.prop.type;

import lq2007.mcmod.isaacformc.common.prop.type.ab.*;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Props {

    // ISAAC_REBIRTH
    public static final EmptyProp EMPTY = EmptyProp.EMPTY;
    public static final BrotherBobby BROTHER_BOBBY = new BrotherBobby(8);

    private static final Map<ResourceLocation, AbstractPropType> PROPS = new HashMap<>();
    private static final Map<ResourceLocation, AbstractPropType> PASSIVE_PROPS = new HashMap<>();
    private static final Map<ResourceLocation, AbstractPropType> ACTIVE_PROPS = new HashMap<>();

    public static <T extends AbstractPropType> Optional<T> get(ResourceLocation key) {
        return Optional.ofNullable((T) PROPS.get(key));
    }

    public static AbstractPropType get(ResourceLocation key, AbstractPropType defaultValue) {
        return PROPS.getOrDefault(key, defaultValue);
    }

    public static void register(AbstractPropType prop) {
        ResourceLocation key = prop.key;
        if (PROPS.containsKey(key)) {
            throw new RuntimeException("Reduplicated Key " + key + "!");
        }
        PROPS.put(key, prop);
        if (prop.isActive()) {
            ACTIVE_PROPS.put(key, prop);
        } else {
            PASSIVE_PROPS.put(key, prop);
        }
    }
}
