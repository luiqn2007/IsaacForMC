package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

public class TessellatorVertexState {

    private final int[] rawBuffer;
    private final int rawBufferIndex;
    private final int vertexCount;
    private final boolean hasTexture;
    private final boolean hasBrightness;
    private final boolean hasNormals;
    private final boolean hasColor;

    public TessellatorVertexState(int[] rawBuffer, int rawBufferIndex, int vertexCount,
                                  boolean hasTexture, boolean hasBrightness, boolean hasNormals, boolean hasColor) {
        this.rawBuffer = rawBuffer;
        this.rawBufferIndex = rawBufferIndex;
        this.vertexCount = vertexCount;
        this.hasTexture = hasTexture;
        this.hasBrightness = hasBrightness;
        this.hasNormals = hasNormals;
        this.hasColor = hasColor;
    }

    public int[] getRawBuffer() {
        return rawBuffer;
    }

    public int getRawBufferIndex() {
        return rawBufferIndex;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public boolean getHasTexture() {
        return hasTexture;
    }

    public boolean getHasBrightness() {
        return hasBrightness;
    }

    public boolean getHasNormals() {
        return hasNormals;
    }

    public boolean getHasColor() {
        return hasColor;
    }
}
