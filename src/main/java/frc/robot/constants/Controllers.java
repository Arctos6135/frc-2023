package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController;

public class Controllers {
    public static int DRIVE_FWD_REV = XboxController.Axis.kLeftY.value; 
    public static int DRIVE_LEFT_RIGHT = XboxController.Axis.kRightX.value; 
    public static int PRECISION_DRIVE_TOGGLE = XboxController.Button.kX.value;
    public static int BOOST_DRIVE_HOLD = XboxController.Button.kX.value;
    // public static int TAPE_AUTO_ALIGN = XboxController.Button.kA.value; 
    public static int APRIL_TAG_AUTO_ALIGN = XboxController.Button.kB.value; 
    public static final int BUMPER_INTAKE = XboxController.Button.kRightBumper.value; 
    public static final int BUMPER_OUTTAKE = XboxController.Button.kLeftBumper.value; 

    public static final int ROTATE_CONTROL = XboxController.Axis.kRightY.value;
    public static final int HOLD_ROTATION = XboxController.Button.kB.value;
    public static final int ELEVATOR_CONTROL = XboxController.Axis.kLeftY.value;
    public static final int SCORE_MIDDLE_CUBE = XboxController.Button.kX.value;
    public static final int SCORE_MIDDLE_CONE = XboxController.Button.kY.value;
    public static final int SUBSTATION_INTAKE = XboxController.Button.kA.value;

    public static final int OPEN_CLAW_BUTTON = XboxController.Button.kRightBumper.value;
    public static final int CLOSE_CLAW_BUTTON = XboxController.Button.kLeftBumper.value; 
}
