package frc.robot.util;

public class Dampener {
    double deadband;
    double power;

    public Dampener(double deadband, double power) {
        this.deadband = deadband;
        this.power = power;
    }

    private static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) <= deadband) {
            return 0;
        }

        return Math.copySign(Math.abs(value) - deadband, value) / (1.0 - deadband);
    }

    public double dampen(double value) {
        double dampened = Math.pow(value, power);
        double deadbanded = Dampener.applyDeadband(dampened, deadband);
        double signed = Math.copySign(deadbanded, value);

        return signed;
    }

    public double smoothDampen(double value) {
        double dampened = 2 * ((2 * Math.pow(Math.E, value)) / (Math.pow(Math.E, value) + 1) - 1);
        double deadbanded = Dampener.applyDeadband(dampened, deadband); 
        double signed = Math.copySign(deadbanded, value); 

        return signed;
    }
}
