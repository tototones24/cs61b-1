import java.util.Set;
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size;
    private K key;
    private V value;
    private BSTMap<K, V> left;
    private BSTMap<K, V> right;

    public BSTMap() {
        size = 0;
        key = null;
        value = null;
        left = null;
        right = null;
    }

    public BSTMap(K k, V v) {
        size = 1;
        key = k;
        value = v;
        left = null;
        right = null;
    }

    public int size() {
        return size;
    }

    public V get(K k) {
        if (size == 0) {
            return null;
        }
        if (k.equals(key)) {
            return value;
        }
        if (k.compareTo(key) < 0) {
            return left.get(k);
        }
        return right.get(k);
    }

    public boolean containsKey(K k) {
        if (size == 0) {
            return false;
        }
        if (k.equals(key)) {
            return true;
        }
        if (k.compareTo(this.key) < 0) {
            return left.containsKey(k);
        }
        return right.containsKey(k);
    }

    public void put(K k, V v) {
        if (size == 0) {
            key = k;
            value = v;
            size++;
        }
        if (k.equals(key)) {
            value = v;
        } else if (k.compareTo(key) < 0) {
            if (left == null) {
                left = new BSTMap(k, v);
                size++;
            } else {
                int s = left.size();
                left.put(k, v);
                if (left.size() > s) {
                    size++;
                }
            }
        } else {
            if (right == null) {
                right = new BSTMap(k, v);
                size++;
            } else {
                int s2 = right.size();
                right.put(k, v);
                if (right.size() > s2) {
                    size++;
                }
            }
        }
    }

    public void printInOrder() {
        if (size == 0) {
            return;
        }

        if (left != null) {
            left.printInOrder();
        }
        System.out.print(key);
        if (right != null) {
            right.printInOrder();
        }
    }

    public void clear() {
        size = 0;
        key = null;
        value = null;
        left = null;
        right = null;
    }

    public V remove(K k) {
        return null;
    }

    public V remove(K k, V v) {
        return null;
    }

    public Set<K> keySet() {
        return null;
    }
}
