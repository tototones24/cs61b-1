//use tenary trie much better
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
public class WeightedTrie {
    //max wieght >= 0 amongst sub tries 
    char c;
    double weight;
    double maxWeight;
    String str;
    WeightedTrie left;
    WeightedTrie down;
    WeightedTrie right;

    public WeightedTrie(String s, double w) {
        this(s, w, 0);
    }

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
        if (s.length() - 1 != i){
            down =new WeightedTrie(s, w, i+1);
        } else {
            weight = w;
            str = s;
        }
    }

    public void insert(String s, double w){
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        insert(s, w, 0);
    }

    public void insert(String s, double w, int i){
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
                down = new WeightedTrie(s, w, i+1);
            } else {
                down.insert(s, w, i+1);
            }
        } else {
            if (left == null) {
                left = new WeightedTrie(s,w, i);
            } else {
                left.insert(s,w, i);
            }
        }
    }

    public boolean find(String s){
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        if (s.length() == 1 && c == s.charAt(0)){
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

    public String topMatch(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }

        if (prefix.equals("")){ 
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

    public Iterable<String> topMatches(String prefix, int k) {
        if (prefix == null || k <= 0) {
            throw new IllegalArgumentException();
        }

        TreeMap<Double, String> map = new TreeMap();
        TreeMap<Double, WeightedTrie> pqueue = new TreeMap();
        if (prefix.equals("")) {
            pqueue.put(maxWeight, this);
        }
        topMatches(prefix, pqueue, map, k);
        //todo: figure out how to get in descending order of weight
        LinkedList<String> list = new LinkedList();
        for (String s : map.values()){
            list.addFirst(s);
        }
        return list;
    }

    public void topMatches(String prefix, TreeMap<Double, WeightedTrie> pqueue, TreeMap<Double, String> map, int k) {
        if (prefix.equals("")) {
            //are these the right conditions?
            while (pqueue.size() != 0) {
                WeightedTrie trie = pqueue.pollLastEntry().getValue();
                //NEVER REFERENCE CURRENT VARIABLES; ONLY THROUGH trie!!!
                if (!Double.isNaN(trie.weight)) {
                    if (map.size() < k) {
                        map.put(trie.weight, trie.str);
                    } else if (map.size() == k && map.firstKey() < trie.weight) {
                        map.pollFirstEntry();
                        map.put(trie.weight, trie.str);
                    }
                } 

                if (trie.left != null) {
                    pqueue.put(trie.left.maxWeight, trie.left);
                }
                if (trie.down != null) {
                    pqueue.put(trie.down.maxWeight, trie.down);
                }
                if (trie.right != null) {
                    pqueue.put(trie.right.maxWeight, trie.right);
                }
                if (pqueue.size() == 0) {
                    break;
                }
                if (map.size() == k && map.firstKey() > pqueue.lastKey()) {
                    break;
                }
            }
            return;
        }

        if (prefix.length() == 1 && c == prefix.charAt(0)) {
            if (!Double.isNaN(weight)) {
                map.put(weight, str);
            }
            if (down != null) {
                pqueue.put(down.maxWeight, down);
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


    public double getWeight(String s){
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


    public void printTree(){
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
