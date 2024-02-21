package tqs.lab1;

import java.util.NoSuchElementException;

public class TqsStack<T> {
    
        private int size;
        private int capacity;
        private T[] elements;
    
        public TqsStack(int capacity) {
            this.capacity = capacity;
            this.elements = (T[]) new Object[capacity];
        }
    
        public boolean isEmpty() {
            return size == 0;
        }
    
        public void push(T element) {
            if (size == capacity) {
                throw new IllegalStateException("Stack is full");
            }
            System.out.println(elements);
            elements[size++] = element;
        }
    
        public T pop() {
            if (size == 0) {
                throw new NoSuchElementException("Stack is empty");
            }
            return elements[--size];
        }
    
        public T peek() {
            if (size == 0) {
                throw new NullPointerException("Stack is empty");
            }
            return elements[size - 1];
        }
    
        public int size() {
            return size;
        }
    
}
