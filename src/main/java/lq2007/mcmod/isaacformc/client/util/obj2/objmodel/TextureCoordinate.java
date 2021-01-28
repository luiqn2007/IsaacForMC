package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

public class TextureCoordinate {

    public float u;
    public float v;
    public float w;

    public TextureCoordinate(float u, float v) {
        this(u, v, 0.0F);
    }

    public TextureCoordinate(float u, float v, float w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}
