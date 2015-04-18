import java.util.Scanner;
public class AlphabetSort {
    public static void main(String[] args){
        //use stringbuider and push & pop as you traverse the trie
        Scanner in = new Scanner(System.in);
        String alphabet = in.nextLine();
        Trie t = new Trie();
        while(in.hasNextLine()) {
            t.insert(in.nextLine());
        }
        t.printWithAlphabet(alphabet, new StringBuilder());
    }
}
