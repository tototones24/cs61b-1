import java.util.HashSet;
import java.sql.Timestamp;
import java.io.IOException;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Commit implements Serializable {
    public Commit previous;
    public HashSet<String> files;
    public String message;
    public Timestamp created;
    public int id;

    public void restore() {
        for (String s : files) {
            restoreFile(s);
        }
    }

    public File getFile(String name) {
        Commit c = this;
        if (!files.contains(name)) {
            return null;
        }

        File f = new File(".gitlet/" + c.id + "/" + name);
        while (!f.exists()) {
            c = c.previous;
            f = new File(".gitlet/" + c.id + "/" + name);
        }
        return f;
    }

    public void restoreFile(String name) {
        Commit c = this;
        if (!files.contains(name)) {
            return;
        }

        Path p = getFile(name).toPath();
        Path d = (new File(".")).toPath();
        try {
            Files.copy(p, d.resolve(name), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException io) {
            System.out.println(io);
        }
    }
}
