import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.File;

public class Gitlet {
    private static MasterState setup() {
        MasterState state = new MasterState();
        File stateFile = new File("./.gitlet/state");
        try {
            if (stateFile.exists()) {
                FileInputStream fileIn = new FileInputStream(stateFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                try {
                    state = (MasterState) in.readObject();
                } catch (ClassNotFoundException c) {
                    System.out.println(c);
                }
                in.close();
                fileIn.close();
            }
        } catch (IOException io) {
            System.out.println(io);
        }
        return state;
    }

    public static void main(String[] args) {
        MasterState state = setup();
        switch (args[0]) {
            case "init":
                File dir = new File("./.gitlet");
                if (dir.exists()) {
                    System.out.println(
                        "A gitlet version control system already exists in the current directory.");
                    break;
                }
                dir.mkdir();
                state.save();
                break;
            case "add":
                state.stage.add(args[1], state.branches.get(state.currentBranch));
                state.save();
                break;
            case "commit":
                state.commit(args[1]);
                state.save();
                break;
            case "rm":
                state.stage.rm(args[1], state.branches.get(state.currentBranch));
                state.save();
                break;
            case "log":
                state.log();
                break;
            case "global-log":
                state.globalLog();
                break;
            case "find":
                state.find(args[1]);
                break;
            case "status":
                state.status();
                break;
            case "checkout":
                checkoutHelper(state, args);
                break;
            case "branch":
                state.branch(args[1]);
                state.save();
                break;
            case "rm-branch":
                state.removeBranch(args[1]);
                state.save();
                break;
            case "reset":
                if (!followThrough()) {
                    break;
                }
                state.reset(Integer.parseInt(args[1]));
                state.save();
                break;
            case "merge":
                if (!followThrough()) {
                    break;
                }
                if (!state.branches.containsKey(args[1])) {
                    System.out.println("A branch with that name does not exist.");
                    break;
                }
                if (args[1].equals(state.currentBranch)) {
                    System.out.println("Cannot merge a branch with itself.");
                    break;
                }
                state.merge(args[1]);
                state.save();
                break;
            default:
                if (!followThrough()) {
                    break;
                }
        }
    }

    private static void checkoutHelper(MasterState state, String[] args) {
        if (!followThrough()) {
            return;
        }
        if (args[1].equals(state.currentBranch)) {
            System.out.println("No need to checkout the current branch.");
        }
        if ((!state.branches.containsKey(args[1])) 
            && (!state.branches.get(state.currentBranch).
                files.contains(args[1]))) {
            System.out.println(
                    "File does not exist in the most recent commit, or no such branch exists.");
        }
        if (args.length == 2) {
            if (state.branches.containsKey(args[1])) {
                state.checkoutBranch(args[1]);
            } else {
                state.checkoutFile(args[1]);
            }
        } else {
            state.checkoutSpecific(Integer.parseInt(args[1]), args[2]);
        }
        state.save();
    }

    private static boolean followThrough() {
        System.out.print(
            "Warning: The command you entered may alter the files in your working directory. ");
        System.out.println(
            "Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
        if ((new In()).readLine().equals("yes")) {
            return true;
        }
        return false;
    }
}
