package lq2007.mcmod.isaacformc.client.util.obj;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class Object {
    public final String name;
    public final ImmutableList<Mesh> meshes;
    public final Group parent;

    public Object(String name, ImmutableList<Mesh> meshes, Group parent) {
        this.name = name;
        this.meshes = meshes;
        this.parent = parent;
    }

    public static class Builder {
        protected String name;
        protected Group parent;
        protected List<Mesh.Builder> meshes;

        public Builder(String name, Group parent) {
            this.name = parent == null ? name : parent.name + "/" + name;
            this.parent = parent;
            if (parent != null) {
                parent.addObject(name, this);
            }
        }

        public void addMesh(Mesh.Builder mesh) {
            this.meshes.add(mesh);
        }

        public Object build(OBJModel model) {
            ImmutableList<Mesh> meshImmutableList;
            if (meshes.size() == 0) {
                meshImmutableList = ImmutableList.of();
            } else {
                Mesh[] meshes = new Mesh[this.meshes.size()];
                for (int i = 0; i < meshes.length; i++) {
                    meshes[i] = this.meshes.get(i).build(model);
                }
                meshImmutableList = ImmutableList.copyOf(meshes);
            }
            return new Object(name, meshImmutableList, parent);
        }
    }
}
