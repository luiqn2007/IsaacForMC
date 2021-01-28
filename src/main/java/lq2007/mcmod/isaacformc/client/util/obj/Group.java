package lq2007.mcmod.isaacformc.client.util.obj;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;

import java.util.HashMap;
import java.util.Map;

public final class Group {

    private Object object = null;
    private ImmutableMap<String, Object> childrenObject = ImmutableMap.of();
    private ImmutableMap<String, Group> childrenGroup = ImmutableMap.of();

    protected final Object.Builder objBuilder;
    protected final Map<String, Object.Builder> childrenObjBuilders = new HashMap<>();
    protected final Map<String, Group> childrenGroupBuilders = new HashMap<>();
    protected final Group parent;
    public final String name;
    public final boolean isRoot;

    public Group(String name, Group parent) {
        this.parent = parent;
        this.name = name;
        this.objBuilder = new Object.Builder(name, null);
        this.isRoot = parent == null;
        if (parent != null) {
            parent.addGroup(name, parent);
        }
    }

    public void addObject(String name, Object.Builder object) {
        childrenObjBuilders.put(name, object);
    }

    private void addGroup(String name, Group group) {
        childrenGroupBuilders.put(name, group);
    }

    public void build(OBJModel model) {
        object = objBuilder.build(model);

        Map.Entry<String, Object>[] entries = new Map.Entry[childrenObjBuilders.size()];
        int ptr = 0;
        for (Map.Entry<String, Object.Builder> next : childrenObjBuilders.entrySet()) {
            entries[ptr++] = new Map.Entry<String, Object>() {
                Object obj = null;

                @Override
                public String getKey() {
                    return next.getKey();
                }

                @Override
                public Object getValue() {
                    if (obj == null) {
                        obj = next.getValue().build(model);
                    }
                    return obj;
                }

                @Override
                public Object setValue(Object value) {
                    return null;
                }
            };
        }
        childrenObject = ImmutableMap.copyOf(() -> Iterators.forArray(entries));

        childrenGroup = ImmutableMap.copyOf(childrenGroupBuilders);
        childrenGroup.forEach((s, group) -> group.build(model));
    }

    public ImmutableMap<String, Object> getObjects() {
        return childrenObject;
    }

    public ImmutableMap<String, Group> getGroups() {
        return childrenGroup;
    }

    public Object getObject() {
        return object;
    }
}
