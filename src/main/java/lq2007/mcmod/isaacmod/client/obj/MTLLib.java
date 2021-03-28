package lq2007.mcmod.isaacmod.client.obj;

import com.google.common.collect.ImmutableList;
import de.javagl.obj.Mtl;
import de.javagl.obj.MtlReader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class MTLLib {

    public static final Map<ResourceLocation, MTLLib> MTL_MAP = new HashMap<>();

    public static MTLLib getOrCreate(ResourceLocation mtlResource, Function<String, ResourceLocation> textureMap) throws IOException {
        MTLLib m = MTL_MAP.get(mtlResource);
        if (m != null && m.isInvalid) return m;
        try (IResource resource = Minecraft.getInstance().getResourceManager().getResource(mtlResource)) {
            MTLLib mtl = new MTLLib(mtlResource, resource, textureMap);
            if (mtl.isInvalid) {
                MTL_MAP.put(mtlResource, mtl);
                return mtl;
            }
        }
        throw new IOException("Read mtllib " + mtlResource + " failed.");
    }

    public boolean isInvalid = false;

    public final ResourceLocation mtlResource;
    public final ImmutableList<Mtl> mtlList;
    public final ImmutableList<ResourceLocation> textureList;

    MTLLib(ResourceLocation mtlResource, IResource resource, Function<String, ResourceLocation> textureMap) throws IOException {
        this.mtlResource = mtlResource;
        mtlList = ImmutableList.copyOf(MtlReader.read(resource.getInputStream()));
        ResourceLocation[] resources = new ResourceLocation[mtlList.size()];
        for (int i = 0; i < mtlList.size(); i++) {
            Mtl mtl = mtlList.get(i);
            String textureName = mtl.getMapKd();
            if (StringUtils.isNullOrEmpty(textureName)) {
                // todo error texture
                resources[i] = null;
            } else {
                resources[i] = textureMap.apply(textureName);
            }
        }
        textureList = ImmutableList.copyOf(resources);
        isInvalid = true;
    }
}
