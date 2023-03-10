package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController.Button;

public class ClawConstants {
    public static final int CLAW_MOTOR = 7;
    public static final int OPEN_CLAW_BUTTON = Button.kRightBumper.value;
    public static final int CLOSE_CLAW_BUTTON = Button.kLeftBumper.value; 

    public static final int LIMIT_SWITCH_OPEN = 0; 
    public static final int LIMIT_SWITCH_CLOSE = 7; 

    // Values need to be tested
    public static final double OPEN_TIME = 0.0;
    public static final double CLOSE_TIME = 0.0;

    public static final double CLOSE_PERCENT_OUTPUT = 0.0;
    public static final double OPEN_PERCENT_OUTPUT = 0.0;

    public static final double OPEN_CLAW_TIME = 2.5; 

    public static final double CLAW_SPEED = 0.8; 
}
