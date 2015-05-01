/**
 * Weighted Trie. Better than other implementation for weights.
 * @author Ganesh Rapolu
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Map;
public class WeightedTrie {
    //max wieght >= 0 amongst sub tries 
    char c;
    double weight;
    double maxWeight;
    String str;
    WeightedTrie left;
    WeightedTrie down;
    WeightedTrie right;

    /**
     * Initializes trie
     * @param s string
     * @param w weight
     */
    public WeightedTrie(String s, double w) {
        this(s, w, 0);
    }

    /**
     * Initializes trie
     * @param s string
     * @param w weight
     * @param i index
     */
    public WeightedTrie(String s, double w, int i) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        maxWeight = w;
        weight = Double.NaN;
        left = null;
        right = null;
        down = null;
        str = null;
        c = s.charAt(i);
        if (s.length() - 1 != i) {
            down =new WeightedTrie(s, w, i + 1);
        } else {
            weight = w;
            str = s;
        }
    }

    /**
     * Inserts word
     * @param s string
     * @param w weight
     */
    public void insert(String s, double w) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        insert(s, w, 0);
    }

    /**
     * Inserts word
     * @param s string
     * @param w weight
     * @param i index
     */
    public void insert(String s, double w, int i) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        if (w > maxWeight) {
            maxWeight = w;
        }
        if (s.length() - 1 == i && c == s.charAt(i)) {
            weight = w;
            str = s;
            return;
        }
        if (c < s.charAt(i)) {
            if (right == null) {
                right = new WeightedTrie(s,w, i);
            } else {
                right.insert(s, w, i);
            }
        } else if (c == s.charAt(i)) {
            if (down == null) {
                down = new WeightedTrie(s, w, i + 1);
            } else {
                down.insert(s, w, i + 1);
            }
        } else {
            if (left == null) {
                left = new WeightedTrie(s,w, i);
            } else {
                left.insert(s,w, i);
            }
        }
    }

    /**
     * Finds word
     * @param s string
     * @return true if s in trie
     */
    public boolean find(String s) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        if (s.length() == 1 && c == s.charAt(0)) {
            return !Double.isNaN(weight);
        }
        if (c < s.charAt(0)) {
            if (right == null) {
                return false;
            }
            return right.find(s);
        } else if (c == s.charAt(0)) {
            if (down == null) {
                return false;
            }
            return down.find(s.substring(1));
        } else {
            if (left == null) {
                return false;
            }
            return left.find(s);
        }
    }

    /**
     * Finds topMatch
     * @param prefix prefix to search
     * @return top matching word
     */
    public String topMatch(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }

        if (prefix.equals("")) { 
            if (weight == maxWeight) {
                return str;
            } else {
                if (left != null && left.maxWeight == maxWeight) {
                    return left.topMatch(prefix);
                }
                if (down != null && down.maxWeight == maxWeight) {
                    return down.topMatch(prefix);
                }
                if (right != null && right.maxWeight == maxWeight) {
                    return right.topMatch(prefix);
                }
            }
        }

        //case where prefix has length 1 and you are at a leaf node
        if (prefix.length() == 1 && c == prefix.charAt(0)) {
            if (weight == maxWeight) {
                return str;
            }
        }

        if (c < prefix.charAt(0)) {
            if (right == null) {
                return null;
            }
            return right.topMatch(prefix);
        } else if (c == prefix.charAt(0)) {
            if (down == null) {
                return null;
            }
            return down.topMatch(prefix.substring(1));
        } else {
            if (left == null) {
                return null;
            }
            return left.topMatch(prefix);
        }
    }

    /**
     * Finds topMatches
     * @param prefix prefix to search
     * @param k number of matches
     * @return top matching words
     */
    public Iterable<String> topMatches(String prefix, int k) {
        if (prefix == null || k <= 0) {
            throw new IllegalArgumentException();
        }

        TreeMap<Double, Stack<String>> map = new TreeMap();
        TreeMap<Double, Stack<WeightedTrie>> pqueue = new TreeMap();
        if (prefix.equals("")) {
            Stack<WeightedTrie> s = new Stack();
            s.push(this);
            pqueue.put(maxWeight, s);
        }
        topMatches(prefix, pqueue, map, k);

        //gets them in the right order
        LinkedList<String> list = new LinkedList();
        int i = 0;
        for (Stack<String> stack : map.values()) {
            if (i == k) {
                break;
            }
            for (String s : stack) {
                list.addFirst(s);
                i++;
                if (i == k) {
                    break;
                }
            }
        }
        return list;
    }

    /**
     * Gets size
     * @param tree tree to find size of
     * @return actual size of tree
     */
    public int mySize(TreeMap<Double, Stack<String>> tree) {
        int size = 0;
        for (Stack s : tree.values()) {
            size += s.size();
        }
        return size;
    }

    /**
     * Finds topMatches
     * @param prefix prefix to search
     * @param pqueue priority queue
     * @param map map to store results
     * @param k number of matches
     * @return top matching words
     */
    public void topMatches(String prefix, TreeMap<Double, Stack<WeightedTrie>> pqueue,
                            TreeMap<Double, Stack<String>> map, int k) {
        if (prefix.equals("")) {

            while (pqueue.size() != 0) {
                //takes care of multiple nodes having the same weighted max
                Stack<WeightedTrie> trieStack = pqueue.pollLastEntry().getValue();
                WeightedTrie trie = trieStack.pop();
                if (!trieStack.empty()) {
                    pqueue.put(trie.maxWeight, trieStack);
                }

                if (!Double.isNaN(trie.weight)) {
                    while (mySize(map) >= k && map.firstKey() < trie.weight) {
                        Map.Entry<Double, Stack<String>> entry = map.pollFirstEntry();
                        entry.getValue().pop();
                        if (!entry.getValue().empty()){
                            map.put(entry.getKey(), entry.getValue());
                        }
                    }

                    if(mySize(map) < k || map.firstKey() < trie.weight) {
                        if (map.containsKey(trie.weight)) {
                            map.get(trie.weight).push(trie.str);
                        } else {
                            Stack<String> s = new Stack();
                            s.push(trie.str);
                            map.put(trie.weight, s);
                        }
                    }
                } 

                WeightedTrie[] w = { trie.left, trie.down, trie.right };
                for (WeightedTrie t : w) {
                    if (t != null) {
                        if (pqueue.containsKey(t.maxWeight)) {
                            pqueue.get(t.maxWeight).push(t);
                        } else {
                            Stack<WeightedTrie> s = new Stack();
                            s.push(t);
                            pqueue.put(t.maxWeight, s);
                        }
                    }
                }

                if (pqueue.size() == 0) {
                    break;
                }
                if (mySize(map) >= k && map.firstKey() > pqueue.lastKey()) {
                    break;
                }
            }
            return;
        }

        if (prefix.length() == 1 && c == prefix.charAt(0)) {
            if (!Double.isNaN(weight)) {
                Stack<String> s = new Stack();
                s.push(str);
                map.put(weight, s);
            }
            if (down != null) {
                Stack<WeightedTrie> s = new Stack();
                s.push(down);
                pqueue.put(down.maxWeight, s);
                topMatches(prefix.substring(1), pqueue, map, k);
            } 
        }

        if (c < prefix.charAt(0)) {
            if (right == null) {
                return;
            }
            right.topMatches(prefix, pqueue, map, k);
        } else if (c == prefix.charAt(0)) {
            if (down == null) {
                return;
            }
            down.topMatches(prefix.substring(1), pqueue, map, k);
        } else {
            if (left == null) {
                return;
            }
            left.topMatches(prefix, pqueue, map, k);
        }
    }


    /**
     * Gets weight of string 
     * @param s string to get weight of
     * @return weight
     */
    public double getWeight(String s) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        if (s.length() == 1 && c == s.charAt(0)) {
            if (!Double.isNaN(weight)) { 
                return weight;
            }
        } 
        if (c < s.charAt(0)) {
            if (right == null) {
                return 0;
            }
            return right.getWeight(s);
        } else if (c == s.charAt(0)) {
            if (down == null) {
                return 0;
            }
            return down.getWeight(s.substring(1));
        } else {
            if (left == null) {
                return 0;
            }
            return left.getWeight(s);
        }
    }


    /**
     * Prints tree
     */
    public void printTree() {
        System.out.println(c);
        if (str != null) {
            System.out.println(str);
        }
        if (left != null) {
            System.out.println("left:");
            left.printTree();
        }
        if (down != null) {
            System.out.println("down:");
            down.printTree();
        }
        if (right != null) {
            System.out.println("right:");
            right.printTree();
        }
    }

    /**
     * Test client
     * @param args command line args
     */
    public static void main(String[] args) {
        WeightedTrie t = new WeightedTrie("haha", 3);
        t.insert("hello", 4);
        t.insert("hey", 5);
        t.insert("hey", 500);
        t.insert("heya", 75);
        t.insert("where", 7005);
        t.insert("which", 735);
        t.insert("goodbye", 8);
        //        t.printTree();
        System.out.println(t.topMatch("hey"));
        System.out.println(t.topMatches("he", 2));
        System.out.println(t.topMatches("", 200));
        System.out.println(t.topMatches("which", 200));

        System.out.println(t.topMatch("heya"));
        System.out.println(t.topMatch("which"));
        System.out.println(t.getWeight("which"));
        System.out.println(t.getWeight("goodbye"));
        System.out.println(t.getWeight("where"));
        System.out.println(t.getWeight("hey"));
        System.out.println(t.getWeight("heya"));
        System.out.println(t.getWeight("hello"));
        System.out.println(t.find("haha"));
        System.out.println(t.find("goodbye"));
        System.out.println(t.find("hey"));
        System.out.println(t.find("hello"));
        System.out.println(t.find("goodby "));
        System.out.println(t.find("bye"));
        System.out.println(t.find("hell"));
    }
}
