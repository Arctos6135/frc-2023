package frc.robot.util;

public class Clamp {
    public static double clamp(double x, double min, double max) {
        return Math.max(Math.min(x, max), min);
    }
}
