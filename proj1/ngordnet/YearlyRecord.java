package ngordnet;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.HashMap;

public class YearlyRecord {
    /** Creates a new empty YearlyRecord. */
    TreeMap<String, Integer> data;
    public YearlyRecord(){
        data = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap){
        data = new TreeMap(otherCountMap);
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word){
        return data.get(word);
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count){
        data.put(word, count);
    }

    /** Returns the number of words recorded this year. */
    public int size(){
        return data.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        return data.keySet();
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        return (Collection<Number>) (Collection<? extends Number>) data.values();
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word){
        //todo
        return 0;
    }
} 
