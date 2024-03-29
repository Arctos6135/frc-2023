package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;

public class PidExtend extends CommandBase {
    private final Elevator elevator;

    private double setpointAngle;
    private final double SPEED = 0.01;
    private final double TOLERANCE = 0.1;

    /**
     * Autonomously rotates the arm to a set position for scoring. 
     * 
     * @param arm
     * @param angle in radians. 
     */
    public PidExtend(Elevator elevator, double setpointAngle) {
        this.elevator = elevator; 
        this.setpointAngle = setpointAngle;
        
        addRequirements(elevator); 
    }

    @Override 
    public void execute() {
        if (elevator.getAngle() < setpointAngle) {
            System.out.printf("Angle %f too small, applying positive speed\n", elevator.getAngle());
            elevator.setMotor(SPEED);
        } else if (elevator.getAngle() > setpointAngle) {
            System.out.printf("Angle %f too large, applying negative speed\n", elevator.getAngle());
            elevator.setMotor(-SPEED);
        }
    }

    @Override 
    public boolean isFinished() {
        return Math.abs(elevator.getAngle() - setpointAngle) < TOLERANCE;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.printf("Finished extending\n");
        elevator.setMotor(0);
    }
}
