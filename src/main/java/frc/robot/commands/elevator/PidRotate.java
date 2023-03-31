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
    public void execute() {
        double pid = arm.getPIDController().calculate(
            this.arm.getAngle(), setpointAngle);

        System.out.printf("Pid control with angle %f, pid %f\n", arm.getAngle(), pid);

        pid = Math.min(0.5, Math.max(pid, -0.5));

        this.arm.setMotor(-pid);
    }

    @Override 
    public boolean isFinished() {
        return Math.abs(this.arm.getAngle() - setpointAngle) < ArmConstants.ARM_TOLERANCE;
    }

}
