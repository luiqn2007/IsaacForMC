import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Path;

public class SourceGenerateTask extends DefaultTask {

    @TaskAction
    public void doTask() {
        Path path = getProject().getProjectDir().toPath().resolve("src/main/java/lq2007/mcmod/isaacmod");
        write(new SourceModProps(path));
        write(new SourceModItems(path));
    }

    private void write(BaseSourceGenerator generator) {
        try {
            generator.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
