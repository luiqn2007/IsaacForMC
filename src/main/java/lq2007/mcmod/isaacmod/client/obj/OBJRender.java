package lq2007.mcmod.isaacmod.client.obj;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class OBJRender {

    public static void render(ResourceLocation objResource) {
        OBJ obj = OBJ.getOrCreate(objResource, tex -> );
        obj.readable().
    }
}
