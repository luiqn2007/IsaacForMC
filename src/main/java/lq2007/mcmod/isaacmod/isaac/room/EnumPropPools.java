package lq2007.mcmod.isaacmod.isaac.room;

public enum EnumPropPools {
    ANGLE,
    DEVIL,
    SHOP,
    GREED_SHOP,
    TREASURE,
    GREED_TREASURE,
    GOLDEN_CHEST,
    /**
     * 赌博乞丐
     */
    SHELL_GAME
    ;

    public static EnumPropPools[] NORMAL =
            new EnumPropPools[] { SHOP, GREED_SHOP, TREASURE, GREED_TREASURE };
    public static EnumPropPools[] NORMAL_AND_GOLDEN_CHEST =
            new EnumPropPools[] { SHOP, GREED_SHOP, TREASURE, GREED_TREASURE, GOLDEN_CHEST };
    public static EnumPropPools[] NORMAL_AND_ANGLE =
            new EnumPropPools[] { SHOP, GREED_SHOP, TREASURE, GREED_TREASURE, ANGLE };
    public static EnumPropPools[] NORMAL_AND_DEVIL =
            new EnumPropPools[] { SHOP, GREED_SHOP, TREASURE, GREED_TREASURE, DEVIL };
}
