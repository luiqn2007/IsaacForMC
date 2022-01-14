import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SourceModItems extends BaseSourceGenerator {

    private static final String TEMPLATE =
            "    public static RegistryObject<Item> %1$s = REGISTER.register(\"%2$s\")\n" +
            "        .item(() -> new ItemProp(ModProps.%1$s))\n" +
            "        .getObject();";

    public SourceModItems(Path path) {
        super(path, "item/ModItems.java");
    }

    @Override
    public List<String> getLines(Path file) throws IOException {
        List<String> lines = new ArrayList<>();
        Files.walk(file.getParent().getParent().resolve("prop"))
                .filter(Files::isRegularFile)
                .forEach(path -> buildLine(lines, path));
        return lines;
    }

    private void buildLine(List<String> lines, Path path) {
        String fileName = path.getFileName().toString();
        if (fileName.endsWith(".java") && fileName.startsWith("Prop")) {
            String simpleName = fileName.substring(4, fileName.length() - 5);
            if (simpleName.isEmpty() || "Empty".equals(simpleName)) return;
            String fieldName = StringUtils.toUpperCaseName(simpleName);
            String id = StringUtils.toLowerCaseName(simpleName);
            lines.add(String.format(TEMPLATE, fieldName, id));
        }
    }
}
