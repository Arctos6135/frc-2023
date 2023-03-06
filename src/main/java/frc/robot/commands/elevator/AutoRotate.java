package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;

public class AutoRotate extends CommandBase {
    private final Arm arm;
    
    private double setpointAngle; 

    private boolean isFinished;

    /**
     * Autonomously rotates the arm to a set position for scoring. 
     * 
     * @param arm
     * @param angle in radians. 
     */
    public AutoRotate(Arm arm, double setpointAngle) {
        this.arm = arm; 
        this.setpointAngle = setpointAngle;
        this.isFinished = false; 
        
        addRequirements(arm); 
    }

    @Override 
    public void initialize() {
        this.arm.getEncoder().reset();
    }

    @Override 
    public void execute() {
        double tolerance = arm.getEncoder().getDistance() - setpointAngle; 

        if (Math.abs(tolerance) <= ElevatorConstants.ROTATION_TOLERANCE) {
            arm.stopMotor(); 
            isFinished = true; 
        } 
        
        else {
            arm.setMotor(arm.getPIDController().calculate(arm.getEncoder().getDistance(), setpointAngle));
        }
    }

    @Override 
    public boolean isFinished() {
        return isFinished;
    }

    @Override 
    public void end(boolean interrupted) {
        this.arm.getEncoder().reset(); 
    }

}
