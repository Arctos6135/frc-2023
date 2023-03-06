package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController;

public class ElevatorConstants {
    public static final int OPERATOR_CONTROLLER = 1;

    public static final int ROTATE_MOTOR = 5; 
    public static final int ELEVATOR_MOTOR = 6;

    public static final double SPEED_FACTOR = 0.5;
    public static final double ROTATE_FACTOR = 0.5;
    
    public static final int HEX_ENCODER_PORT = 7; 

    public static final int ROTATE_CONTROL = XboxController.Axis.kRightY.value;
    public static final int ELEVATOR_CONTROL = XboxController.Axis.kLeftY.value;
    public static final int AUTO_ROTATE_MIDDLE_CUBE = XboxController.Button.kB.value; 
    public static final int AUTO_ROTATE_MIDDLE_CONE = XboxController.Button.kA.value; 

    public static final double VERSAPLANETARY_GEARBOX_RATIO = 1.0 / 50.0; 
    public static final int CHAIN_LINKS = 132; // CHECK VALUE
    public static final int HEX_ROTATION = 12; 
    public static final double DISTANCE_PER_ROTATION_RADIANS = HEX_ROTATION / CHAIN_LINKS; 
    public static final double ROTATION_TOLERANCE = DISTANCE_PER_ROTATION_RADIANS / 4; 

    // Arm's "Home Setpoint" should be low level for intake. These values are in radians and need testing. 
    // These values were calculated with an arm length of 3 feet. 
    public static final double ROTATION_LOW_LEVEL = 0; 
    public static final double ROTATION_MIDDLE_LEVEL_CONE = 0.42; 
    public static final double ROTATION_HIGH_LEVEL_CONE = 0; 
    public static final double ROTATION_MIDDLE_LEVEL_CUBE = 0.35; 
    public static final double ROTATION_HIGH_LEVEL_CUBE = 0;
}
