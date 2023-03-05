package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;

public class DriveForward extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed;
    private final double time;
    private double maxTime;

    public DriveForward(double speed, double distance, Drivetrain drivetrain) {
        this.speed = speed;
        this.drivetrain = drivetrain;
        this.time = distance / speed * DriveConstants.WHEEL_DIAMETER;
    }

    @Override
    public void initialize() {
        maxTime = this.time + Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(speed, 0, 1);
    }

    @Override
    public boolean isFinished() {
        if (Timer.getFPGATimestamp() >= maxTime)
            return true;
        else
            return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0, 0);
    }
}
