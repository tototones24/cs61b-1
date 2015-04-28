import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.GregorianCalendar;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.sql.Timestamp;

public class MasterState implements Serializable {
    public HashMap<String, Commit> branches;
    public int currentUniqueID;
    public String currentBranch;
    public StagingArea stage;

    public MasterState() {
        branches = new HashMap<String, Commit>();
        Commit c = new Commit();
        c.previous = null;
        c.files = new HashSet();
        c.message = "initial commit";
        c.created = new Timestamp((new GregorianCalendar()).getTime().getTime());
        c.id = 0;
        branches.put("master", c);
        currentUniqueID = 1;
        currentBranch = "master";
        stage = new StagingArea();
    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("./.gitlet/state");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    public void commit(String message) {
        if ((stage.stagedFiles.size() + stage.removedFiles.size()) == 0) {
            System.out.println("No changes added to the commit.");
            return;
        }
        if (message.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        Commit c = new Commit();
        Commit old = branches.get(currentBranch);
        c.previous = old;
        c.message = message;
        c.created = new Timestamp((new GregorianCalendar()).getTime().getTime());
        c.id = currentUniqueID;
        HashSet fileSet = new HashSet();
        if (old != null) {
            fileSet.addAll(old.files);
        }
        fileSet.addAll(stage.stagedFiles);
        fileSet.removeAll(stage.removedFiles);
        c.files = fileSet;
        branches.put(currentBranch, c);

        File newDir = new File(".gitlet/" + currentUniqueID);
        newDir.mkdir();
        for (String name : stage.stagedFiles) {
            try {
                Path p = (new File(name)).toPath();
                Path possibleExtraDir = p.getParent();
                if (possibleExtraDir != null) {
                    newDir.toPath().resolve(possibleExtraDir).toFile().mkdir();
                }
                Files.copy(p, newDir.toPath().resolve(p));
            } catch (IOException io) {
                System.out.println(io);
            }
        }
        currentUniqueID++;
        stage = new StagingArea();
    }

    public void log() {
        Commit c = branches.get(currentBranch);
        while (c != null) {
            System.out.println("====");
            System.out.println("Commit " + c.id + ".");
            System.out.println(c.created);
            System.out.println(c.message);
            System.out.println();
            c = c.previous;
        }
    }

    public void globalLog() {
        for (Commit c : branches.values()) {
            while (c != null) {
                System.out.println("====");
                System.out.println("Commit " + c.id + ".");
                System.out.println(c.created);
                System.out.println(c.message);
                System.out.println();
                c = c.previous;
            }
        }
    }

    public void find(String message) {
        boolean found = false;
        for (Commit c : branches.values()) {
            while (c != null) {
                if (c.message.equals(message)) {
                    System.out.println(c.id);
                    found = true;
                }
                c = c.previous;
            }
        }
        if (!found) {
            System.out.println("Found no commit with that message.");
        }
    }

    public void status() {
        System.out.println("=== Branches ===");
        System.out.println("*" + currentBranch);
        Set<String> rest = branches.keySet();
        rest.remove(currentBranch);
        for (String s : rest) {
            System.out.println(s);
        }

        System.out.println("");
        System.out.println("=== Staged Files ===");
        for (String name : stage.stagedFiles) {
            System.out.println(name);
        }

        System.out.println("");
        System.out.println("=== Files Marked for Removal ===");
        for (String name : stage.removedFiles) {
            System.out.println(name);
        }
    }

    public void checkoutFile(String name) {
        if (!branches.get(currentBranch).files.contains(name)) {
            System.out.println(
                    "File does not exist in the most recent commit, or no such branch exists.");
            return;
        }

        branches.get(currentBranch).restoreFile(name);
    }

    public void checkoutBranch(String name) {
        branches.get(name).restore();
        currentBranch = name;
    }

    public void checkoutSpecific(int commitID, String name) {
        for (Commit c : branches.values()) { 
            while (c != null) {
                if (c.id == commitID) {
                    if (!c.files.contains(name)) {
                        System.out.println("File does not exist in that commit.");
                        return;
                    }
                    c.restoreFile(name);
                    return;
                }
                c = c.previous;
            }
        }
        System.out.println("No commit with that id exists.");
    }

    public void branch(String name) {
        if (branches.containsKey(name)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        branches.put(name, branches.get(currentBranch));
    }

    public void removeBranch(String name) {
        if (!branches.containsKey(name)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (currentBranch.equals(name)) {
            System.out.println("Cannot remove the current branch.");
            return;

        }
        branches.remove(name);
    }

    public void reset(int commitID) {
        for (Commit c : branches.values()) {
            while (c != null) {
                if (c.id == commitID) {
                    c.restore();
                    return;
                }
                c = c.previous;
            }
        }
        System.out.println("No commit with that id exists.");
    }

    public void merge(String branchName) {
        Commit other = branches.get(branchName);
        Commit current = branches.get(currentBranch);
        for (String s : other.files) {
            if (!current.files.contains(s)) {
                other.restoreFile(s);
            } else {
                //this is wrong. should be comparing against file at the split
                //point commit
                In oldFile = new In(current.getFile(s));
                In newFile = new In(s);

                if (newFile.exists() && oldFile.readAll().equals(newFile.readAll())) {
                    other.restoreFile(s);
                } else {
                    File f = other.getFile(s);
                    Path d = (new File(".")).toPath();

                    try {
                        Files.copy(f.toPath(), d.resolve(s + ".conflicted"), 
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException io) {
                        System.out.println(io);
                    }
                }
            }

        }
    }

    public void rebase(String branchName) {
    }

    public void advancedRebase(String branchName) {
    }
}
