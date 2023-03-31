package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;


public class EncodedElevator extends CommandBase {
    private final Elevator elevator;
    private final double rotations;
    private final double SPEED = -0.5;

    public EncodedElevator(Elevator elevator, double rotations) {
        this.elevator = elevator;
        this.rotations = rotations;

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        if (elevator.getAngle() > rotations) {
            System.out.printf("Running encoded extend with angle %f, too big\n", elevator.getAngle());
            elevator.setMotor(-SPEED);
        } else {
            System.out.printf("Running encoded extend with angle %f, too small\n", elevator.getAngle());
            elevator.setMotor(SPEED);
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.elevator.setMotor(0);
    }

    @Override
    public boolean isFinished() {
        return this.elevator.getAngle() >= this.rotations;
    }
}
