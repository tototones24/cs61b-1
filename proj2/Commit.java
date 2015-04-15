import java.util.*;
import java.sql.Timestamp;
import java.io.*;
import java.nio.file.*;

public class Commit implements Serializable {
    public Commit previous;
    public HashSet<String> files;
    public String message;
    public Timestamp created;
    public int id;

    public void restore(){
        Commit c = this;
        File thisDir = new File(".");
        for (File f : thisDir.listFiles()) {
            if (!f.toString().equals(".gitlet")) {
                f.delete();
            }
        }

        HashSet filesLeftToCopy = new HashSet(files);

        while (c != null) {
            File commitDir = new File(".gitlet/" + c.id);
            for (File f : commitDir.listFiles()){
                Path p = f.toPath();
                if (!filesLeftToCopy.contains(p.getFileName())) {
                    System.out.println(p.getFileName());
                    continue;
                }
                try {
                    Files.copy(p,thisDir.toPath().resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException io) {
                    System.out.println(io);
                }
                filesLeftToCopy.remove(p.getFileName());
            }
            if (filesLeftToCopy.size() == 0) {
                break;
            }
            c = c.previous;
        }
    }
}
