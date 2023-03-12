package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Elevator;

public class AutoExtend extends CommandBase {
    private final Elevator elevator; 

    public double initialTime; 
    public double extensionTime; 
    public boolean extend; 

    public AutoExtend(Elevator elevator, double extensionTime, boolean extend) {
        this.elevator = elevator; 
        this.extensionTime = extensionTime; 
        this.extend = extend; 

        addRequirements(elevator);
    }

    @Override 
    public void initialize() {
        this.initialTime = Timer.getFPGATimestamp(); 
        this.elevator.setMotor(extend ? -ElevatorConstants.ELEVATOR_EXTENSION_SPEED : ElevatorConstants.ELEVATOR_EXTENSION_SPEED);
    }

    @Override 
    public void execute() {
        if (!(Math.abs(Timer.getFPGATimestamp() - this.initialTime) >= this.extensionTime)) {
            this.elevator.setMotor(extend ? -ElevatorConstants.ELEVATOR_EXTENSION_SPEED : ElevatorConstants.ELEVATOR_EXTENSION_SPEED);
        }
    }

    @Override 
    public boolean isFinished() {
        return Math.abs(Timer.getFPGATimestamp() - this.initialTime) >= this.extensionTime; 
    }

    @Override 
    public void end(boolean interrupted) {
        this.elevator.setMotor(0);
    }
}
