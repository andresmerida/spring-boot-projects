package dev.am.basic_demo.others;

public class GamePlatform {
    public static double calculateFinalSpeed(double initialSpeed, int[] inclinations) {
        double currentSpeed = initialSpeed;
        for (int inclination : inclinations) {
            currentSpeed -= inclination;

            // Senior check: Immediate exit if speed is depleted
            if (currentSpeed < 0) {
                return 0.0;
            }
        }

        return currentSpeed;
    }

    static void main() {
        // Example: Start at 60, up 30 (30), flat 0 (30), down 45 (75), flat 0 (75)
        IO.println(calculateFinalSpeed(60, new int[] {0, 30, 0, -45, 0}));
    }
}
