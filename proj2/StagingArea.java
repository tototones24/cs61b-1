import java.util.*;
import java.io.*;
public class StagingArea implements Serializable {
    public HashSet<String> removedFiles;
    public HashSet<String> stagedFiles;

    public StagingArea(){
        removedFiles = new HashSet<String>();
        stagedFiles = new HashSet<String>();
    }

    public void add(String fileName, Commit head) {
        if (removedFiles.contains(fileName)) {
            removedFiles.remove(fileName);
        } else {
            if (head != null && head.files.contains(fileName)){
                In oldFile = new In(".gitlet/"+head.id+"/"+fileName);
                In newFile = new In(fileName);
                if (oldFile.readAll().equals(newFile.readAll())){
                    System.out.println("File has not been modified since the last commit.");
                    return;
                }
            }
            
            stagedFiles.add(fileName);
        }
    }

    public void rm(String fileName) {
        if (stagedFiles.contains(fileName)) {
            stagedFiles.remove(fileName);
        } else {
            removedFiles.add(fileName);
        }
    }
}
