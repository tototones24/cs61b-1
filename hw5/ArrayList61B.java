import java.util.AbstractList;
public class ArrayList61B<T> extends AbstractList<T>{
    private T[] array;
    private int numElems;

    public ArrayList61B(int initialCapacity){
        array =  (T[]) new Object[initialCapacity];
        numElems = 0;
    }
    public ArrayList61B(){
        this(1);
    }

    public T get(int i){
        if (i < 0 || i >= numElems)
            throw new IllegalArgumentException();
        return array[i];
    }

    public boolean add(T item){
        if (numElems == array.length){
            T[] newArray =  (T[]) new Object[array.length * 2];
            for (int i=0; i<numElems; i++)
                newArray[i] = array[i];
            array = newArray;
            
        }
        array[numElems] = item;
        numElems++;
        return true;
    }

    public int size(){
        return numElems;
    }
}
