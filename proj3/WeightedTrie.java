//use tenary trie much better
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
        if (s.length() == 1){
            return (c == s.charAt(0)) && down == null && left == null && right == null; 
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
        return null;
    }


    public double getWeight(String s){
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        if (s.length() == 1){
            if (down == null && left == null && right == null) {
                return weight;
            } else {
                return 0;
            }
        }
        if (c < s.charAt(0)) {
            return right.getWeight(s.substring(1));
        } else if (c == s.charAt(0)) {
            return down.getWeight(s.substring(1));
        } else {
            return left.getWeight(s.substring(1));
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
        t.insert("goodbye", 8);

        //t.printTree();
        System.out.println(t.find("haha"));
        System.out.println(t.find("goodbye"));
        System.out.println(t.find("hey"));
        System.out.println(t.find("hello"));
        System.out.println(t.find("goodby "));
        System.out.println(t.find("bye"));
        System.out.println(t.find("hell"));
    }
}
