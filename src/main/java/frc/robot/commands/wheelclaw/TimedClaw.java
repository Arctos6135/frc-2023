package frc.robot.commands.wheelclaw;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelClaw;

public class TimedClaw extends CommandBase {
    private final WheelClaw wheelClaw; 

    private double time; 
    private double initialTime; 

    private boolean intake; 

    private boolean isFinished = false; 

    public static double clawSpeed = 0.5; 

    public TimedClaw(WheelClaw wheelClaw, double time, boolean intake) {
        this.wheelClaw = wheelClaw; 
        this.time = time; 
        this.intake = intake; 

        addRequirements(wheelClaw);
    }

    @Override 
    public void initialize() {
        initialTime = Timer.getFPGATimestamp(); 
    }

    @Override 
    public void execute() {
        if (Math.abs(initialTime - Timer.getFPGATimestamp()) < time) {
            wheelClaw.setMotorSpeed(intake ? clawSpeed : -clawSpeed);
        } else {
            isFinished = true; 
        }
    }

    @Override 
    public void end(boolean interrupted) {
        wheelClaw.setMotorSpeed(0);
    }

    @Override 
    public boolean isFinished() {
        return isFinished;
    }
}
