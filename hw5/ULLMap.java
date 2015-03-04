import java.util.Set; /* java.util.Set needed only for challenge problem. */
import java.util.Iterator;

/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. 
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */ 
public class ULLMap<K,V> implements Map61B<K,V>, Iterable<K> { 
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as a the actual front item
      * of the linked list. 
      */
    private Entry front;
    private int size;

    public ULLMap(){
        front = null;
        size = 0;
    }

    @Override
    public V get(K key) {
        Entry e = front.get(key);
        if (e == null)
            return null;
        return e.val;
    }

    @Override
    public void put(K key, V val) {
        if (front != null){
            Entry e = front.get(key);
            if (e != null){
                e.val = val;
                return;
            }
        }
        front = new Entry(key, val, front);
    }

    @Override
    public boolean containsKey(K key) {
        return front.get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        front = null;
    }

    @Override
    public Iterator<K> iterator(){
        return (Iterator<K>) new ULLMapIter(front);
    }

    public static <K,V> ULLMap<V,K> invert(ULLMap<K,V> map){
        ULLMap<V,K> invertedMap = new ULLMap<V,K>();
        for (K key : map)
            invertedMap.put(map.get(key), key);
        return invertedMap;
    }

    private class ULLMapIter implements Iterator<K> {
        private Entry start;
        public ULLMapIter(Entry e){
            this.start = e;
        }

        @Override
        public boolean hasNext(){
            return start != null;
        }

        @Override
        public K next(){
            Entry e = start;
            start = start.next;
            return e.key;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry {
    
        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        public Entry(K k, V v, Entry n) {
            key = k;
            val = v;
            next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        public Entry get(K k) {
            if (key.equals(k)){
                return this;
            }
            else{
                if (next == null)
                    return null;
                return next.get(k);
                
            }
        }

        /** Stores the key of the key-value pair of this node in the list. */
        private K key;
        /** Stores the value of the key-value pair of this node in the list. */
        private V val;
        /** Stores the next Entry in the linked list. */
        private Entry next;
    
    }

    /* Methods below are all challenge problems. Will not be graded in any way. 
     * Autograder will not test these. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }


}
