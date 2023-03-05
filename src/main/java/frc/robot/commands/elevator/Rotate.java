package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.TeleopDrive;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Arm;

public class Rotate extends CommandBase {
    private final Arm arm;
    
    public final XboxController controller;
    
    private final int ROTATION_AXIS;
    
    public Rotate(Arm arm, XboxController operatorController, int rotateAxis) {
        this.arm = arm;
        this.controller = operatorController;
        this.ROTATION_AXIS = rotateAxis;

        addRequirements(arm);
    }
    
    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
        double rotation = TeleopDrive.applyDeadband(controller.getRawAxis(ROTATION_AXIS), 
                DriveConstants.CONTROLLER_DEADZONE);
        
        rotation = Math.copySign(Math.pow(rotation, 2), rotation); 

        this.arm.setMotor(rotation);
    }
}
