package dev.am.basic_demo.others;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueWithComparator {
    static void main() {
        Queue<Task> queueTask = new PriorityQueue<>(Comparator.comparingInt(Task::priority));
        queueTask.add(new Task("Send email", 3));
        queueTask.add(new Task("Bug critical fix", 1));
        queueTask.add(new Task("Weekly meeting", 2));

        while (!queueTask.isEmpty()) {
            IO.println(queueTask.poll());
        }
    }

    record Task(String name, int priority) {}
}
