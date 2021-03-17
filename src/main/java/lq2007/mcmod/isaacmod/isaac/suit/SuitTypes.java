package lq2007.mcmod.isaacmod.isaac.suit;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SuitTypes {

    public static final Normal EMPTY = new Normal("normal", 0);
    public static final Conjoined CONJOINED = new Conjoined("normal", 0);

    public static final Map<ResourceLocation, SuitType> SUITS = new HashMap<>();

    public static Optional<SuitType> get(ResourceLocation key) {
        return Optional.ofNullable(SUITS.get(key));
    }

    @Nullable
    public static SuitType get(ResourceLocation key, @Nullable SuitType defaultValue) {
        return SUITS.getOrDefault(key, defaultValue);
    }

    public static void register(SuitType prop) {
        ResourceLocation key = prop.key;
        if (SUITS.containsKey(key)) {
            throw new RuntimeException("Reduplicated Key " + key + "!");
        }
        SUITS.put(key, prop);
    }
}
