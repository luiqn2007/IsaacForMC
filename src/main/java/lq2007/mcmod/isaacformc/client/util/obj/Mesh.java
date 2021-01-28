package lq2007.mcmod.isaacformc.client.util.obj;

import com.google.common.collect.Lists;
import net.minecraftforge.client.model.obj.MaterialLibrary;

import javax.annotation.Nullable;
import java.util.List;

public class Mesh {
    public MaterialLibrary.Material mat;
    public String smoothingGroup;
    public final List<int[][]> faces = Lists.newArrayList();

    public ModelMesh(@Nullable MaterialLibrary.Material currentMat, @Nullable String currentSmoothingGroup) {
        this.mat = currentMat;
        this.smoothingGroup = currentSmoothingGroup;
    }
}
