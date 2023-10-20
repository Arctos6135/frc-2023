package frc.robot.commands.wheelclaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.Controllers;
import frc.robot.subsystems.WheelClaw;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Arm;
import frc.robot.commands.scoring.Score;
import frc.robot.commands.intake.Intake;

public class TeleopIntake extends CommandBase {
    
    private final WheelClaw wheelClaw; 
    private final XboxController operatorController; 
    private final Elevator elevator;
    private final Arm arm;

    public TeleopIntake(
        Arm arm, 
        WheelClaw wheelClaw, 
        XboxController operatorController, 
        Elevator elevator
    ) {
        this.wheelClaw = wheelClaw; 
        this.operatorController = operatorController;
        this.elevator = elevator;
        this.arm = arm;
        
        addRequirements(wheelClaw);
        addRequirements(elevator);
        addRequirements(arm);
    }

    @Override 
    public void execute() {

        // Arm Rotation Inputs (Check if ROTATE_CONTROL is the appropriate button)
        /* [-1, 0) -> ground intake
           (0, 1] -> double substation intake */
        if (Controllers.ROTATE_CONTROL < 0) {
            elevator.setPositionMode(false); // set pid mode
            elevator.setPosition(0.0);
            Intake.intakeGround(arm, elevator, wheelClaw);
            elevator.setPositionMode(true); // set pos mode
        } 
        
        else if (Controllers.ROTATE_CONTROL > 0) {
            elevator.setPositionMode(false);
            elevator.setPosition(0.0);
            Score.intakeDoubleSubstation(arm, wheelClaw);
            elevator.setPositionMode(true);
        }

        // Intake Inputs
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
