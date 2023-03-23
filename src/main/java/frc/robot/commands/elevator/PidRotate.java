package frc.robot.commands.elevator;

import edu.wpi.first.networktables.GenericEntry;
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
    public PidRotate(Arm arm, GenericEntry entry) {
        this.arm = arm; 
        this.setpointAngle = entry.getDouble(0);
        
        addRequirements(arm); 
    }
    @Override 
    public void initialize() {
    }

    @Override 
    public void execute() {
        double pid = arm.getPIDController().calculate(
        this.arm.getAngle(), setpointAngle);
        this.arm.setMotor(pid);
        DriverStation.reportWarning(Double.toString(pid), false);
    }

    @Override 
    public boolean isFinished() {
        return arm.getPIDController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("PID finished");
    }
}
