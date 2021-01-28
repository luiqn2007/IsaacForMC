package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

import java.util.ArrayList;

public class GroupObject {

    public String name;
    public ArrayList<Face> faces;
    public int glDrawingMode;

    public GroupObject(String name) {
        this(name, -1);
    }

    public GroupObject(String name, int glDrawingMode) {
        faces = new ArrayList<>();
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }

    public void render() {
        if (faces.size() > 0) {
            Tessellator2 tessellator = Tessellator2.instance;
            tessellator.startDrawing(glDrawingMode);
            render(tessellator);
            tessellator.draw();
        }
    }

    public void render(Tessellator2 tessellator) {
        if (faces.size() > 0) {
            faces.forEach(face -> face.addFaceForRender(tessellator));
        }
    }
}
