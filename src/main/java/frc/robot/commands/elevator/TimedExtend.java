package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class TimedExtend extends CommandBase {
    private final Elevator elevator; 

    private double time; 
    private double initialTime; 

    private boolean extend; 

    private boolean isFinished = false; 

    public static double extensionSpeed = 0.25;

    public TimedExtend(Elevator elevator, double time, boolean extend) {
        this.elevator = elevator; 
        this.time = time; 
        this.extend = extend; 

        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        initialTime = Timer.getFPGATimestamp(); 
    }

    @Override 
    public void execute() {
        if (Math.abs(initialTime - Timer.getFPGATimestamp()) < time) {
            elevator.setMotor(extend ? extensionSpeed : -extensionSpeed);
        } 

        else {
            isFinished = true; 
        }
    }

    @Override 
    public void end(boolean interrupted) {
        elevator.setMotor(0);
    }

    @Override 
    public boolean isFinished() {
        return isFinished; 
    }
}
