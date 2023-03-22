package frc.robot.commands;

import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ClawConstants;
import frc.robot.subsystems.Claw;

public class TeleopClaw extends CommandBase {
    private final Claw claw;
    private final XboxController controller;   
    private int buttonGather; 
    private int buttonRelease; 

    public TeleopClaw(Claw claw, XboxController operatorController, int buttonGather, int buttonRelease) {
        this.claw = claw;
        this.controller = operatorController;
        this.buttonGather = buttonGather;
        this.buttonRelease = buttonRelease;
        addRequirements(claw);
    }

    @Override
    public void execute() {
        // I dont know if .getRawButton() does what I think it does...
        if (controller.getRawButton(buttonGather) == true) {
            this.claw.gather();
        } else if (controller.getRawButton(buttonRelease) == true) {
            this.claw.release();
        } else {
            this.claw.stop();
        }
    };

    @Override 
    public boolean isFinished() {
        return false; 
    }

    @Override 
    public void end(boolean interrupted) {
        claw.setMotors(0);
    }
}