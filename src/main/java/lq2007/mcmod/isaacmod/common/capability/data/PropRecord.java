package lq2007.mcmod.isaacmod.common.capability.data;

import lq2007.mcmod.isaacmod.common.prop.Prop;
import lq2007.mcmod.isaacmod.common.prop.type.AbstractPropType;
import lq2007.mcmod.isaacmod.common.util.serializer.packet.INBTSerializable;
import lq2007.mcmod.isaacmod.common.util.serializer.packet.NBTData;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.BufferData;
import lq2007.mcmod.isaacmod.common.util.serializer.buffer.IPacketSerializable;

import javax.annotation.Nullable;
import java.util.LinkedList;

public class PropRecord implements INBTSerializable, IPacketSerializable {

    @BufferData @NBTData
    public AbstractPropType type;
    @NBTData(collection = LinkedList.class, V = Prop.class)
    @BufferData(collection = LinkedList.class, V = Prop.class)
    private LinkedList<Prop> props = new LinkedList<>();
    @BufferData @NBTData
    private boolean noPicked = true;

    public PropRecord() { }

    public PropRecord(AbstractPropType type) {
        this.type = type;
    }

    @Nullable
    public Prop add(Prop prop) {
        if (prop.isEmpty) {
            return null;
        }
        Prop removed = null;
        if (noPicked) {
            noPicked = false;
        } else if (type.isExclusive()) {
            removed = props.pollLast();
            props.clear();
        }
        props.add(prop);
        return removed;
    }

    public void remove(boolean removeRecord) {
        if (!noPicked) {
            Prop prop = props.pollLast();
            if (removeRecord && props.isEmpty()) {
                noPicked = true;
            }
        }
    }

    public void clear(boolean removeRecord) {
        if (!noPicked) {
            props.clear();
            if (removeRecord) {
                noPicked = true;
            }
        }
    }

    public Prop getFirst() {
        return props.isEmpty() ? new Prop() : props.getFirst();
    }

    public boolean isEmpty() {
        return noPicked || props.isEmpty();
    }

    public boolean isPicked() {
        return !noPicked;
    }
}
