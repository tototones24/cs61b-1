package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(2);
        arb.enqueue(12);
        arb.enqueue(24);
        assertEquals(arb.dequeue(),12,0.5);
        arb.enqueue(20);
        assertEquals(arb.dequeue(),24,0.5);
        assertEquals(arb.dequeue(),20,0.5);
        assertEquals(arb.isEmpty(), true);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
