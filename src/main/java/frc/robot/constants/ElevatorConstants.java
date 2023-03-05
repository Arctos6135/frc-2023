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
}
