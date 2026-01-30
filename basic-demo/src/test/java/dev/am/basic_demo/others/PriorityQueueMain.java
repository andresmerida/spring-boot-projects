package dev.am.basic_demo.others;

import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueMain {
    static void main() {
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(10);
        queue.add(5);
        queue.add(20);
        queue.add(15);

        IO.println("Elements: " + queue);
        IO.println("Fist element: " + queue.peek());

        IO.println();
        IO.println("Elements retrieved in ascending natural order");
        while (!queue.isEmpty()) {
            IO.println(queue.poll());
        }
    }
}
