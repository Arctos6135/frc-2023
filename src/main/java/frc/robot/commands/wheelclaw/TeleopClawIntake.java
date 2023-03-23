package frc.robot.commands.wheelclaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelClaw;

public class TeleopClawIntake extends CommandBase {
    private final WheelClaw wheelClaw; 
    private final XboxController operatorController; 

    private int clawIntake, clawOuttake; 

    public static double clawSpeed = 0.5; 

    public TeleopClawIntake(WheelClaw wheelClaw, XboxController operatorController, 
        int clawIntake, int clawOuttake) {

        this.wheelClaw = wheelClaw; 
        this.operatorController = operatorController; 
        this.clawIntake = clawIntake; 
        this.clawOuttake = clawOuttake; 

        addRequirements(wheelClaw);
    }

    @Override 
    public void execute() {
        if (operatorController.getRawButton(clawIntake)) {
            this.wheelClaw.setMotorSpeed(clawSpeed);
        }

        else if (operatorController.getRawButton(clawOuttake)) {
            this.wheelClaw.setMotorSpeed(-clawSpeed);
        } 

        else {
            this.wheelClaw.setMotorSpeed(0);
        }
    }

    @Override 
    public boolean isFinished() {
        return false;
    }

    @Override 
    public void end(boolean interrupted) {
        this.wheelClaw.setMotorSpeed(0);
    }
    
}
