import java.util.Set;
/* Your implementation MyHashMap should implement this interface. To do so, 
 * append "implements Map61B<K,V>" to the end of your "public class..."
 * declaration, though you can use other formal type parameters if you'd like.
 * For example, you can also say "... implements Map61B<Ben, Jerry>".
 */ 
public class MyHashMap<K,V> implements Map61B<K, V> {
    /** Removes all of the mappings from this map. */
    public void clear(){
    }

    /* Returns true if this map contains a mapping for the specified key. 
     * Should run on average constant time when called on a HashMap. 
     */
    public boolean containsKey(K key){
        return false;
    }
    

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. Should run on average constant time
     * when called on a HashMap. 
     */
    public V get(K key){
        return null;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size(){
        return 0;
    }

    /* Associates the specified value with the specified key in this map. 
     * Should run on average constant time when called on a HashMap. */
    public void put(K key, V value){
    }

    /* Removes the mapping for the specified key from this map if present. 
     * Should run on average constant time when called on a HashMap. */
    public V remove(K key){
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Should run on average constant time when called on 
     * a HashMap. */
    public V remove(K key, V value){
        return null;
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet(){
        return null;
    }
}
