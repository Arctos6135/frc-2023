package frc.robot.commands.driving;

import java.util.Map;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private static boolean precisionDrive = false;
    private static double precisionFwdRev = 0.4;
    private static double precisionTurn = 0.8;


    public TeleopDrive(Drivetrain drivetrain, XboxController driverController, int fwdRevAxis, int leftRightAxis,
            ShuffleboardTab driveTab) {
        this.drivetrain = drivetrain;
        this.controller = driverController;

        this.X_AXIS = leftRightAxis;
        this.Y_AXIS = fwdRevAxis;
// sensitive turning
// 
        this.trans = driveTab.add("translation speed modifier", 1).withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 1));
        this.rot = driveTab.add("rotation speed modifier", 0.75).withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 1));

        this.xDampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 6);
        this.yDampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 4);

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double y = controller.getRawAxis(XboxController.Axis.kLeftY.value);
        double y1 = -yDampener.dampen(y) * (precisionDrive ? precisionFwdRev : 1.0);
        double x = controller.getRawAxis(XboxController.Axis.kRightX.value);
        double x1 = xDampener.dampen(x) * (precisionDrive ? precisionTurn : 1.0);

        SmartDashboard.putNumber("drive controller x", x1);
        SmartDashboard.putNumber("drive controller y", y1);
        
        double precisionFwdFactor = precisionDrive ? precisionFwdRev : 1;
        double precisionTurnFactor = precisionDrive ? precisionTurn : 1;

        drivetrain.arcadeDrive(y1 * 0.85 * precisionTurnFactor, x1 * 0.25 * precisionFwdFactor);
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

}
