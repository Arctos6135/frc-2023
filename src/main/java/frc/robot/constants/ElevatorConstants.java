package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController;

public class ElevatorConstants {
    public static final int OPERATOR_CONTROLLER = 1;

    public static final int ROTATE_MOTOR = 5;
    public static final int ELEVATOR_MOTOR = 6;

    public static final double SPEED_FACTOR = 0.7; // **USING
    public static final double ROTATE_FACTOR = 0.5;
    public static final double HOLD_FACTOR = -0.15;
    public static final double SCORE_SPEED = -0.4;

    public static final int HEX_ENCODER_PORT = 0;

    public static final double VERSAPLANETARY_GEARBOX_RATIO = 1.0 / 50.0;
    public static final int CHAIN_LINKS = 132; // CHECK VALUE
    public static final int HEX_ROTATION = 12;
    public static final double DISTANCE_PER_ROTATION_RADIANS = 2 * Math.PI / 250;
    public static final double ROTATION_TOLERANCE = DISTANCE_PER_ROTATION_RADIANS / 4;

    // Arm's "Home Setpoint" should be low level for intake. These values are in
    // radians and need testing.
    // These values were calculated with an arm length of 3 feet.
    public static final double ROTATION_LOW_LEVEL = 0;
    public static final double ROTATION_MIDDLE_LEVEL_CONE = 0.84;
    public static final double ROTATION_HIGH_LEVEL_CONE = 3.65;
    public static final double ROTATION_MIDDLE_LEVEL_CUBE = 0.70;
    public static final double ROTATION_HIGH_LEVEL_CUBE = 4.27;

    public static final double ARM_HOLD_TIME = 2.5;
    public static final double ELEVATOR_EXTENSION_TIME = 0.6;
    public static final double ELEVATOR_EXTENSION_SPEED = 0.75; // **USING

    public static final int ELEVATOR_LIMIT_SWITCH_PORT = 9;

    public static final double ENCODER_ANGLE_HIGHEST = 0; // Needs to be tested **USING
    public static final double ENCODER_ANGLE_LOWEST = 0; // Needs to be tested **USING
    public static final double DISTANCE_PER_ROTATION_RADIANS_ELEVATOR = 0; // Needs to be tested **USING

    public static final int ELEVATOR_ENCODER = 8;
}
