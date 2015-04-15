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
            for (String s : commitDir.list()){
                if (!filesLeftToCopy.contains(s)){ 
                    continue;
                }

                Path p = (new File(s)).toPath();
                try {
                    Files.copy(p,thisDir.toPath().resolve(p), StandardCopyOption.REPLACE_EXISTING);
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

    public void getFile(String name){
        Commit c = this;
        if (!files.contains(name)){
            return;
        }
        
        File f = new File("./.gitlet/" + c.id + "/" + name);
        while (!f.exists()) {
            c = c.previous;
            f = new File("./.gitlet/" + c.id + "/" + name);
        }
        Path p = f.toPath();
        Path d = (new File(".")).toPath();
        try {
            Files.copy(p,d.resolve(p), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException io) {
            System.out.println(io);
        }
    }
}
