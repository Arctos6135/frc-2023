package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;


public class EncodedElevator extends CommandBase { // 1.5in/rotation
    private final Elevator elevator;
    private double rotations;
    private double intialRotation;

    public EncodedElevator(Elevator elevator, double rotations) {
        this.elevator = elevator;
        this.rotations = rotations;

        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        intialRotation = this.elevator.getAngle();
    }

    @Override
    public void execute() {
        this.elevator.setMotor(.8);
    }

    @Override
    public void end(boolean interrupted) {
        this.elevator.setMotor(0);
    }

    @Override
    public boolean isFinished() {
        return this.elevator.getAngle() - this.intialRotation >= this.rotations;
    }
}
