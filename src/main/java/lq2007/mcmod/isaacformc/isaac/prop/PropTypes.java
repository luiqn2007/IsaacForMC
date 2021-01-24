package lq2007.mcmod.isaacformc.isaac.prop;

import lq2007.mcmod.isaacformc.isaac.prop.data.IPropData;
import lq2007.mcmod.isaacformc.isaac.prop.type.*;
import lq2007.mcmod.isaacformc.isaac.room.EnumPropPools;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PropTypes {

    // ISAAC_REBIRTH
    public static final EmptyProp EMPTY = EmptyProp.EMPTY;
    public static final TheSadOnion THE_SAD_ONION = new TheSadOnion("the_sad_onion", false, 1, EnumPropPools.NORMAL);
    public static final TheInnerEye THE_INNER_EYE = new TheInnerEye("the_inner_eye", false, 2, EnumPropPools.NORMAL);
    public static final SpoonBender SPOON_BENDER = new SpoonBender("spoon_bender", false, 3, EnumPropPools.NORMAL);
    public static final CricketsHead CRICKETS_HEAD = new CricketsHead("crickets_head", false, 4, EnumPropPools.NORMAL_AND_GOLDEN_CHEST);
    public static final MyReflection MY_REFLECTION = new MyReflection("my_reflection", false, 5, EnumPropPools.NORMAL);
    public static final NumberOne NUMBER_ONE = new NumberOne("number_one", false, 6, EnumPropPools.NORMAL);
    public static final BloodOfTheMartyr BLOOD_OF_THE_MARTYR = new BloodOfTheMartyr("blood_of_the_martyr", false, 7, EnumPropPools.NORMAL_AND_ANGLE);
    public static final BrotherBobby BROTHER_BOBBY = new BrotherBobby("brother_bobby", false, 8, EnumPropPools.NORMAL_AND_DEVIL);
    public static final Skatole SKATOLE = new Skatole("skatole", false, 9, EnumPropPools.SHELL_GAME);

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
