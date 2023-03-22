package frc.robot.constants;

public class ArmConstants {
    public static final double ARM_SPEED_FACTOR = 0.1;
    public static final double ARM_TOLERANCE = 5 * Math.PI / 180;
    public static final int LEFT_MOTOR_PORT = 8; 
    public static final int RIGHT_MOTOR_PORT = 5; 
    public static final double STOW_ANGLE = 0;

    public static final double VERSAPLANETARY_GEARBOX_RATIO = 1.0 / 50.0;
    public static final int CHAIN_LINKS = 132; // CHECK VALUE
    public static final int HEX_ROTATION = 12;
    public static final double DISTANCE_PER_ROTATION_RADIANS = 0.5712;
    public static final double ROTATION_TOLERANCE = DISTANCE_PER_ROTATION_RADIANS / 4;

    // Arm's "Home Setpoint" should be low level for intake. These values are in
    // radians and need testing.
    // These values were calculated with an arm length of 3 feet.
    public static final double ROTATION_LOW_LEVEL = 0;
    public static final double ROTATION_MIDDLE_LEVEL_CONE = 0.84;
    public static final double ROTATION_HIGH_LEVEL_CONE = 3.65;
    public static final double ROTATION_MIDDLE_LEVEL_CUBE = 0.70;
    public static final double ROTATION_HIGH_LEVEL_CUBE = 4.27;
}