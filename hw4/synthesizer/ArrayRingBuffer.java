// Make sure to make this class a part of the synthesizer package
package synthesizer;

public class ArrayRingBuffer extends AbstractBoundedQueue {
    /* Index for the next dequeue or peek. */
    private int first;           
    /* Index for the next enqueue. */
    private int last;             
    /* Array for storing the buffer data. */
    private double[] rb;

    /** Create a new ArrayRingBuffer with the given capacity. */
    public ArrayRingBuffer(int capacity) {
        // Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0. 
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue.
        first = 0;
        last = 0;
        fillCount = 0;
        rb = new double[capacity];
        this.capacity = capacity;
    }

    /** Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow") 
     */
    public void enqueue(double x) {
        // Enqueue the item. Don't forget to increase fillCount and update last.
        // is there room?
        if (isFull())
            throw new RuntimeException("Ring buffer overflow");

        fillCount++;
        rb[last] = x;
        last = (last + 1) % capacity;
    }

    /** Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow");
     */
    public double dequeue() {
        // Dequeue the first item. Don't forget to decrease fillCount and update first.
        if (isEmpty())
            throw new RuntimeException("Ring buffer underflow");
        fillCount--;
        double result = rb[first];
        first = (first + 1) % capacity;
        return result;
    }

    /** Return oldest item, but don't remove it. */
    public double peek() {
        //Return the first item. None of your instance variables should change.
        if (isEmpty())
            throw new RuntimeException("Ring buffer underflow");
        return rb[first];
    }

}
