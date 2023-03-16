package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.constants.ArmConstants;
// import frc.robot.subsystems.Arm;

public class TeleopArm extends CommandBase{
    
    /* private final XboxController controller;
    private final int Y_AXIS;
    // private final Arm arm;

    /* public TeleopArm(Arm arm, XboxController operatorController, int upDownAxis){
        this.controller = operatorController;
        this.Y_AXIS = upDownAxis;
        // this.arm = arm;
    } */ 

    /*
    public void execute(){
        double y = controller.getRawAxis(Y_AXIS);
        double armSpeed = y * ArmConstants.ARM_SPEED_FACTOR;

        arm.setSpeed(armSpeed);
    }
    */
}
