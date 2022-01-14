package lq2007.mcmod.isaacmod.prop;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModProps {

    final static Map<ResourceLocation, AbstractPropType> propTypeMap = new HashMap<>();

    // source generate
// ===> generated source begin
    public static final PropBrotherBobby BROTHER_BOBBY = new PropBrotherBobby();
    public static final PropEmpty EMPTY = PropEmpty.INSTANCE;
    public static final PropTheSadOnion THE_SAD_ONION = new PropTheSadOnion();
// ===> generated source end

    public static AbstractPropType get(ResourceLocation name) {
        return propTypeMap.getOrDefault(name, EMPTY);
    }

    public static AbstractPropType getNullable(ResourceLocation name) {
        return propTypeMap.get(name);
    }

    public static Optional<AbstractPropType> getOpt(ResourceLocation name) {
        return Optional.ofNullable(getNullable(name));
    }
}
