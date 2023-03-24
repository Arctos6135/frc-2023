package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.Arm;

public class PidRotate extends CommandBase {
    private final Arm arm;
    
    private double setpointAngle;

    private boolean setpointReached;

    /**
     * Autonomously rotates the arm to a set position for scoring. 
     * 
     * @param arm
     * @param angle in radians. 
     */
    public PidRotate(Arm arm, double setpointAngle) {
        this.arm = arm; 
        this.setpointAngle = setpointAngle;
        this.setpointReached = false; 
        
        addRequirements(arm); 
    }

    @Override 
    public void initialize() {
        this.arm.resetEncoder();
    }

    @Override 
    public void execute() {
        if (!setpointReached) {
            double pid = arm.getPIDController().calculate(
                this.arm.getAngle(), setpointAngle);
            this.arm.setMotor(pid);
            DriverStation.reportWarning(Double.toString(pid), false);

            if (Math.abs(this.arm.getEncoder().getDistance() - setpointAngle) < ArmConstants.ARM_TOLERANCE) {
                this.setpointReached = true; 
            }
        }
    }

    @Override 
    public boolean isFinished() {
        return this.setpointReached;
    }

    @Override
    public void end(boolean interrupted) {
        this.arm.resetEncoder(); 
    }

}
