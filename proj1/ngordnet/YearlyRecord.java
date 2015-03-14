/** An object that stores word counts of all words in a single year. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
package ngordnet;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Comparator;

/** An object that stores all word counts for a given year, */
public class YearlyRecord {
    private TreeMap<String, Integer> countMap = new TreeMap<String, Integer>();

    private TreeMap<String, Integer> rankMap = new TreeMap<String, Integer>();

    /** Determines whether or not the fewerZsCount map needs updating. */
    private boolean needsUpdating = true;


    public YearlyRecord() {
        countMap = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        countMap = new TreeMap(otherCountMap);
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return countMap.get(word);
    }

    public int size() {
        return countMap.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        if (needsUpdating) {
            updateRank();
        }
        return (SortedSet<String>) rankMap.keySet();
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        TreeSet<Number> s = new TreeSet();
        s.addAll(countMap.values());
        return s;

    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        /* Will not pass muster for that 0.1 bonus points. */
        if (count > 0) {
            countMap.put(word, count);
            needsUpdating = true;
        }

    }

    /** Returns the number of words with fewer Zs than x, where x is some
     * key in the map. If x is not part of the map, return -1. */
    public int rank(String x) {        
        if (!countMap.containsKey(x)) {
            return -1;
        }

        if (needsUpdating) {
            updateRank();
        }
        return rankMap.get(x);
    }

    /** Comparator that compares strings based on zCount. */
    private class RankComparator implements Comparator<String> {
        public int compare(String x, String y) {
            return countMap.get(y) - countMap.get(x);
        }
    }

    /** Update sthe fewerZsCount map using sorting. */
    private void updateRank() {
        rankMap = new TreeMap<String, Integer>();
        /** The slow approach:
         * For every key, compare against all other keys, and count zs.
         * O(N^2) -- slow compared to sorting. */

        /* Better approach: Sort the items! */
        /* After sorting: we get 'peel', 'zebra', 'zebras', 'zzzzzz'
        */

        String[] words = new String[countMap.size()];
        int cnt = 0;
        for (String word : countMap.keySet()) {
            words[cnt] = word;
            cnt += 1;
        }

        /* Sort words by order of number of Zs */
        Arrays.sort(words, new RankComparator());

        /* This is specific to this weird problem I've made up, not the
         * project. */
        for (int i = 0; i < words.length; i += 1) {
            rankMap.put(words[i], i + 1);
        }
        needsUpdating = false;

    }
} 
