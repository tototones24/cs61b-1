import java.util.Scanner;
public class AlphabetSort {
    public static void main(String[] args){
        //use stringbuider and push & pop as you traverse the trie
        Scanner in = new Scanner(System.in);
        if (!in.hasNextLine()) {
            throw new IllegalArgumentException();
        }
        String alphabet = in.nextLine();
        Set<Character> s = new Set();
        for (int i=0; i<alphabet.length();i++){
            s.add(alphabet.charAt(i));
        }
        if (!in.hasNextLine() || alphabet.length() != s.size()) {
            throw new IllegalArgumentException();
        }
        Trie t = new Trie();
        while(in.hasNextLine()) {
            t.insert(in.nextLine());
        }
        t.printWithAlphabet(alphabet, new StringBuilder());
    }
}
