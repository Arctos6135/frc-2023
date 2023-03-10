package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class AutoExtend extends CommandBase {
    private final Elevator elevator; 

    public double initialTime; 
    public double extensionTime; 

    public static double extensionSpeed = 0.5; 

    public AutoExtend(Elevator elevator, double extensionTime) {
        this.elevator = elevator; 
        this.extensionTime = extensionTime; 

        addRequirements(elevator);
    }

    @Override 
    public void initialize() {
        this.initialTime = Timer.getFPGATimestamp(); 
        this.elevator.setMotor(extensionSpeed);
    }

    @Override 
    public void execute() {

    }

    @Override 
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - this.initialTime >= this.extensionTime; 
    }

    @Override 
    public void end(boolean interrupted) {
        this.elevator.setMotor(0);
    }
}
