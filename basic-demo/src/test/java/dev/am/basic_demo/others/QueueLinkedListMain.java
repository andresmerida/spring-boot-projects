package dev.am.basic_demo.others;

import java.util.LinkedList;
import java.util.Queue;

public class QueueLinkedListMain {
    static void main() {
        Queue<String> queue = new LinkedList<>();
        queue.offer("First");
        queue.offer("Second");
        queue.offer("Third");

        IO.println("Queue: " + queue);
        // Peek at the first element
        IO.println("Peek: " + queue.peek());

        IO.println();

        // remove the first element
        IO.println("Remove element: " + queue.poll());
        IO.println("Queue after first removed: " + queue);

        IO.println();

        // remove all elements
        IO.println("Removing all elements...");
        while (!queue.isEmpty()) {
            queue.poll();
        }
        IO.println("Empty queue: " + queue);
    }
}
