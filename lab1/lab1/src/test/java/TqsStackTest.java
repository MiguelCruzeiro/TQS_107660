
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import tqs.lab1.TqsStack;

@TestInstance(Lifecycle.PER_CLASS)
public class TqsStackTest {

    private TqsStack<Integer> stack;

    @BeforeEach
    public void init() {
        stack = new TqsStack<>(10);
    }

    @AfterAll
    public void tearDown() {
        stack = null;
    }

    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void testPush() {
        stack.push(1);
        System.out.println(stack.size());
        Assertions.assertEquals(1, stack.size());
    }

    @Test
    public void testPop() {
        stack.push(1);
        stack.push(2);
        stack.pop();
        Assertions.assertEquals(1, stack.size());
    }

    @Test
    public void testPeek() {
        stack.push(1);
        stack.push(2);
        Assertions.assertEquals(2, stack.peek());
    }

    @Test
    public void testSize() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Assertions.assertEquals(3, stack.size());
    }

    @Test
    public void testPopEmpty() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test
    public void testPeekEmpty() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            stack.peek();
        });
    }

    

    @Test
    public void testPopEmptyStack() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test
    public void testPeekEmptyStack() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            stack.peek();
        });
    }

    @Test
    public void testPushAndPeek() {
        stack.push(1);
        Assertions.assertEquals(1, stack.peek());
    }



    
}
