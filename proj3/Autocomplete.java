import java.util.LinkedList;
import java.util.HashSet;
/**
 * 
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 * @author Ganesh Rapolu
 */
public class Autocomplete {
    WeightedTrie trie;
    /**
     * Initializes required data structures from parallel arrays.
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        if (terms.length != weights.length) {
            throw new IllegalArgumentException();
        }
        trie = new WeightedTrie(terms[0], weights[0]);
        HashSet<String> seen = new HashSet();
        for (int i = 0; i < terms.length; i++) {
            if (weights[i] < 0) {
                throw new IllegalArgumentException();
            }
            trie.insert(terms[i], weights[i]);
            if (!seen.add(terms[i])) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term Term to search for
     * @return Weight of term
     */
    public double weightOf(String term) {
        return trie.getWeight(term);
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        return trie.topMatch(prefix);
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param prefix Prefix to search for
     * @param k number of items
     * @return top k matches
     */
    public Iterable<String> topMatches(String prefix, int k) {
        return trie.topMatches(prefix, k);
    }

    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * @param word The word to spell-check
     * @param dist Maximum edit distance to search
     * @param k    Number of results to return 
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        LinkedList<String> results = new LinkedList<String>();  
        /* YOUR CODE HERE; LEAVE BLANK IF NOT PURSUING BONUS */
        return results;
    }
    /**
     * Test client. Reads the data from the file, 
     * then repeatedly reads autocomplete queries from standard input and prints out the 
     * top k matching terms.
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();

            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
            }
        }
    }
}
