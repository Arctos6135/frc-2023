package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class TimedRotate extends CommandBase {
    private final Arm arm; 
    private double time; 
    private boolean up; 

    public double initialTime; 

    public static double speed = -0.4; 

    public TimedRotate(Arm arm, double time, boolean up) {
        this.arm = arm; 
        this.time = time; 
        this.up = up; 

        addRequirements(arm);
    }

    @Override 
    public void initialize() {
        this.arm.setMotor(up ? speed : -speed);
        this.initialTime = Timer.getFPGATimestamp(); 
    }

    @Override 
    public boolean isFinished() {
        return Math.abs(Timer.getFPGATimestamp() - this.initialTime) > this.time; 
    }

    @Override 
    public void end(boolean interrupted) {
        this.arm.setMotor(0);
    }
    
}
