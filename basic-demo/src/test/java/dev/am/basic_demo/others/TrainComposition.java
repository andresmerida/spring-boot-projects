package dev.am.basic_demo.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class TrainComposition {
    private final Deque<Integer> train = new ArrayDeque<>();

    public TrainComposition() {}

    public void attachWagonFromLeft(int wagonId) {
        train.addFirst(wagonId);
    }

    public void attachWagonFromRight(int wagonId) {
        train.addLast(wagonId);
    }

    public int detachWagonFromLeft() {
        return train.removeFirst();
    }

    public int detachWagonFromRight() {
        return train.removeLast();
    }

    static void main() {
        TrainComposition trainComposition = new TrainComposition();
        trainComposition.attachWagonFromLeft(2);
        trainComposition.attachWagonFromLeft(1);
        trainComposition.attachWagonFromRight(3);
        trainComposition.attachWagonFromRight(4);

        IO.println("Train: " + trainComposition.train);

        IO.println(trainComposition.detachWagonFromRight());
        IO.println(trainComposition.detachWagonFromLeft());

        IO.println("End Train: " + trainComposition.train);
    }
}
