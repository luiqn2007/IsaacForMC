package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.nio.*;
import java.util.Arrays;
import java.util.PriorityQueue;

@SideOnly(Side.CLIENT)
public class Tessellator2 {

    private static final int nativeBufferSize = 0x200000;
    public static boolean renderingWorldRenderer = false;

    private int rawBufferSize = 0;
    private static final ByteBuffer byteBuffer = GLAllocation.createDirectByteBuffer(nativeBufferSize * 4);
    private static final IntBuffer intBuffer = byteBuffer.asIntBuffer();
    private static final FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
    private static final ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
    private int[] rawBuffer;
    private int vertexCount;
    private double textureU;
    private double textureV;
    private int color;
    private boolean hasColor;
    private boolean hasTexture;
    private boolean hasBrightness;
    private boolean hasNormals;
    private int rawBufferIndex;
    private boolean isColorDisabled;
    private int drawMode;
    private int normal;
    public static final Tessellator2 instance = new Tessellator2();
    private boolean isDrawing;

    public void draw() {
        if (!isDrawing) throw new IllegalStateException("Not tesselating!");
        isDrawing = false;
        int offs = 0;
        while (offs < vertexCount) {
            int vtc = Math.min(vertexCount - offs, nativeBufferSize >> 5);
            intBuffer.clear();
            intBuffer.put(rawBuffer, offs * 8, vtc * 8);
            byteBuffer.position(0);
            byteBuffer.limit(vtc * 32);
            offs += vtc;
            if (hasTexture) {
                floatBuffer.position(3);
                GL11.glTexCoordPointer(2, 32, floatBuffer);
                GL11.glEnableClientState(32888);
            }
            if (hasBrightness) {
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                shortBuffer.position(14);
                GL11.glTexCoordPointer(2, 32, shortBuffer);
                GL11.glEnableClientState(32888);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            }
            if (hasColor) {
                byteBuffer.position(20);
                GL11.glColorPointer(4, true, 32, byteBuffer);
                GL11.glEnableClientState(32886);
            }
            if (hasNormals) {
                byteBuffer.position(24);
                GL11.glNormalPointer(32, byteBuffer);
                GL11.glEnableClientState(32885);
            }
            floatBuffer.position(0);
            GL11.glVertexPointer(3, 32, floatBuffer);
            GL11.glEnableClientState(32884);
            GL11.glDrawArrays(drawMode, 0, vtc);
            GL11.glDisableClientState(32884);
            if (hasTexture)
                GL11.glDisableClientState(32888);
            if (hasBrightness) {
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                GL11.glDisableClientState(32888);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            }
            if (hasColor)
                GL11.glDisableClientState(32886);
            if (hasNormals)
                GL11.glDisableClientState(32885);
        }
        if (rawBufferSize > 0x20000 && rawBufferIndex < rawBufferSize << 3) {
            rawBufferSize = 0x10000;
            rawBuffer = new int[rawBufferSize];
        }
        reset();
    }

    public TessellatorVertexState getVertexState(float x, float y, float z) {
        int[] newArray = new int[rawBufferIndex];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(rawBufferIndex, new QuadComparator(rawBuffer, x, y, z));
        for (int i = 0; i < rawBufferIndex; i += 32)
            priorityQueue.add(i);

        for (int i = 0; !priorityQueue.isEmpty(); i += 32) {
            int j = priorityQueue.remove();
            System.arraycopy(rawBuffer, j, newArray, i, 32);
        }

        System.arraycopy(newArray, 0, rawBuffer, 0, newArray.length);
        return new TessellatorVertexState(newArray, rawBufferIndex, vertexCount, hasTexture, hasBrightness, hasNormals, hasColor);
    }

    public void setVertexState(TessellatorVertexState state) {
        while (state.getRawBuffer().length > rawBufferSize && rawBufferSize > 0) {
            rawBufferSize <<= 1;
        }
        if (rawBufferSize > rawBuffer.length) rawBuffer = new int[rawBufferSize];
        System.arraycopy(state.getRawBuffer(), 0, rawBuffer, 0, state.getRawBuffer().length);
        rawBufferIndex = state.getRawBufferIndex();
        vertexCount = state.getVertexCount();
        hasTexture = state.getHasTexture();
        hasBrightness = state.getHasBrightness();
        hasColor = state.getHasColor();
        hasNormals = state.getHasNormals();
    }

    private void reset() {
        vertexCount = 0;
        byteBuffer.clear();
        rawBufferIndex = 0;
    }

    public void startDrawingQuads() {
        startDrawing(7);
    }

    public void startDrawing(int mode) {
        if (!isDrawing) {
            isDrawing = true;
            reset();
            drawMode = mode;
            hasNormals = false;
            hasColor = false;
            hasTexture = false;
            hasBrightness = false;
            isColorDisabled = false;
        } else {
            throw new IllegalStateException("Already tessellating!");
        }
    }

    public void setTextureUV(double texU, double texV) {
        hasTexture = true;
        textureU = texU;
        textureV = texV;
    }

    public void setColorOpaque(int p_78376_1_, int p_78376_2_, int p_78376_3_) {
        setColorRGBA(p_78376_1_, p_78376_2_, p_78376_3_, 255);
    }

    public void setColorRGBA(int red, int green, int blue, int alpha) {
        if (!isColorDisabled) {
            if (red > 255) red = 255;
            if (green > 255) green = 255;
            if (blue > 255) blue = 255;
            if (alpha > 255) alpha = 255;
            if (red < 0) red = 0;
            if (green < 0) green = 0;
            if (blue < 0) blue = 0;
            if (alpha < 0) alpha = 0;
            hasColor = true;
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
                color = alpha << 24 | blue << 16 | green << 8 | red;
            else
                color = red << 24 | green << 16 | blue << 8 | alpha;
        }
    }

    public void setColorOpaque(byte red, byte green, byte blue) {
        setColorOpaque(red & 0xff, green & 0xff, blue & 0xff);
    }

    public void addVertexWithUV(double x, double y, double z, double u, double v) {
        setTextureUV(u, v);
        addVertex(x, y, z);
    }

    public void addVertex(double x, double y, double z) {
        if (rawBufferIndex >= rawBufferSize - 32)
            if (rawBufferSize == 0) {
                rawBufferSize = 0x10000;
                rawBuffer = new int[rawBufferSize];
            } else {
                rawBufferSize *= 2;
                rawBuffer = Arrays.copyOf(rawBuffer, rawBufferSize);
            }
        if (hasTexture) {
            rawBuffer[rawBufferIndex + 3] = Float.floatToRawIntBits((float) textureU);
            rawBuffer[rawBufferIndex + 4] = Float.floatToRawIntBits((float) textureV);
        }
        if (hasBrightness)
            rawBuffer[rawBufferIndex + 7] = 0;
        if (hasColor)
            rawBuffer[rawBufferIndex + 5] = color;
        if (hasNormals)
            rawBuffer[rawBufferIndex + 6] = normal;
        rawBuffer[rawBufferIndex] = Float.floatToRawIntBits((float) x);
        rawBuffer[rawBufferIndex + 1] = Float.floatToRawIntBits((float) y);
        rawBuffer[rawBufferIndex + 2] = Float.floatToRawIntBits((float) z);
        rawBufferIndex += 8;
        vertexCount++;
    }

    public void setNormal(float p_78375_1_, float p_78375_2_, float p_78375_3_) {
        hasNormals = true;
        byte b0 = (byte) (int) (p_78375_1_ * 127F);
        byte b1 = (byte) (int) (p_78375_2_ * 127F);
        byte b2 = (byte) (int) (p_78375_3_ * 127F);
        normal = b0 & 0xff | (b1 & 0xff) << 8 | (b2 & 0xff) << 16;
    }
}
