import java.util.Scanner;

public class ClockAngleCost {

    // Method to calculate the current angle between hour and minute hands
    public static int calculateCurrentAngle(int hour, int minute) {
        // Hour hand moves 0.5 degrees per minute
        int hourAngle = (int)((hour % 12) * 30 + minute * 0.5);
        // Minute hand moves 6 degrees per minute
        int minuteAngle = minute * 6;

        // Calculate the absolute difference and make sure it's within [0, 180] degrees
        int angle = Math.abs(hourAngle - minuteAngle);
        return Math.min(angle, 360 - angle);
    }

    // Method to calculate the cost for moving the hour hand
    public static int calculateHourCost(int angleChange, int P, int Q) {
        if (angleChange <= 90) {
            return angleChange * P;
        } else {
            return (90 * P) + (angleChange - 90) * Q;
        }
    }

    // Method to calculate the cost for moving the minute hand
    public static int calculateMinuteCost(int angleChange, int X, int Y) {
        if (angleChange <= 90) {
            return angleChange * X;
        } else {
            return (90 * X) + (angleChange - 90) * Y;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input initial time
        String time = scanner.nextLine();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Input number of queries
        int N = scanner.nextInt();

        // Input costs
        int A = scanner.nextInt();
        int B = scanner.nextInt();
        int P = scanner.nextInt();
        int Q = scanner.nextInt();
        int X = scanner.nextInt();
        int Y = scanner.nextInt();

        // Initialize total cost
        int totalCost = 0;

        // Process each query
        for (int i = 0; i < N; i++) {
            int targetAngle = scanner.nextInt();

            // Calculate the current angle between the hands
            int currentAngle = calculateCurrentAngle(hour, minute);

            // If the current angle matches the target, no movement is needed
            if (currentAngle == targetAngle) {
                continue;
            }

            // We can either move clockwise or counterclockwise to reach the target angle
            // Hour hand moves in 30 degree steps (move 1 hour forward or backward)
            int hourAngleClockwise = Math.abs(currentAngle - targetAngle);
            int hourAngleCounterClockwise = 360 - hourAngleClockwise;

            // Minute hand moves in 6 degree steps (move 1 minute forward or backward)
            int minuteAngleClockwise = Math.abs(currentAngle - targetAngle);
            int minuteAngleCounterClockwise = 360 - minuteAngleClockwise;

            // Calculate the minimum cost to reach the target angle
            int hourCostClockwise = calculateHourCost(hourAngleClockwise, P, Q);
            int hourCostCounterClockwise = calculateHourCost(hourAngleCounterClockwise, P, Q);
            int minuteCostClockwise = calculateMinuteCost(minuteAngleClockwise, X, Y);
            int minuteCostCounterClockwise = calculateMinuteCost(minuteAngleCounterClockwise, X, Y);

            // We pick the minimum cost between both clockwise and counterclockwise movements
            int cost = Math.min(hourCostClockwise + minuteCostCounterClockwise, hourCostCounterClockwise + minuteCostClockwise);
            totalCost += cost;
        }

        // Output the total minimum cost
        System.out.println(totalCost);
    }
}
