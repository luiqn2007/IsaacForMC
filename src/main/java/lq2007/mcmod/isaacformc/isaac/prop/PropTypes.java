package lq2007.mcmod.isaacformc.isaac.prop;

import lq2007.mcmod.isaacformc.isaac.prop.data.IPropData;
import lq2007.mcmod.isaacformc.isaac.prop.type.*;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PropTypes {

    // ISAAC_REBIRTH
    public static final EmptyProp EMPTY = EmptyProp.EMPTY;
    public static final TheSadOnion THE_SAD_ONION = new TheSadOnion();
    public static final TheInnerEye THE_INNER_EYE = new TheInnerEye();
    public static final SpoonBender SPOON_BENDER = new SpoonBender();
    public static final CricketsHead CRICKETS_HEAD = new CricketsHead();
    public static final MyReflection MY_REFLECTION = new MyReflection();
    public static final NumberOne NUMBER_ONE = new NumberOne();
    public static final BloodOfTheMartyr BLOOD_OF_THE_MARTYR = new BloodOfTheMartyr();

    public static final MutantSpider MUTANT_SPIDER = new MutantSpider();
    public static final Polyphemus POLYPHEMUS = new Polyphemus();

    private static final Map<ResourceLocation, PropType<?>> PROPS = new HashMap<>();
    private static final Map<ResourceLocation, PropType<?>> PASSIVE_PROPS = new HashMap<>();
    private static final Map<ResourceLocation, PropType<?>> ACTIVE_PROPS = new HashMap<>();

    public static <T extends IPropData> Optional<PropType<T>> get(ResourceLocation key) {
        return Optional.ofNullable((PropType<T>) PROPS.get(key));
    }

    @Nullable
    public static PropType<?> get(ResourceLocation key, @Nullable PropType<?> defaultValue) {
        return PROPS.getOrDefault(key, defaultValue);
    }

    public static void register(PropType<?> prop) {
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
