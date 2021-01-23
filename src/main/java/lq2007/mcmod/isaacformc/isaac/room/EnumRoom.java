package lq2007.mcmod.isaacformc.isaac.room;

public enum EnumRoom {
    ANGLE,
    DEVIL,
    SHOP,
    GREED_SHOP,
    TREASURE,
    GREED_TREASURE,
    GOLDEN_CHEST;

    public static EnumRoom[] NORMAL =
            new EnumRoom[] { SHOP, GREED_SHOP, TREASURE, GREED_TREASURE };
    public static EnumRoom[] NORMAL_AND_GOLDEN_CHEST =
            new EnumRoom[] { SHOP, GREED_SHOP, TREASURE, GREED_TREASURE, GOLDEN_CHEST };
    public static EnumRoom[] NORMAL_AND_ANGLE =
            new EnumRoom[] { SHOP, GREED_SHOP, TREASURE, GREED_TREASURE, ANGLE };
    public static EnumRoom[] NORMAL_AND_DEVIL =
            new EnumRoom[] { SHOP, GREED_SHOP, TREASURE, GREED_TREASURE, DEVIL };
}
