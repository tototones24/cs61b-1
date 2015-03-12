/** An object that stores word counts of all words in a single year. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
package ngordnet;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.HashMap;

import java.util.TreeMap;
import java.util.Arrays;
import java.util.Comparator;

/** An object that stores all word counts for a given year, */
public class YearlyRecord {
    private TreeMap<String, Integer> countMap = new TreeMap<String, Integer>();

    /** Store the number of keys with fewer Zs than our String. */
    private TreeMap<String, Integer> fewerZsCount = new TreeMap<String, Integer>();

    /** Determines whether or not the fewerZsCount map needs updating. */
    private boolean needsUpdating = true;


    public YearlyRecord(){
       countMap = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap){
        countMap = new TreeMap(otherCountMap);
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return countMap.get(word);
    }

    public int size(){
        return countMap.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        return countMap.keySet();
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        return (Collection<Number>) (Collection<? extends Number>) countMap.values();
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        /* Will not pass muster for that 0.1 bonus points. */
        countMap.put(word, count);
        needsUpdating = true;

    }

    /** Returns the number of words with fewer Zs than x, where x is some
      * key in the map. If x is not part of the map, return -1. */
    public int rank(String x) {        
        if (!countMap.containsKey(x)) {
            return -1;
        }

        if (needsUpdating) {
            updateFewerZsCount();
            needsUpdating = false;
        }
        return fewerZsCount.get(x);
    }

    /** Comparator that compares strings based on zCount. */
    private class ZComparator implements Comparator<String> {
        public int compare(String x, String y) {
            if (countMap.get(x) == countMap.get(y))
                return x.compareTo(y);
            return countMap.get(x) - countMap.get(y);
        }
    }

    /** Update sthe fewerZsCount map using sorting. */
    private void updateFewerZsCount() {
        fewerZsCount = new TreeMap<String, Integer>();
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
        Arrays.sort(words, new ZComparator());

        /* This is specific to this weird problem I've made up, not the
         * project. */
        int lastZCount = 0;
        fewerZsCount.put(words[0], 0);        
        for (int i = 1; i < words.length; i += 1) {
            int currentZCount = countMap.get(words[i]);
            int numWordsLessZs;

            if (currentZCount > lastZCount) {
                numWordsLessZs = i;
            } else {
                numWordsLessZs = fewerZsCount.get(words[i-1]);
            }
            
            fewerZsCount.put(words[i], numWordsLessZs);
            lastZCount = currentZCount;
        }

    }
} 
