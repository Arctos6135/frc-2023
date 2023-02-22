package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

public class TeleopDrive extends CommandBase {
    private final Drivetrain drivetrain; 

    private final XboxController controller; 

    private final int X_AXIS; 
    private final int Y_AXIS; 

    private static boolean precisionDrive = true; 

    private static double precisionFactor = 0.5; 

    public TeleopDrive(Drivetrain drivetrain, XboxController driverController, int fwdRevAxis, int leftRightAxis) {
        this.drivetrain = drivetrain; 
        this.controller = driverController; 

        this.X_AXIS = leftRightAxis; 
        this.Y_AXIS = fwdRevAxis; 

        addRequirements(drivetrain);
    }

    public static double applyDeadband(double x, double deadband) {
        if (Math.abs(x) <= deadband) {
            return 0;
        }

        return Math.copySign(Math.abs(x) - deadband, x) / (1.0 - deadband);
    }

    @Override
    public void initialize() {

    }

    @Override 
    public void execute() {
        double y = applyDeadband(controller.getRawAxis(Y_AXIS),
                DriveConstants.CONTROLLER_DEADZONE);

        // Increase control by squaring input values. Negative values will, however, stay negative. 
        y = Math.copySign(y * y, y) * DriveConstants.FWD_REV_DAMPENING;

        double x = applyDeadband(controller.getRawAxis(X_AXIS), DriveConstants.CONTROLLER_DEADZONE);
        x = Math.copySign(x * x * x, x) * DriveConstants.LEFT_RIGHT_DAMPENING;

        drivetrain.arcadeDrive(-y, x, precisionDrive ? precisionFactor : 1.0);
    }

    public static boolean isPrecisionDrive() {
        return precisionDrive; 
    }

    public static void setPrecisionDrive(boolean precisionDrive) {
        TeleopDrive.precisionDrive = precisionDrive; 
    }

    public static void togglePrecisionDrive() {
        precisionDrive = !precisionDrive; 
    }

    public static void setPrecisionFactor(double factor) {
        precisionFactor = factor; 
    }

    public static double getPrecisionFactor() {
        return precisionFactor; 
    }
}
