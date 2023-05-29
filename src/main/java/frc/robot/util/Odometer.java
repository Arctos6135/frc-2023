package frc.robot.util;

public class Odometer {
    // measured in inches
    private double lastLeft = 0;
    // measured in inches
    private double lastRight = 0;
    // measured in radians
    private double lastAngle = 0;

    // measured in inches, inches, radians
    private double x = 0;
    private double y = 0;

    public Odometer(double startLeft, double startRight, double startAngle) {
        lastLeft = startLeft;
        lastRight = startRight;
        lastAngle = startAngle;
    }

    public void update(double newLeft, double newRight, double newAngle) {
        double distanceChange = ((newLeft - lastLeft) + (newRight - lastRight)) / 2;
        double averageAngle = (lastAngle + newAngle) / 2;

        x += distanceChange * Math.cos(averageAngle);
        y += distanceChange * Math.sin(averageAngle);
    }

    public void zeroPosition() {
        x = 0;
        y = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}