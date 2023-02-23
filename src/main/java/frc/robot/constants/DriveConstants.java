package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController;

public class DriveConstants {
    public static int DRIVER_CONTROLLER = 0; 
    public static int OPERATOR_CONTROLLER = 1; 

    public static int RIGHT_MASTER = 1; 
    public static int LEFT_MASTER = 4;
    public static int RIGHT_FOLLOWER = 3; 
    public static int LEFT_FOLLOWER = 2;

    public static double CONTROLLER_DEADZONE = 0.15; 
    public static double FWD_REV_DAMPENING = 0.90; 
    public static double LEFT_RIGHT_DAMPENING = 0.50;

    // Driver Controls 
    public static int DRIVE_FWD_REV = XboxController.Axis.kLeftY.value; 
    public static int DRIVE_LEFT_RIGHT = XboxController.Axis.kRightX.value; 
    public static int PRECISION_DRIVE_TOGGLE = XboxController.Button.kX.value; 
    public static int BOOST_DRIVE_HOLD = XboxController.Axis.kLeftTrigger.value; 

    // Operator Controls 
    public static int ROTATE_ARM = XboxController.Axis.kRightY.value; 
    public static int EXTEND_ELEVATOR = XboxController.Axis.kLeftY.value; 
    public static int CLAW_SQUEEZE = XboxController.Button.kX.value; 
    public static int CLAW_RELEASE = XboxController.Button.kY.value; 
}
