package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

import java.util.Comparator;

public class QuadComparator implements Comparator<Integer> {

    private final float x;
    private final float y;
    private final float z;
    private final int[] buffers;

    public QuadComparator(int[] buffers, float x, float y, float z) {
        this.buffers = buffers;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int compare(Integer obj0, Integer obj1) {
        float f   =   Float.intBitsToFloat(buffers[obj0])    - x;
        float f1  = Float.intBitsToFloat(buffers[obj0 + 1])  - y;
        float f2  = Float.intBitsToFloat(buffers[obj0 + 2])  - z;
        float f3  = Float.intBitsToFloat(buffers[obj0 + 8])  - x;
        float f4  = Float.intBitsToFloat(buffers[obj0 + 9])  - y;
        float f5  = Float.intBitsToFloat(buffers[obj0 + 10]) - z;
        float f6  = Float.intBitsToFloat(buffers[obj0 + 16]) - x;
        float f7  = Float.intBitsToFloat(buffers[obj0 + 17]) - y;
        float f8  = Float.intBitsToFloat(buffers[obj0 + 18]) - z;
        float f9  = Float.intBitsToFloat(buffers[obj0 + 24]) - x;
        float f10 = Float.intBitsToFloat(buffers[obj0 + 25]) - y;
        float f11 = Float.intBitsToFloat(buffers[obj0 + 26]) - z;
        float f12 = Float.intBitsToFloat(buffers[obj1])      - x;
        float f13 = Float.intBitsToFloat(buffers[obj1 + 1])  - y;
        float f14 = Float.intBitsToFloat(buffers[obj1 + 2])  - z;
        float f15 = Float.intBitsToFloat(buffers[obj1 + 8])  - x;
        float f16 = Float.intBitsToFloat(buffers[obj1 + 9])  - y;
        float f17 = Float.intBitsToFloat(buffers[obj1 + 10]) - z;
        float f18 = Float.intBitsToFloat(buffers[obj1 + 16]) - x;
        float f19 = Float.intBitsToFloat(buffers[obj1 + 17]) - y;
        float f20 = Float.intBitsToFloat(buffers[obj1 + 18]) - z;
        float f21 = Float.intBitsToFloat(buffers[obj1 + 24]) - x;
        float f22 = Float.intBitsToFloat(buffers[obj1 + 25]) - y;
        float f23 = Float.intBitsToFloat(buffers[obj1 + 26]) - z;
        float f24 = (f   + f3  + f6  + f9 ) * 0.25F;
        float f25 = (f1  + f4  + f7  + f10) * 0.25F;
        float f26 = (f2  + f5  + f8  + f11) * 0.25F;
        float f27 = (f12 + f15 + f18 + f21) * 0.25F;
        float f28 = (f13 + f16 + f19 + f22) * 0.25F;
        float f29 = (f14 + f17 + f20 + f23) * 0.25F;
        float f30 = f24 * f24 + f25 * f25 + f26 * f26;
        float f31 = f27 * f27 + f28 * f28 + f29 * f29;
        return Float.compare(f31, f30);
    }
}
