package frc.robot.constants;

import edu.wpi.first.math.util.Units;

public class VisionConstants {
    // Measurements in METERS 
    public static double CAMERA_HEIGHT_METERS = Units.inchesToMeters(36); 
    public static double APRIL_TAG_HEIGHT_METERS = Units.inchesToMeters(20.5); 
    public static double TAPE_HEIGHT_METERS = Units.inchesToMeters(23.5);
    public static double CAMERA_PITCH_RADIANS = 0;

    // Measurements in INCHES 
    // How far from the goal the robot should be.
    public static double GOAL_RANGE_INCHES = 16.0; 
}
