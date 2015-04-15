import java.io.*;

public class Gitlet {
    public static void main(String[] args) {
        File stateFile = new File("./.gitlet/state");
        MasterState state = new MasterState();
        try {
            if (stateFile.exists()){
                FileInputStream fileIn = new FileInputStream(stateFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                try {
                    state = (MasterState) in.readObject();
                }
                catch (ClassNotFoundException c) {}
                in.close();
                fileIn.close();
            }
        }
        catch (IOException io) {
            System.out.println(io);
        }
        switch (args[0]) {
            case "init":
                File dir = new File("./.gitlet");
                if (dir.exists()) {
                    System.out.println("A gitlet version control system already exists in the current directory.");
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
                if (!followThrough()){
                    break;
                }
                if (args[1].equals(state.currentBranch)) {
                    System.out.println("No need to checkout the current branch.");
                }
                if ((!state.branches.containsKey(args[1])) && (!state.branches.get(state.currentBranch).files.contains(args[1]))) {
                    System.out.println("File does not exist in the most recent commit, or no such branch exists.");
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
                if (!followThrough()){
                    break;
                }
                state.reset(Integer.parseInt(args[1]));
                state.save();
                break;

            case "merge":
                if (!followThrough()){
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

            case "rebase":
                if (!followThrough()){
                    break;
                }
                state.rebase(args[1]);
                state.save();
                break;

            case "i-rebase":
                if (!followThrough()){
                    break;
                }
                state.advancedRebase(args[1]);
                state.save();
                break;
        }
    }

    private static boolean followThrough(){
        System.out.println("Warning: The command you entered may alter the files in your working directory. Uncommitted changes may be lost. Are you sure you want to continue? (yes/no)");
        if ((new In()).readLine().equals("yes")){
            return true;
        }
        return false;
    }
}
