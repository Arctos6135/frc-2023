package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.util.Dampener;

public class Rotate extends CommandBase {
    private final Arm arm;
    
    public final XboxController controller;
    
    private final int ROTATION_AXIS;
    private final int HOLD_BUTTON;

    private final Dampener dampener;
    
    public Rotate(Arm arm, XboxController operatorController, int rotateAxis, int holdButton) {
        this.arm = arm;
        this.controller = operatorController;
        this.ROTATION_AXIS = rotateAxis;
        this.HOLD_BUTTON = holdButton;
        this.dampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);

        addRequirements(arm);
    }
    
    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
        if (controller.getRawButton(HOLD_BUTTON)) {
            this.arm.setMotor(ElevatorConstants.HOLD_FACTOR);
        } else {
            double rotation = dampener.smoothDampen(controller.getRawAxis(ROTATION_AXIS));
            this.arm.setMotor(rotation);
        } 
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