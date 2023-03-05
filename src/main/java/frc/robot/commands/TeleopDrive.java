package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Dampener;

public class TeleopDrive extends CommandBase {
    private final Drivetrain drivetrain; 

    private final XboxController controller; 

    private final int X_AXIS; 
    private final int Y_AXIS; 

    private final Dampener xDampener;
    private final Dampener yDampener;

    private static boolean precisionDrive = true; 
    private static double precisionFactor = 0.5; 

    public TeleopDrive(Drivetrain drivetrain, XboxController driverController, int fwdRevAxis, int leftRightAxis) {
        this.drivetrain = drivetrain; 
        this.controller = driverController; 

        this.X_AXIS = leftRightAxis; 
        this.Y_AXIS = fwdRevAxis;

        this.xDampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);
        this.yDampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

    }

    @Override 
    public void execute() {
        double y = yDampener.dampen(controller.getRawAxis(Y_AXIS));
        double x = xDampener.dampen(controller.getRawAxis(X_AXIS));

        drivetrain.arcadeDrive(-y, x, precisionDrive ? precisionFactor : 1.0);
    }

    public static boolean isPrecisionDrive() {
        return precisionDrive; 
    }

    public static void setPrecisionDrive(boolean precisionDrive) {
        TeleopDrive.precisionDrive = precisionDrive; 
    }

    public static Command togglePrecisionDrive() {
        precisionDrive = !precisionDrive;
        return null;  
    }

    public static void setPrecisionFactor(double factor) {
        precisionFactor = factor; 
    }

    public static double getPrecisionFactor() {
        return precisionFactor; 
    }
}
