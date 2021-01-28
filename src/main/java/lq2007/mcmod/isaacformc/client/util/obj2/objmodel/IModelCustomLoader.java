package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

import net.minecraft.util.ResourceLocation;

public interface IModelCustomLoader {

    String getType();

    String[] getSuffixes();

    IModelCustom loadInstance(ResourceLocation resourcelocation) throws ModelFormatException;
}
