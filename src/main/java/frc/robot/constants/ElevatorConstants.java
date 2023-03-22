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

    public static final int HEX_ENCODER_PORT = 8;

    public static final int ROTATE_CONTROL = XboxController.Axis.kRightY.value;
    public static final int HOLD_ROTATION = XboxController.Button.kB.value;
    public static final int ELEVATOR_CONTROL = XboxController.Axis.kLeftY.value;
    public static final int SCORE_MIDDLE_CUBE = XboxController.Button.kX.value;
    public static final int SCORE_MIDDLE_CONE = XboxController.Button.kY.value;
    public static final int SUBSTATION_INTAKE = XboxController.Button.kA.value;

    public static final double ARM_HOLD_TIME = 2.5;
    public static final double ELEVATOR_EXTENSION_TIME = 0.6;
    public static final double ELEVATOR_EXTENSION_SPEED = 0.75; // **USING

    public static final int ELEVATOR_LIMIT_SWITCH_PORT = 9;

    public static final double ENCODER_ANGLE_HIGHEST = 0; // Needs to be tested **USING
    public static final double ENCODER_ANGLE_LOWEST = 0; // Needs to be tested **USING
    public static final double DISTANCE_PER_ROTATION_RADIANS_ELEVATOR = 0; // Needs to be tested **USING

    public static final int ELEVATOR_ENCODER = 10;
}
