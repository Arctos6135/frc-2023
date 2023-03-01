package frc.robot.constants;

import edu.wpi.first.wpilibj.XboxController.Button;

public class ClawConstants {
    public static final int CLAW_MOTOR = 0;

    // Values need to be tested
    public static final double OPEN_FROM_CUBE_TIME = 0.0;
    public static final double OPEN_FROM_CONE_TIME = 0.0;
    public static final double CLOSE_ON_CUBE_TIME = 0.0;
    public static final double CLOSE_ON_CONE_TIME = 0.0;

    public static final double CLOSE_PERCENT_OUTPUT = 0.0;
    public static final double OPEN_PERCENT_OUTPUT = 0.0;

    public static final int OPEN_FROM_CUBE_BUTTON = Button.kX.value;
    public static final int CLOSE_ON_CUBE_BUTTON = Button.kA.value;
    public static final int OPEN_FROM_CONE_BUTTON = Button.kY.value;
    public static final int CLOSE_ON_CONE_BUTTON = Button.kB.value;
}
