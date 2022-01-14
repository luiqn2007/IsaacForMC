import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SourceModProps extends BaseSourceGenerator {

    public SourceModProps(Path path) {
        super(path, "prop/ModProps.java");
    }

    @Override
    public List<String> getLines(Path file) throws IOException {
        List<String> lines = new ArrayList<>();
        Files.walk(file.getParent())
                .filter(Files::isRegularFile)
                .forEach(path -> buildLine(lines, path));
        return lines;
    }

    private void buildLine(List<String> lines, Path path) {
        String fileName = path.getFileName().toString();
        if (fileName.endsWith(".java") && fileName.startsWith("Prop")) {
            String simpleName = fileName.substring(0, fileName.length() - 5);
            String fieldName = StringUtils.toUpperCaseName(simpleName.substring(4));
            if (StringUtils.isNullOrEmpty(fieldName)) return;
            lines.add("    public static final " + simpleName + " " + fieldName + " = " + "new " + simpleName + "();");
        }
    }
}
