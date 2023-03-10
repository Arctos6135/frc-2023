package frc.robot.commands.driving;

import java.util.Map;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
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

    public SimpleWidget trans;
    public SimpleWidget rot;

    private Dampener xDampener;
    private Dampener yDampener;

    private static boolean precisionDrive = true; 
    private static double precisionFactor = 0.5; 

    public TeleopDrive(Drivetrain drivetrain, XboxController driverController, int fwdRevAxis, int leftRightAxis, ShuffleboardTab driveTab) {
        this.drivetrain = drivetrain; 
        this.controller = driverController; 

        this.X_AXIS = leftRightAxis; 
        this.Y_AXIS = fwdRevAxis;

        this.trans = driveTab.add("translation speed modifier", 1).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1));
        this.rot = driveTab.add("rotation speed modifier", 1).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1));

        this.xDampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);
        this.yDampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

    }

    @Override 
    public void execute() {
        double y = controller.getRawAxis(Y_AXIS);
        double y1 = -yDampener.smoothDampen(y) * (precisionDrive ? precisionFactor : 1.0); 
        double x = controller.getRawAxis(X_AXIS);
        double x1 = xDampener.smoothDampen(x) * (precisionDrive ? precisionFactor : 1.0); 

        drivetrain.arcadeDrive(y1, x1);
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
