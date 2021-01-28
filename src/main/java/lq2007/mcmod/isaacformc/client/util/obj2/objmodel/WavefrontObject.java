package lq2007.mcmod.isaacformc.client.util.obj2.objmodel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class WavefrontObject implements IModelCustom {

    private static final Pattern VertexPattern = Pattern.compile("(v( (-)?\\d+\\.\\d+){3,4} *\\n)|(v( (-)?\\d+\\.\\d+){3,4} *$)");
    private static final Pattern VertexNormalPattern = Pattern.compile("(vn( (-)?\\d+\\.\\d+){3,4} *\\n)|(vn( (-)?\\d+\\.\\d+){3,4} *$)");
    private static final Pattern textureCoordinatePattern = Pattern.compile("(vt( (-)?\\d+\\.\\d+){2,3} *\\n)|(vt( (-)?\\d+\\.\\d+){2,3} *$)");
    private static final Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
    private static final Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
    private static final Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
    private static final Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
    private static final Pattern groupObjectPattern = Pattern.compile("([go]( [\\w\\d]+) *\\n)|([go]( [\\w\\d]+) *$)");
    private static Matcher VertexMatcher;
    private static Matcher FaceNormalMatcher;
    private static Matcher textureCoordinateMatcher;
    private static Matcher face_V_VT_VN_Matcher;
    private static Matcher face_V_VT_Matcher;
    private static Matcher face_V_VN_Matcher;
    private static Matcher face_V_Matcher;
    private static Matcher groupObjectMatcher;
    public ArrayList<Vector3f> vertices;
    public ArrayList<Vector3f> Vector3fNormals;
    public ArrayList<TextureCoordinate> textureCoordinates;
    public ArrayList<GroupObject> groupObjects;
    private GroupObject currentGroupObject;
    private final String fileName;

    public WavefrontObject(ResourceLocation resource) throws ModelFormatException {
        vertices = new ArrayList<>();
        Vector3fNormals = new ArrayList<>();
        textureCoordinates = new ArrayList<>();
        groupObjects = new ArrayList<>();
        fileName = resource.toString();

        try {
            IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
            loadObjModel(res.getInputStream());
        } catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format", e);
        }
    }

    private void loadObjModel(InputStream inputStream) throws ModelFormatException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String currentLine;
            int lineCount = 0;
            while ((currentLine = reader.readLine()) != null) {
                lineCount++;
                currentLine = currentLine.replaceAll("\\s+", " ").trim();
                if (!currentLine.startsWith("#") && currentLine.length() != 0)
                    if (currentLine.startsWith("v ")) {
                        Vector3f vertex = parseVertex(currentLine, lineCount);
                        if (vertex != null) vertices.add(vertex);
                    } else if (currentLine.startsWith("vn ")) {
                        Vector3f Vector3f = parseVertexNormal(currentLine, lineCount);
                        if (Vector3f != null) Vector3fNormals.add(Vector3f);
                    } else if (currentLine.startsWith("vt ")) {
                        TextureCoordinate textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
                        if (textureCoordinate != null) textureCoordinates.add(textureCoordinate);
                    } else if (currentLine.startsWith("f ")) {
                        if (currentGroupObject == null) currentGroupObject = new GroupObject("Default");
                        currentGroupObject.faces.add(parseFace(currentLine, lineCount));
                    } else if (currentLine.startsWith("g ") | currentLine.startsWith("o ")) {
                        GroupObject group = parseGroupObject(currentLine, lineCount);
                        if (group != null && currentGroupObject != null)
                            groupObjects.add(currentGroupObject);
                        currentGroupObject = group;
                    }
            }
            groupObjects.add(currentGroupObject);
        } catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format", e);
        }
    }

    @Override
    public void renderAll() {
        Tessellator2 tessellator = Tessellator2.instance;
        tessellator.startDrawing(currentGroupObject == null ? 4 : currentGroupObject.glDrawingMode);
        tessellateAll(tessellator);
        tessellator.draw();
    }

    public void tessellateAll(Tessellator2 tessellator) {
        groupObjects.forEach(object -> object.render(tessellator));
    }

    @Override
    public void renderOnly(String[] groupNames) {
        for (GroupObject groupObject : groupObjects) {
            for (String name : groupNames) {
                if (name.equalsIgnoreCase(groupObject.name)) {
                    groupObject.render();
                }
            }
        }
    }

    @Override
    public void renderPart(String partName) {
        groupObjects.stream()
                .filter(obj -> obj.name.equalsIgnoreCase(partName))
                .forEach(GroupObject::render);
    }

    @Override
    public void renderAllExcept(String[] excludedGroupNames) {
        for (GroupObject groupObject : groupObjects) {
            boolean skipPart = false;
            for (String excludedGroupName : excludedGroupNames) {
                if (excludedGroupName.equalsIgnoreCase(groupObject.name)) {
                    skipPart = true;
                    break;
                }
            }

            if (!skipPart) groupObject.render();
        }
    }

    private Vector3f parseVertex(String line, int lineCount) throws ModelFormatException {
        if (!isValidVertexLine(line))
            throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        line = line.substring(line.indexOf(" ") + 1);
        String[] tokens = line.split(" ");
        try {
            if (tokens.length == 2)
                return new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), 0);
            return tokens.length == 3 ? new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : null;
        } catch (NumberFormatException e) {
            throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
        }
    }

    private Vector3f parseVertexNormal(String line, int lineCount)
            throws ModelFormatException {
        if (!isValidVector3fNormalLine(line))
            throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        line = line.substring(line.indexOf(" ") + 1);
        String[] tokens = line.split(" ");
        try {
            return tokens.length == 3 ? new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : null;
        } catch (NumberFormatException e) {
            throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
        }
    }

    private TextureCoordinate parseTextureCoordinate(String line, int lineCount)
            throws ModelFormatException {
        if (!isValidTextureCoordinateLine(line))
            throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        line = line.substring(line.indexOf(" ") + 1);
        String[] tokens = line.split(" ");

        try {
            if (tokens.length == 2)
                return new TextureCoordinate(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]));
            return tokens.length == 3 ? new TextureCoordinate(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])) : null;
        } catch (NumberFormatException e) {
            throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
        }
    }

    private Face parseFace(String line, int lineCount) throws ModelFormatException {
        Face face;
        if (isValidFaceLine(line)) {
            face = new Face();
            String trimmedLine = line.substring(line.indexOf(" ") + 1);
            String[] tokens = trimmedLine.split(" ");
            String[] subTokens;
            if (tokens.length == 3) {
                if (currentGroupObject.glDrawingMode == -1)
                    currentGroupObject.glDrawingMode = 4;
                else if (currentGroupObject.glDrawingMode != 4)
                    throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + fileName + "' - Invalid number of points for face (expected 4, found " + tokens.length + ")");
            } else if (tokens.length == 4)
                if (currentGroupObject.glDrawingMode == -1)
                    currentGroupObject.glDrawingMode = 7;
                else if (currentGroupObject.glDrawingMode != 7)
                    throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + fileName + "' - Invalid number of points for face (expected 3, found " + tokens.length + ")");
            if (isValidFace_V_VT_VN_Line(line)) {
                face.vertices = new Vector3f[tokens.length];
                face.textureCoordinates = new TextureCoordinate[tokens.length];
                face.vertexNormals = new Vector3f[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    subTokens = tokens[i].split("/");
                    face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.textureCoordinates[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                    face.vertexNormals[i] = Vector3fNormals.get(Integer.parseInt(subTokens[2]) - 1);
                }

                face.faceNormal = face.calculateFaceNormal();
            } else if (isValidFace_V_VT_Line(line)) {
                face.vertices = new Vector3f[tokens.length];
                face.textureCoordinates = new TextureCoordinate[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    subTokens = tokens[i].split("/");
                    face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.textureCoordinates[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                }

                face.faceNormal = face.calculateFaceNormal();
            } else if (isValidFace_V_VN_Line(line)) {
                face.vertices = new Vector3f[tokens.length];
                face.vertexNormals = new Vector3f[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    subTokens = tokens[i].split("//");
                    face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.vertexNormals[i] = Vector3fNormals.get(Integer.parseInt(subTokens[1]) - 1);
                }

                face.faceNormal = face.calculateFaceNormal();
            } else if (isValidFace_V_Line(line)) {
                face.vertices = new Vector3f[tokens.length];
                for (int i = 0; i < tokens.length; i++)
                    face.vertices[i] = vertices.get(Integer.parseInt(tokens[i]) - 1);

                face.faceNormal = face.calculateFaceNormal();
            } else {
                throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
            }
        } else {
            throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }
        return face;
    }

    private GroupObject parseGroupObject(String line, int lineCount) throws ModelFormatException {
        GroupObject group = null;
        if (isValidGroupObjectLine(line)) {
            String trimmedLine = line.substring(line.indexOf(" ") + 1);
            if (trimmedLine.length() > 0)
                group = new GroupObject(trimmedLine);
        } else {
            throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }
        return group;
    }

    private static boolean isValidVertexLine(String line) {
        if (VertexMatcher != null)
            VertexMatcher.reset();
        VertexMatcher = VertexPattern.matcher(line);
        return VertexMatcher.matches();
    }

    private static boolean isValidVector3fNormalLine(String line) {
        if (FaceNormalMatcher != null)
            FaceNormalMatcher.reset();
        FaceNormalMatcher = VertexNormalPattern.matcher(line);
        return FaceNormalMatcher.matches();
    }

    private static boolean isValidTextureCoordinateLine(String line) {
        if (textureCoordinateMatcher != null)
            textureCoordinateMatcher.reset();
        textureCoordinateMatcher = textureCoordinatePattern.matcher(line);
        return textureCoordinateMatcher.matches();
    }

    private static boolean isValidFace_V_VT_VN_Line(String line) {
        if (face_V_VT_VN_Matcher != null)
            face_V_VT_VN_Matcher.reset();
        face_V_VT_VN_Matcher = face_V_VT_VN_Pattern.matcher(line);
        return face_V_VT_VN_Matcher.matches();
    }

    private static boolean isValidFace_V_VT_Line(String line) {
        if (face_V_VT_Matcher != null)
            face_V_VT_Matcher.reset();
        face_V_VT_Matcher = face_V_VT_Pattern.matcher(line);
        return face_V_VT_Matcher.matches();
    }

    private static boolean isValidFace_V_VN_Line(String line) {
        if (face_V_VN_Matcher != null)
            face_V_VN_Matcher.reset();
        face_V_VN_Matcher = face_V_VN_Pattern.matcher(line);
        return face_V_VN_Matcher.matches();
    }

    private static boolean isValidFace_V_Line(String line) {
        if (face_V_Matcher != null)
            face_V_Matcher.reset();
        face_V_Matcher = face_V_Pattern.matcher(line);
        return face_V_Matcher.matches();
    }

    private static boolean isValidFaceLine(String line) {
        return isValidFace_V_VT_VN_Line(line)
                || isValidFace_V_VT_Line(line) 
                || isValidFace_V_VN_Line(line) 
                || isValidFace_V_Line(line);
    }

    private static boolean isValidGroupObjectLine(String line) {
        if (groupObjectMatcher != null)
            groupObjectMatcher.reset();
        groupObjectMatcher = groupObjectPattern.matcher(line);
        return groupObjectMatcher.matches();
    }

    @Override
    public String getType() {
        return "obj";
    }
}
