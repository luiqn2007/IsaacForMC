package lq2007.mcmod.isaacmod.client.obj;

import com.google.common.collect.ImmutableList;
import de.javagl.obj.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class OBJ {

    public static final Map<ResourceLocation, OBJ> OBJ_MAP = new HashMap<>();

    public static OBJ getOrCreate(ResourceLocation objResource, Function<String, ResourceLocation> textureMap) throws IOException {
        OBJ o = OBJ_MAP.get(objResource);
        if (o != null && o.isInvalid) return o;
        try (IResource resource = Minecraft.getInstance().getResourceManager().getResource(objResource)) {
            OBJ obj = new OBJ(objResource, resource, textureMap);
            if (obj.isInvalid) {
                OBJ_MAP.put(objResource, obj);
                return obj;
            }
        }
        throw new IOException("Read obj " + objResource + " failed.");
    }

    public boolean isInvalid = false;

    public Obj obj;
    public ResourceLocation objResource;
    public ImmutableList<ResourceLocation> mtlResourceList;
    public ImmutableList<MTLLib> mtlList;

    OBJ(ResourceLocation objResource, IResource resource, Function<String, ResourceLocation> textureMap) throws IOException {
        this.objResource = objResource;
        this.obj = ObjReader.read(resource.getInputStream());
        // mtl
        List<String> mtlFileNames = obj.getMtlFileNames();
        ResourceLocation[] mtlNames = new ResourceLocation[mtlFileNames.size()];
        int index = objResource.getPath().lastIndexOf('/');
        String path = index > 0 ? objResource.getPath().substring(0, index + 1) : "";
        for (int i = 0; i < mtlNames.length; i++) {
            mtlNames[i] = new ResourceLocation(objResource.getNamespace(), path + mtlFileNames.get(i));
        }
        mtlResourceList = ImmutableList.copyOf(mtlNames);
        MTLLib[] mtls = new MTLLib[mtlResourceList.size()];
        for (int i = 0; i < mtls.length; i++) {
            mtls[i] = MTLLib.getOrCreate(mtlNames[i], textureMap);
        }
        mtlList = ImmutableList.copyOf(mtls);

        isInvalid = true;
    }

    public ReadableObj readable() {
        return obj;
    }
}
