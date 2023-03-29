package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class TimedElevator extends CommandBase{
    private final Elevator elevator;
    private double startTime;
    private double time;

    public TimedElevator(Elevator elevator, double time) {
        this.elevator = elevator;
        this.time = time;
        
        addRequirements(elevator);
    }
    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        this.elevator.setMotor(0.2);
    }

    @Override
    public void end(boolean interrupted) {
        this.elevator.setMotor(0);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= time;
    }

}
