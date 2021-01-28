package lq2007.mcmod.isaacformc.client.util.obj;

import java.util.HashMap;
import java.util.Map;

public class Group extends Object {
    public final Map<String, Object> children = new HashMap<>();

    protected Group() { }

    public Group(Group parent, String name) {
        super(parent, name);
    }
}
