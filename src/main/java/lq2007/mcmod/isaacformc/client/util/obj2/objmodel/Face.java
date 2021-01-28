package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

import org.lwjgl.util.vector.Vector3f;

public class Face {

    public Vector3f[] vertices;
    public Vector3f[] vertexNormals;
    public Vector3f faceNormal;
    public TextureCoordinate[] textureCoordinates;

    public void addFaceForRender(Tessellator2 tessellator) {
        if (faceNormal == null)
            faceNormal = calculateFaceNormal();
        tessellator.setNormal(faceNormal.x, faceNormal.y, faceNormal.z);
        float averageU = 0.0F;
        float averageV = 0.0F;
        float textureOffset = 0.0005F;
        if (textureCoordinates != null && textureCoordinates.length > 0) {
            for (TextureCoordinate textureCoordinate : textureCoordinates) {
                averageU += textureCoordinate.u;
                averageV += textureCoordinate.v;
            }

            averageU /= textureCoordinates.length;
            averageV /= textureCoordinates.length;
        }
        for (int i = 0; i < vertices.length; i++) {
            if (textureCoordinates != null && textureCoordinates.length > 0) {
                float offsetU = textureOffset;
                float offsetV = textureOffset;
                if (textureCoordinates[i].u > averageU)
                    offsetU = -offsetU;
                if (textureCoordinates[i].v > averageV)
                    offsetV = -offsetV;
                tessellator.addVertexWithUV(vertices[i].x, vertices[i].y, vertices[i].z, textureCoordinates[i].u + offsetU, textureCoordinates[i].v + offsetV);
            } else {
                tessellator.addVertex(vertices[i].x, vertices[i].y, vertices[i].z);
                for (Vector3f vertex : vertices) {
                    if (textureCoordinates == null || textureCoordinates.length <= 0) {
                        tessellator.addVertex(vertex.x, vertex.y, vertex.z);
                    }
                }
            }
        }
    }

    public Vector3f calculateFaceNormal() {
        Vector3f.sub(vertices[1], vertices[0], new Vector3f());
        Vector3f v1 = Vector3f.sub(vertices[1], vertices[0], new Vector3f());
        Vector3f v2 = Vector3f.sub(vertices[2], vertices[0], new Vector3f());
        Vector3f normal = Vector3f.cross(v1, v2, new Vector3f());
        return normal.length() == 0 ? new Vector3f() : (Vector3f) normal.normalise();
    }
}
