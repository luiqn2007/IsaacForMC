package lq2007.mcmod.isaacformc.isaac.property;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import lq2007.mcmod.isaacformc.isaac.IsaacItem;
import net.minecraft.util.ResourceLocation;

public class Property {

    private float value;
    private final Object2IntMap<ResourceLocation> sign = new Object2IntArrayMap<>();

    public EnumPropertyType type;

    public Property(float value, EnumPropertyType type) {
        this.value = value;
        this.type = type;

        sign.defaultReturnValue(0);
    }

    public void add(float value) {
        this.value += value;
    }

    public void add(float value, float maxValue) {
        this.value += value;
        if (this.value > maxValue) {
            this.value = maxValue;
        }
    }

    public void dec(float value) {
        this.value -= value;
    }

    public void dec(float value, float minValue) {
        this.value -= value;
        if (this.value < minValue) {
            this.value = minValue;
        }
    }

    public void mul(float value) {
        this.value *= value;
    }

    public void mul(float value, float maxValue) {
        this.value *= value;
        if (this.value > maxValue) {
            this.value = maxValue;
        }
    }

    public void mul(float value, float minValue, float maxValue) {
        this.value *= value;
        if (this.value > maxValue) {
            this.value = maxValue;
        }
        if (this.value < minValue) {
            this.value = minValue;
        }
    }

    public void mark(IsaacItem item) {
        sign.put(item.key, sign.getInt(item.key) + 1);
    }

    public int markCount(IsaacItem item) {
        return sign.getInt(item.key);
    }

    public boolean hasMark(IsaacItem item) {
        return sign.containsKey(item.key);
    }

    public float get() {
        return value;
    }
}
