package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

public class AutoDrive extends CommandBase {
    private final Drivetrain drivetrain; 
    private double distance; 
    private boolean autoDriveFinished; 

    /**
     * Drives a specified distance with PID Control. 
     * 
     * @param drivetrain
     * @param distance in inches 
     */
    public AutoDrive(Drivetrain drivetrain, double distance) {
        this.drivetrain = drivetrain; 
        this.distance = distance; 
        this.autoDriveFinished = false; 

        addRequirements(drivetrain);
    }

    
    @Override 
    public void initialize() {
        this.drivetrain.resetEncoders();
        this.drivetrain.driveDistance(this.distance); 
    }

    @Override 
    public void execute() {
        if (Math.abs(distance - this.drivetrain.getPosition()) < DriveConstants.DRIVE_TOLERANCE) {
            this.autoDriveFinished = true; 
        }
    }

    @Override 
    public boolean isFinished() {
        return this.autoDriveFinished; 
    }

    @Override 
    public void end(boolean interrupted) {
        this.drivetrain.resetEncoders();
        this.drivetrain.arcadeDrive(0, 0, 0);
    }
}
