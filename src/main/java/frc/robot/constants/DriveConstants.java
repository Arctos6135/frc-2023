package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.SPI.Port;

public class DriveConstants {
    public static final int ENGAGE_TRIGGER = XboxController.Button.kA.value;
    public static final int GYROSCOPE_PORT = 10;
    public static double DEFAULT_ACCELERATION_GAIN = 3.6 / 60; // 3.6 power per second (as in goes from 0 to 1 in ~1/3 of a second)
    public static int DRIVER_CONTROLLER = 0;
    public static int OPERATOR_CONTROLLER = 1;

    public static double CONTROLLER_DEADZONE = 0.15;
    public static double FWD_REV_DAMPENING = 0.75;
    public static double LEFT_RIGHT_DAMPENING = 0.75;

    // Drivetrain Constants (Inches)
    public static double WHEEL_DIAMETER = 6.0; 
    public static double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; 
    public static double GEARBOX_RATIO = 1 / 8.45; 
    public static double POSITION_CONVERSION_FACTOR = WHEEL_CIRCUMFERENCE * GEARBOX_RATIO; 
    public static double DRIVE_TOLERANCE = 1.0; 
    public static double CHASSIS_WIDTH = 28;
    public static double ROTATIONS_PER_SECOND = 100;

    // to be calibrated
    // measured in cubits
    public static double BUMPER_CORNER_ONE_THIRD_OF_DISTANCE_DIAGONALLY_ACROSS = 0.7506;

    public static double TURNING_SPEED = 0.05;

}
