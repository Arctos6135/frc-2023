package frc.robot.commands.driving;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardTillAngle extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed;
    private final double targetAngle;
    private final double maxSafeDistance = 14 * 12;

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
        if (Math.abs(drivetrain.getPosition()) > maxSafeDistance) {
            System.out.printf("Was driving forward till angle %f, stopped robot because it drove more than the max safe distance (encoders reading %f)\n", targetAngle, drivetrain.getPosition());
            drivetrain.arcadeDrive(0, 0);
        } else {
            //System.out.printf("Driving forward till angle %f at speed %f, reading angle %f\n", targetAngle, speed, drivetrain.getPitch());
            drivetrain.arcadeDrive(speed, 0);
        }
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(drivetrain.getPitch()) > Math.abs(targetAngle)) {
            System.out.printf("Finished driving till rotated %f radians\n", this.targetAngle);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("terminating command");
        drivetrain.arcadeDrive(0, 0);
    }
}