package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class TimedElevator extends CommandBase{
    private final Elevator elevator;
    private double startTime;
    private double time;
    private boolean extend;

    private boolean isFinished;

    public TimedElevator(Elevator elevator, double time, boolean extend) {
        this.elevator = elevator;
        this.time = time;
        this.extend = extend; 
        this.isFinished = false; 

        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        if (Math.abs(Timer.getFPGATimestamp() - startTime) > time) {
            this.elevator.setMotor(extend ? 0.3 : -0.3);
            System.out.printf("Extending elevator");
            DriverStation.reportWarning("Extending elevator", false);
        }
        
        else {
            this.elevator.setMotor(0);
            this.isFinished = true; 
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.elevator.setMotor(0);
    }

    @Override
    public boolean isFinished() {
        return this.isFinished; 
    }

}
