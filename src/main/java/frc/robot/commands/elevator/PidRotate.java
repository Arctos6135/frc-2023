package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.Arm;

public class PidRotate extends CommandBase {
    private final Arm arm;
    
    private double setpointAngle;

    /**
     * Autonomously rotates the arm to a set position for scoring. 
     * 
     * @param arm
     * @param angle in radians. 
     */
    public PidRotate(Arm arm, double setpointAngle) {
        this.arm = arm; 
        this.setpointAngle = setpointAngle;
        
        addRequirements(arm); 
    }

    @Override
    public void initialize() {
        arm.setAutomaticPosition(setpointAngle);
    }

    @Override 
    public boolean isFinished() {
        return this.arm.atTarget();
    }

    // doesn't do anything to stop the motor when terminated
}
