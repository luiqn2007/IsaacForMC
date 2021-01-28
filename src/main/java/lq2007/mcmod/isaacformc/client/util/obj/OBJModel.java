package lq2007.mcmod.isaacformc.client.util.obj;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import joptsimple.internal.Strings;
import lq2007.mcmod.isaacformc.common.Isaac;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.client.model.obj.LineReader;
import net.minecraftforge.client.model.obj.MaterialLibrary;
import net.minecraftforge.client.model.obj.OBJLoader;

import java.util.*;

import static net.minecraftforge.client.model.obj.OBJModel.*;

public class OBJModel extends Group {

    private static final Map<ResourceLocation, OBJModel> RESOURCE_CACHES = new HashMap<>();
    public static final ResourceLocation FROM_LINES = new ResourceLocation(Isaac.ID, "__from_lines");

    public static OBJModel read(ResourceLocation resource) {
        return RESOURCE_CACHES.computeIfAbsent(resource, OBJModel::new);
    }

    public final ResourceLocation resource;
    private final List<String[]> lines;

    private final List<Vector3f> positions = Lists.newArrayList();
    private final List<Vector2f> texCoords = Lists.newArrayList();
    private final List<Vector3f> normals = Lists.newArrayList();
    private final List<Vector4f> colors = Lists.newArrayList();

    public MaterialLibrary mtllib;

    private OBJModel(ResourceLocation resource) {
        this.resource = resource;
        this.lines = new ArrayList<>();
        try {
            read();
            parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OBJModel(List<String[]> lines) {
        this.resource = FROM_LINES;
        this.lines = ImmutableList.copyOf(lines);
        try {
            parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read() throws Exception {
        IResource resource = Minecraft.getInstance().getResourceManager().getResource(this.resource);
        try(LineReader reader = new LineReader(resource)) {
            String[] line;
            while ((line = reader.readAndSplitLine(true)) != null) {
                lines.add(line);
            }
        }
    }

    private void parse() {
        MaterialLibrary mtllib = MaterialLibrary.EMPTY;
        MaterialLibrary.Material currentMat = null;
        String currentSmoothingGroup = null;
        ModelObject currentObject = null;
        ModelMesh currentMesh = null;

        boolean objAboveGroup = false;

        for (String[] line : lines) {
            switch (line[0]) {
                case "mtllib": {
                    // Loads material library
                    String lib = line[1];
                    ResourceLocation location = lib.contains(":")
                            ? new ResourceLocation(lib)
                            : new ResourceLocation(resource.getNamespace(), resource.getPath() + lib);
                    mtllib = OBJLoader.INSTANCE.loadMaterialLibrary(location);
                    break;
                }
                case "usemtl": {
                    // Sets the current material (starts new mesh)
                    String mat = Strings.join(Arrays.copyOfRange(line, 1, line.length), " ");
                    MaterialLibrary.Material newMat = mtllib.getMaterial(mat);
                    if (!Objects.equals(newMat, currentMat)) {
                        currentMat = newMat;
                        if (currentMesh != null && currentMesh.mat == null && currentMesh.faces.size() == 0) {
                            currentMesh.mat = currentMat;
                        } else {
                            // Start new mesh
                            currentMesh = null;
                        }
                    }
                    break;
                }
                case "v": {
                    // Vertex
                    positions.add(parseVector4To3(line));
                    break;
                }
                case "vt": {
                    // Vertex texcoord
                    texCoords.add(parseVector2(line));
                    break;
                }
                case "vn": {
                    // Vertex normal
                    normals.add(parseVector3(line));
                    break;
                }
                case "vc": {
                    // Vertex color (non-standard)
                    colors.add(parseVector4(line));
                    break;
                }
                case "f": {
                    // Face
                    if (currentMesh == null) {
                        currentMesh = new ModelMesh(currentMat, currentSmoothingGroup);
                        if (currentObject != null) {
                            currentObject.meshes.add(currentMesh);
                        } else if (currentGroup == null) {
                            currentGroup = new ModelObject("");
                            parts.put("", currentGroup);
                            currentGroup.meshes.add(currentMesh);
                        } else {
                            currentGroup.meshes.add(currentMesh);
                        }
                    }

                    int[][] vertices = new int[line.length - 1][];
                    for(int i = 0; i < vertices.length; i++)
                    {
                        String vertexData = line[i+1];
                        String[] vertexParts = vertexData.split("/");
                        int[] vertex = Arrays.stream(vertexParts)
                                .mapToInt(num -> Strings.isNullOrEmpty(num) ? 0 : Integer.parseInt(num))
                                .toArray();
                        vertex[0] = vertex[0] < 0 ? positions.size() + vertex[0] : vertex[0] - 1;
                        if (vertex.length > 1) {
                            vertex[1] = vertex[1] < 0 ? texCoords.size() + vertex[1] : vertex[1] - 1;
                            if (vertex.length > 2) {
                                vertex[2] = vertex[2] < 0 ? normals.size() + vertex[2] : vertex[2] - 1;
                                if (vertex.length > 3) {
                                    vertex[3] = vertex[3] < 0 ? colors.size() + vertex[3] : vertex[3] - 1;
                                }
                            }
                        }
                        vertices[i] = vertex;
                    }

                    currentMesh.faces.add(vertices);
                    break;
                }
                case "s": {
                    // Smoothing group (starts new mesh)
                    String smoothingGroup = "off".equals(line[1]) ? null : line[1];
                    if (!Objects.equals(currentSmoothingGroup, smoothingGroup)) {
                        currentSmoothingGroup = smoothingGroup;
                        if (currentMesh != null && currentMesh.smoothingGroup == null && currentMesh.faces.size() == 0) {
                            currentMesh.smoothingGroup = currentSmoothingGroup;
                        } else {
                            // Start new mesh
                            currentMesh = null;
                        }
                    }
                    break;
                }
                case "g": {
                    String name = line[1];
                    if (objAboveGroup) {
                        currentObject = new ModelObject(currentGroup.name + "/" + name);
                        currentGroup.parts.put(name, currentObject);
                    } else {
                        currentGroup = new ModelGroup(name);
                        parts.put(name, currentGroup);
                        currentObject = null;
                    }
                    // Start new mesh
                    currentMesh = null;
                    break;
                }
                case "o": {
                    String name = line[1];
                    if (objAboveGroup || currentGroup == null) {
                        objAboveGroup = true;

                        currentGroup = new ModelGroup(name);
                        parts.put(name, currentGroup);
                        currentObject = null;
                    } else {
                        currentObject = new ModelObject(currentGroup.name + "/" + name);
                        currentGroup.parts.put(name, currentObject);
                    }
                    // Start new mesh
                    currentMesh = null;
                    break;
                }

                default: {
                    throw new RuntimeException("Unknown tag: " + line[0] + " at line " + Arrays.toString(line));
                }
            }
        }
    }

    public void render(String name) {
        parts.computeIfPresent(name, (n, group) -> {
            group.parts.forEach(this::render);
            return group;
        });
    }

    public void renderAll() {

    }

    private void render(Object object) {

    }

}
