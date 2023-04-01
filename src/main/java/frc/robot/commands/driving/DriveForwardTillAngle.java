package frc.robot.commands.driving;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardTillAngle extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed;
    private final double targetAngle;

    /** 
     * @param speed the speed of the robot in percent [-1, 1]
     * @param angle the absolute value of the angle the robot should drive until it reaches, in radians, in the range [0, 2Pi]
    */
    public DriveForwardTillAngle(Drivetrain drivetrain, double speed, double angle) {
        this.speed = speed;
        this.drivetrain = drivetrain;
        this.targetAngle = angle;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetEncoders();
        drivetrain.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void execute() {
        System.out.printf("Arcade drive with speed %f\n", speed);
        drivetrain.arcadeDrive(speed, 0);
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(drivetrain.getPitch()) > targetAngle) {
            System.out.printf("finished driving till rotated %f radians\n", this.targetAngle);
            return true;
        } else {
            System.out.printf("Read angle %f, not stopping\n", drivetrain.getPitch());
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("terminating command");
        drivetrain.arcadeDrive(0, 0);
    }
}