package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.util.Dampener;

public class TeleopRotate extends CommandBase {
    private final Arm arm;
    
    public final XboxController controller;
    
    private final int ROTATION_AXIS;

    private final Dampener dampener;
    
    public TeleopRotate(Arm arm, XboxController operatorController, int rotateAxis) {
        this.arm = arm;
        this.controller = operatorController;
        this.ROTATION_AXIS = rotateAxis;

        this.dampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);

        addRequirements(arm);
    }
    
    @Override 
    public void initialize() {
    }

    @Override 
    public void execute() {
        double rotation = dampener.smoothDampen(controller.getRawAxis(ROTATION_AXIS));
        this.arm.setMotor(rotation * 0.6);

        // DriverStation.reportWarning(Double.toString(this.arm.getEncoder().getDistance()), false);
        // DriverStation.reportWarning(Boolean.toString(this.arm.getEncoder().isConnected()), false);
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }

    @Override 
    public void end(boolean interrupted) {
        arm.setMotor(0); 
    }
}
