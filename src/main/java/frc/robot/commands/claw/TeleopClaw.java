package frc.robot.commands.claw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;

public class TeleopClaw extends CommandBase {
    private final Claw claw; 
    private int buttonOpen; 
    private int buttonClose; 
    
    private XboxController operatorController; 

    public TeleopClaw(Claw claw, XboxController operatorController, int buttonOpen, int buttonClose) {
        this.claw = claw; 
        this.buttonOpen = buttonOpen; 
        this.buttonClose = buttonClose; 
        this.operatorController = operatorController; 

        addRequirements(claw);
    }

    @Override 
    public void execute() {
        if (operatorController.getRawButton(buttonOpen) && operatorController.getRawButton(buttonClose)) {
            claw.setSpeed(0);
        }
        
        else if (operatorController.getRawButton(buttonOpen)) {
            claw.setSpeed(0.8);
        }

        else if (operatorController.getRawButton(buttonClose)) {
            claw.setSpeed(-0.8); 
        } 

        else {
            claw.setSpeed(0); 
        }
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }

    @Override 
    public void end(boolean interrupted) {
        claw.setSpeed(0);
    }
}
