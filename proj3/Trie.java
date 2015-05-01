import java.util.HashMap;
/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Ganesh Rapolu
 */
public class Trie {
    boolean exists;
    HashMap<Character, Trie> map;

    /**
     * Initializes required data structures.
     */
    public Trie() {
        exists = false;
        map = new HashMap();
    }


    /**
     * Finds word
     * @param s string to search for
     * @param isFullWord is a full word
     * @return true if word is in trie
     */
    public boolean find(String s, boolean isFullWord) {
        if (s == null) { 
            return false;
        }
        if (s.equals("")) {
            return (!isFullWord) || exists;
        }
        if (!map.containsKey(s.charAt(0))) {
            return false;
        }
        return map.get(s.charAt(0)).find(s.substring(1), isFullWord);
    }

    /**
     * Inserts word
     * @param s string to insert
     */
    public void insert(String s) {
        if (s == null || s.equals("")) {
            throw(new IllegalArgumentException());
        }
        if (map.containsKey(s.charAt(0))) {
            if (s.length() == 1) {
                map.get(s.charAt(0)).exists = true;
            } else {
                map.get(s.charAt(0)).insert(s.substring(1));
            }
        } else {
            Trie t = new Trie();
            map.put(s.charAt(0), t);
            if (s.length() == 1) {
                t.exists = true;
            } else {
                t.insert(s.substring(1));
            }
        }
    }


    /**
     * Prints trie in order specified by alphabet
     * @param alphabet alphabet order
     * @param builder buffer
     */
    public void printWithAlphabet(String alphabet, StringBuilder builder) {
        if (exists) {
            System.out.println(builder);
        }
        for (int i = 0; i < alphabet.length(); i++) { 
            char c = alphabet.charAt(i);
            if (map.containsKey(c)) {
                builder.append(c);
                map.get(c).printWithAlphabet(alphabet, builder);
                builder.deleteCharAt(builder.length() - 1);
            }
        }
    }

    /**
     * Test client.
     * @param args command line args
     */
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        t.insert("goodbye");
        t.printWithAlphabet("abcdefghijklmnopqrstuvwxyz", new StringBuilder());
        System.out.println(t.find("hell", false));
        System.out.println(t.find("hello", true));
        System.out.println(t.find("good", false));
        System.out.println(t.find("bye", false));
        System.out.println(t.find("heyy", false));
        System.out.println(t.find("hell", true));   
    }
}
