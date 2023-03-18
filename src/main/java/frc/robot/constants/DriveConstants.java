package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController;

public class DriveConstants {
    public static final int ENGAGE_TRIGGER = XboxController.Button.kA.value;
    public static double DEFAULT_ACCELERATION_GAIN = 3.6 / 60; // 3.6 power per second (as in goes from 0 to 1 in ~1/3 of a second)
    public static int DRIVER_CONTROLLER = 0;
    public static int OPERATOR_CONTROLLER = 1;

    public static int RIGHT_MASTER = 1;
    public static int LEFT_MASTER = 4;
    public static int RIGHT_FOLLOWER = 3;
    public static int LEFT_FOLLOWER = 2;

    public static double CONTROLLER_DEADZONE = 0.15;
    public static double FWD_REV_DAMPENING = 0.75;
    public static double LEFT_RIGHT_DAMPENING = 0.75;

    public static int DRIVE_FWD_REV = XboxController.Axis.kLeftY.value; 
    public static int DRIVE_LEFT_RIGHT = XboxController.Axis.kRightX.value; 
    public static int PRECISION_DRIVE_TOGGLE = XboxController.Button.kX.value;
    public static int BOOST_DRIVE_HOLD = XboxController.Axis.kLeftTrigger.value; 
    // public static int TAPE_AUTO_ALIGN = XboxController.Button.kA.value; 
    public static int APRIL_TAG_AUTO_ALIGN = XboxController.Button.kB.value; 

    // Drivetrain Constants (Inches)
    public static double WHEEL_DIAMETER = 6.0; 
    public static double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; 
    public static double GEARBOX_RATIO = 1 / 8.45; 
    public static double POSITION_CONVERSION_FACTOR = WHEEL_CIRCUMFERENCE * GEARBOX_RATIO; 
    public static double DRIVE_TOLERANCE = 1.0; 
    public static double CHASSIS_WIDTH = 28;
    public static double ROTATIONS_PER_SECOND = 100;

    public static double TURNING_SPEED = 0.05;

}
