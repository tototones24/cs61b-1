//use tenary trie much better
import java.util.ArrayList;
public class WeightedTrie {
    //max wieght >= 0 amongst sub tries 
    char c;
    double weight;
    double maxWeight;
    WeightedTrie left;
    WeightedTrie down;
    WeightedTrie right;

    public WeightedTrie(String s, double w) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        maxWeight = w;
        weight = Double.NaN;
        left = null;
        right = null;
        down = null;
        c = s.charAt(0);
        if (s.length() != 1){
            down = new WeightedTrie(s.substring(1), w);
        } else {
            weight = w;
        }
    }

    public void insert(String s, double w){
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        if (w > maxWeight) {
            maxWeight = w;
        }
        if (s.length() == 1 && c == s.charAt(0)) {
            weight = w;
            return;
        }
        if (c < s.charAt(0)) {
            if (right == null) {
                right = new WeightedTrie(s,w);
            } else {
                right.insert(s, w);
            }
        } else if (c == s.charAt(0)) {
            if (down == null) {
                down = new WeightedTrie(s.substring(1), w);
            } else {
                down.insert(s.substring(1), w);
            }
        } else {
            if (left == null) {
                left = new WeightedTrie(s,w);
            } else {
                left.insert(s,w);
            }
        }
    }

    public boolean find(String s){
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        if (s.length() == 1 && c == s.charAt(0)){
            return down == null && left == null && right == null; 
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
        if (prefix == null || prefix.equals("")) {
            throw new IllegalArgumentException();
        }
        return topMatch(prefix, new StringBuffer());
    }

    public String topMatch(String prefix, StringBuffer buf) {
        if (prefix.equals("")){ 
            if (weight == maxWeight) {
                buf.append(c);
                return buf.toString();
            }
            if (left != null && left.maxWeight == maxWeight) {
                return left.topMatch(prefix, buf);
            }
            if (down != null && down.maxWeight == maxWeight) {
                buf.append(c);
                return down.topMatch(prefix, buf);
            }
            if (right != null && right.maxWeight == maxWeight) {
                return right.topMatch(prefix, buf);
            }
        }

        if (prefix.length() == 1 && c == prefix.charAt(0)) {
            if (left == null && down == null && right == null) {
                buf.append(c);
                return buf.toString();
            }
        }
        if (c < prefix.charAt(0)) {
            if (right == null) {
                return null;
            }
            return right.topMatch(prefix, buf);
        } else if (c == prefix.charAt(0)) {
            if (down == null) {
                return null;
            }
            buf.append(c);
            return down.topMatch(prefix.substring(1), buf);
        } else {
            if (left == null) {
                return null;
            }
            return left.topMatch(prefix, buf);
        }
    }

    public Iterable<String> topMatches(String prefix, int k) {
        if (prefix == null || prefix.equals("")) {
            throw new IllegalArgumentException();
        }

        return topMatches(prefix,new StringBuffer(prefix), new ArrayList(), k);
    }

    public Iterable<String> topMatches(String prefix, StringBuffer buf, ArrayList arr, int k) {
        return null;
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
        t.insert("hey", 5);
        t.insert("heya", 75);
        t.insert("where", 7005);
        t.insert("which", 735);
        t.insert("goodbye", 8);

        System.out.println(t.topMatch("heya"));
        System.out.println(t.topMatch("which"));
        System.out.println(t.getWeight("which"));
        System.out.println(t.getWeight("goodbye"));
        System.out.println(t.getWeight("where"));
        System.out.println(t.getWeight("hey"));
        System.out.println(t.getWeight("heya"));
        System.out.println(t.getWeight("hello"));
        //t.printTree();
        /*
        System.out.println(t.find("haha"));
        System.out.println(t.find("goodbye"));
        System.out.println(t.find("hey"));
        System.out.println(t.find("hello"));
        System.out.println(t.find("goodby "));
        System.out.println(t.find("bye"));
        System.out.println(t.find("hell"));
        */
    }
}
