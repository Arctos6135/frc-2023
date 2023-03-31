package frc.robot.commands.gyroBalance;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class BangBangVelocity extends CommandBase {
    private final Drivetrain drivetrain;

    private final double HIGH_LIMIT = 3 * Math.PI / 180;
    private final double LOW_LIMIT = 3 * Math.PI / 180;
    private final double RATE_LIMIT = 0.2;
    private final double SPEED = 0.1;

    /** Balance the robot by driving only if both the angle is wrong and the charge station isn't moving in the right direction
    */
    public BangBangVelocity(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.printf("Initializing BBV balancer\n");
        drivetrain.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void execute() {
        double angle = drivetrain.getPitch();
        double angleRate = drivetrain.getPitchRate();

        if (angle > HIGH_LIMIT && !(angleRate < -RATE_LIMIT)) {
            System.out.printf("Angle %f too high and angle rate %f too high, forwarding\n", angle, angleRate);
            drivetrain.arcadeDrive(SPEED, 0);
        } else if (angle < LOW_LIMIT && !(angleRate > -RATE_LIMIT)) {
            System.out.printf("Angle %f too low and angle rate %f too low, reversing\n", angle, angleRate);
            drivetrain.arcadeDrive(-SPEED, 0);
        } else {
            System.out.printf("Angle %f and rate %f just right :)\n", angle, angleRate);
            drivetrain.arcadeDrive(0, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("BBV balance terminated\n");
    }
}