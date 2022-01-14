package lq2007.mcmod.isaacmod.util;


import net.minecraftforge.fml.loading.FMLEnvironment;

public class I18n {

    public static String get(String key) {
        if (FMLEnvironment.dist.isClient()) {
            net.minecraft.client.resources.I18n.format(key);
        }
        return key;
    }
}
