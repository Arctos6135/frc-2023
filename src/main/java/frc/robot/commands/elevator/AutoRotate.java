package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class AutoRotate extends CommandBase {
    private final Arm arm;
    
    private double setpointAngle;

    /**
     * Autonomously rotates the arm to a set position for scoring. 
     * 
     * @param arm
     * @param angle in radians. 
     */
    public AutoRotate(Arm arm, double setpointAngle) {
        this.arm = arm; 
        this.setpointAngle = setpointAngle;
        
        addRequirements(arm); 
    }

    @Override 
    public void initialize() {
        this.arm.resetEncoder();
        this.arm.setAngle(setpointAngle);
    }

    @Override 
    public boolean isFinished() {
        return this.arm.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        this.arm.resetEncoder(); 
    }

}
