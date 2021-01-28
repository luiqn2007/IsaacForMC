package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ObjModelLoader implements IModelCustomLoader {

    private static final String[] types = {"obj"};

    public String getType() {
        return "OBJ model";
    }

    public String[] getSuffixes() {
        return types;
    }

    public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException {
        return new WavefrontObject(resource);
    }
}
