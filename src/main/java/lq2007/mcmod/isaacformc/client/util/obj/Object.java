package lq2007.mcmod.isaacformc.client.util.obj;

import java.util.ArrayList;
import java.util.List;

public class Object {
    public final String name;
    public final List<Mesh> meshes = new ArrayList<>();
    public final Group parent;
    public final boolean isRoot;

    public Object(Group parent, String name) {
        this.parent = parent;
        this.name = name;
        this.isRoot = false;

        parent.children.put(name, this);
    }

    protected Object() {
        this.parent = null;
        this.name = "";
        this.isRoot = true;
    }
}
