package frc.robot.commands.wheelclaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.Controllers;
import frc.robot.subsystems.WheelClaw;

public class TeleopIntake extends CommandBase {
    private final WheelClaw wheelClaw; 

    private final XboxController operatorController; 

    public TeleopIntake(WheelClaw wheelClaw, XboxController operatorController) {
        this.wheelClaw = wheelClaw; 
        this.operatorController = operatorController; 
        
        addRequirements(wheelClaw);
    }

    @Override 
    public void execute() {
        if (operatorController.getRawButton(Controllers.INTAKE_CLAW_BUTTON)) {
            wheelClaw.intake(); 
        }

        else if (operatorController.getRawButton(Controllers.OUTTAKE_CLAW_BUTTON)) {
            wheelClaw.outtake(); 
        }

        else {
            wheelClaw.setMotorSpeed(0);
        }
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }

    @Override 
    public void end(boolean interrupted) {
        wheelClaw.setMotorSpeed(0); 
    }
}
