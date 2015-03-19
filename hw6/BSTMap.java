import java.util.Set;
public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V> {
    private int size;
    private K key;
    private V value;
    private BSTMap<K,V> left;
    private BSTMap<K,V> right;

    public BSTMap(){
        size = 0;
        key = null;
        value = null;
        left = null;
        right = null;
    }

    public BSTMap(K key, V value){
        size = 1;
        this.key = key;
        this.value = value;
        left = null;
        right = null;
    }

    public int size(){
        return size;
    }

    public V get(K key){
        if (size == 0) {
            return null;
        }
        if (key.equals(this.key)){
            return value;
        }
        if (key.compareTo(this.key) < 0){
            return left.get(key);
        }
        return right.get(key);
    }

    public boolean containsKey(K key){
        if (size == 0) {
            return false;
        }
        if (key.equals(this.key)){
            return true;
        }
        if (key.compareTo(this.key) < 0){
            return left.containsKey(key);
        }
        return right.containsKey(key);
    }

    public void put(K key, V value){
        if (size == 0){
            this.key = key;
            this.value = value;
            size++;
        }
        if (key.equals(this.key)){
            this.value = value;
        } else if (key.compareTo(this.key) < 0){
            if (left == null){
                left = new BSTMap(key, value);
                size++;
            } else {
                int s = left.size();
                left.put(key, value);
                if (left.size() > s) {
                    size++;
                }
            }
        } else {
            if (right == null){
                right = new BSTMap(key, value);
                size++;
            } else {
                int s2 = right.size();
                right.put(key, value);
                if (right.size() > s2) {
                    size++;
                }
            }
        }
    }

    public void printInOrder(){
        if (size == 0){
            return;
        }

        if (left != null){
            left.printInOrder();
        }
        System.out.print(key);
        if (right != null){
            right.printInOrder();
        }
    }

    public void clear(){
        size = 0;
        key = null;
        value = null;
        left = null;
        right = null;
    }

    public V remove(K key){
        return null;
    }

    public V remove(K key, V value){
        return null;
    }

    public Set<K> keySet(){
        return null;
    }
}
