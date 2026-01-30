package dev.am.basic_demo.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequeArrayDequeMain {
    static void main() {
        Deque<String> deque = new ArrayDeque<>();

        // Operations from the ahead (front) ***
        deque.addFirst("Element 1 (Head)"); // Adds to the front
        deque.push("Element 0 (Head)");     // Also adds to the front (stack method)

        IO.println("peekFirst: " + deque.peekFirst());
        IO.println("Deque: " + deque);
        IO.println();

        deque.addLast("Element 2 (Tail)");      // Adds to the end
        deque.offerLast("Element 3 (Tail)");    // Also adds to the end (queue method)

        IO.println("peekLast: " + deque.peekLast());
        IO.println("Deque: " + deque);
        IO.println();

        // Retrieving/Removing elements ***
        IO.println("Remove first element: " + deque.removeFirst()); // Removes and return the first element
        IO.println("Remove last element: " + deque.pollLast());     // Removes and return the last element, returns null if empty
        IO.println("Deque: " + deque);
        IO.println();

        // Examining elements without removing them ***
        IO.println("Head element (peek): " + deque.peekFirst());
        IO.println("Tail element (peek): " + deque.peekLast());
        IO.println("Deque After operations: " + deque);
    }
}
