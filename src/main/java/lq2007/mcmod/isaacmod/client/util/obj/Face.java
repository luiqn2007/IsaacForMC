package lq2007.mcmod.isaacmod.client.util.obj;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;

public class Face {

    public final ImmutableList<Vertex> vertices;

    public Face(ImmutableList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public static class Builder {

        public final int count;
        private Vertex.Builder[] vertices;
        private int ptr = 0;

        public Builder(int count) {
            this.count = count;
            this.vertices = new Vertex.Builder[count];
        }

        public Vertex.Builder addVertex() {
            Vertex.Builder vertex = new Vertex.Builder();
            if (vertices.length > ptr) {
                vertices[ptr++] = vertex;
            } else {
                vertices = ArrayUtils.add(vertices, vertex);
                ptr++;
            }
            return vertex;
        }

        public Face build(OBJModel model) {
            Vertex[] vertices = new Vertex[this.vertices.length];
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = this.vertices[i].build(model);
            }
            return new Face(ImmutableList.copyOf(vertices));
        }
    }
}
