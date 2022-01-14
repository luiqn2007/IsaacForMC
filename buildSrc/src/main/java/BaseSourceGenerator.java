import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

public abstract class BaseSourceGenerator {

    private static final String REPLACE_S = "// source generate";
    private static final String REPLACE_B = "// ===> generated source begin";
    private static final String REPLACE_E = "// ===> generated source end";

    private final Path path;
    private final String subFile;

    public BaseSourceGenerator(Path path, String subFile) {
        this.path = path;
        this.subFile = subFile;
    }

    public abstract List<String> getLines(Path file) throws IOException;

    public void writeToFile() throws IOException {
        Path filePath = path.resolve(subFile);
        if (!Files.isRegularFile(filePath)) {
            throw new FileNotFoundException(filePath.toAbsolutePath().toString());
        }
        boolean isReplaced = false;
        boolean skipBegin = false;
        Path newFilePath = filePath.getParent().resolve(filePath.getFileName().toString() + ".gen");
        Path bakFilePath = filePath.getParent().resolve(filePath.getFileName().toString() + ".bak");
        BufferedWriter writer = Files.newBufferedWriter(newFilePath, StandardOpenOption.CREATE_NEW);
        for (String line : Files.readAllLines(filePath)) {
            if (REPLACE_E.equals(line)) {
                skipBegin = false;
                continue;
            }
            if (skipBegin) {
                continue;
            }
            if (REPLACE_B.equals(line)) {
                skipBegin = true;
                continue;
            }
            writer.write(line + "\n");
            if (!isReplaced && REPLACE_S.equals(line.trim())) {
                writer.write(REPLACE_B + "\n");
                isReplaced = true;
                for (String s : getLines(filePath)) {
                    writer.write(s + "\n");
                }
                writer.write(REPLACE_E + "\n");
            }
        }
        writer.flush();
        writer.close();
        Files.move(filePath, bakFilePath, StandardCopyOption.REPLACE_EXISTING);
        Files.move(newFilePath, filePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
