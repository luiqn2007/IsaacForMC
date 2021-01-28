package lq2007.mcmod.isaacformc.client.util.obj;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

public class Vertex {

    public final Vector3f position, normal;
    public final Vector4f color;
    public final Vector2f texCoord;

    public Vertex(Vector3f position, Vector4f color, Vector3f normal, Vector2f texCoord) {
        this.position = position;
        this.color = color;
        this.normal = normal;
        this.texCoord = texCoord;
    }

    public void add(BufferBuilder buffer) {
        buffer.pos(color.getX(), color.getY(), color.getZ())
                .normal(color.getX(), color.getY(), color.getZ())
                .color(color.getX(), color.getY(), color.getZ(), color.getW())
                .tex(color.getX(), color.getY())
                .endVertex();
    }

    public static class Builder {
        private int position = 0;
        private int color = 0;
        private int normal = 0;
        private int texCoord = 0;

        public void position(int idx) {
            position = idx;
        }

        public void color(int idx) {
            color = idx;
        }

        public void normal(int idx) {
            normal = idx;
        }

        public void texCoord(int idx) {
            texCoord = idx;
        }

        public Vertex build(OBJModel model) {
            return new Vertex(model.getPosition(position),
                    model.getColor(color),
                    model.getNormal(normal),
                    model.getTexCoord(texCoord));
        }

        public void parseIndex(int[] vertex, int positionCount, int texCoordCount, int normalCount, int colorCount) {
            switch (vertex.length) {
                case 4: color(vertex[3] < 0 ? colorCount + vertex[3] : vertex[3] - 1);
                case 3: normal(vertex[2] < 0 ? normalCount + vertex[2] : vertex[2] - 1);
                case 2: texCoord(vertex[1] < 0 ? texCoordCount + vertex[1] : vertex[1] - 1);
                case 1: position(vertex[0] < 0 ? positionCount + vertex[0] : vertex[0] - 1);
            }
        }
    }
}
