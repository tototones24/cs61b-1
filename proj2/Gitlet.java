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
                if (!dir.exists()) {
                    dir.mkdir();
                    state.save();
                }
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
                state.stage.rm(args[1]);
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
                state.reset(Integer.parseInt(args[1]));
                state.save();
                break;
            case "merge":
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
                state.rebase(args[1]);
                state.save();
                break;
            case "i-rebase":
                state.advancedRebase(args[1]);
                state.save();
                break;
        }
    }
}
