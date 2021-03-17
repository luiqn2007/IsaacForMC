package lq2007.mcmod.isaacmod.client.util.obj;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.client.model.obj.MaterialLibrary;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    public final MaterialLibrary.Material material;
    public final String smoothingGroup;
    public final ImmutableList<Face> faces;

    public Mesh(MaterialLibrary.Material material, String smoothingGroup, ImmutableList<Face> faces) {
        this.material = material;
        this.smoothingGroup = smoothingGroup;
        this.faces = faces;
    }

    public static class Builder {

        private MaterialLibrary.Material material;
        private String smoothingGroup;
        private final List<Face.Builder> faces = new ArrayList<>();

        public Builder material(MaterialLibrary.Material material) {
            this.material = material;
            return this;
        }

        public Builder materialOrFinish(MaterialLibrary.Material material) {
            if (this.material == null && faces.isEmpty()) {
                this.material = material;
                return this;
            }
            return null;
        }

        public Builder smoothingGroup(String group) {
            this.smoothingGroup = group;
            return this;
        }

        public Builder smoothingGroupOrFinish(String group) {
            if (smoothingGroup == null && faces.isEmpty()) {
                this.smoothingGroup = group;
                return this;
            }
            return null;
        }

        public Face.Builder addFace(int vertexCount) {
            Face.Builder face = new Face.Builder(vertexCount);
            this.faces.add(face);
            return face;
        }

        public Mesh build(OBJModel model) {
            Face[] faces = new Face[this.faces.size()];
            for (int i = 0; i < faces.length; i++) {
                faces[i] = this.faces.get(i).build(model);
            }
            return new Mesh(material, smoothingGroup, ImmutableList.copyOf(faces));
        }
    }
}
