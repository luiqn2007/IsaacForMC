package lq2007.mcmod.isaacformc.client.util.obj;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import joptsimple.internal.Strings;
import lq2007.mcmod.isaacformc.common.Isaac;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.client.model.obj.LineReader;
import net.minecraftforge.client.model.obj.MaterialLibrary;
import net.minecraftforge.client.model.obj.OBJLoader;
import org.lwjgl.opengl.GL11;

import java.util.*;

import static net.minecraftforge.client.model.obj.OBJModel.*;

public class OBJModel {

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

    public MaterialLibrary mtllib = MaterialLibrary.EMPTY;
    public final Group root = new Group("", null);

    public Entity entityIn;
    public float limbSwing;
    public float limbSwingAmount;
    public float ageInTicks;
    public float netHeadYaw;
    public float headPitch;

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
        MaterialLibrary.Material currentMat = null;
        String currentSmoothingGroup = null;
        Group currentGroup = root;
        Object.Builder currentObject = root.objBuilder;
        Mesh.Builder currentMesh = null;

        // boolean objAboveGroup = false;

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
                        if (currentMesh != null) {
                            currentMesh = currentMesh.materialOrFinish(currentMat);
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
                        currentMesh = new Mesh.Builder()
                                .material(currentMat)
                                .smoothingGroup(currentSmoothingGroup);
                        currentObject.addMesh(currentMesh);
                    }

                    Face.Builder face = currentMesh.addFace(line.length - 1);
                    for(int i = 0; i < face.count; i++) {
                        int[] vertexIndex = Arrays.stream(line[i + 1].split("/"))
                                .mapToInt(num -> Strings.isNullOrEmpty(num) ? 0 : Integer.parseInt(num))
                                .toArray();
                        face.addVertex()
                            .parseIndex(vertexIndex, positions.size(), texCoords.size(), normals.size(), colors.size());
                    }

                    break;
                }
                case "s": {
                    // Smoothing group (starts new mesh)
                    String smoothingGroup = "off".equals(line[1]) ? null : line[1];
                    if (!Objects.equals(currentSmoothingGroup, smoothingGroup)) {
                        currentSmoothingGroup = smoothingGroup;
                        if (currentMesh != null) {
                            currentMesh = currentMesh.smoothingGroupOrFinish(currentSmoothingGroup);
                        }
                    }
                    break;
                }
                case "g": {
                    currentGroup = new Group(line[1], currentGroup);
                    currentObject = currentGroup.objBuilder;
                    currentMesh = null;
                    break;
                }
                case "o": {
                    currentObject = new Object.Builder(line[1], currentGroup);
                    currentMesh = null;
                    break;
                }
                default: {
                    Isaac.LOGGER.warn("Unknown tag: " + line[0] + " at line " + Arrays.toString(line) + " from " + resource);
                }
            }
        }

        root.build(this);
    }

    public void renderAll(ResourceLocation texture) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        render(root, buffer);
        tessellator.draw();
    }

    private void render(Object object, BufferBuilder buffer) {
        object.meshes.forEach(
                mesh -> mesh.faces.forEach(
                        face -> face.vertices.forEach(
                                vertex -> vertex.add(buffer))));
    }

    private void render(Group group, BufferBuilder buffer) {
        render(group.getObject(), buffer);
        group.getObjects().forEach((name, o) -> render(o, buffer));
        group.getGroups().forEach((name, g) -> render(g, buffer));
    }

    public Vector3f getPosition(int index) {
        return positions.get(index);
    }

    public Vector2f getTexCoord(int index) {
        return texCoords.get(index);
    }

    public Vector3f getNormal(int index) {
        return normals.get(index);
    }

    public Vector4f getColor(int index) {
        return colors.get(index);
    }

    public void setRotationAngles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.entityIn = entityIn;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
    }
}
