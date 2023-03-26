package frc.robot.commands.gyroBalance;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class BangBangBalance extends CommandBase {
    private final Drivetrain drivetrain;
    private final double HIGH_LIMIT = 3 * Math.PI / 180;
    private final double LOW_LIMIT = 3 * Math.PI / 180;
    private final double SPEED = 0.1;

    /** Balance the robot by driving forwards if tilted up, backwards if tilted down
    */
    public BangBangBalance(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.printf("Initializing bang bang balancer\n");
        drivetrain.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void execute() {
        double angle = drivetrain.getPitch();

        if (angle > HIGH_LIMIT) {
            System.out.printf("Angle %f too high, forwarding\n", angle);
            drivetrain.arcadeDrive(SPEED, 0);
        } else if (angle < LOW_LIMIT) {
            System.out.printf("Angle %f too low, reversing\n", angle);
            drivetrain.arcadeDrive(-SPEED, 0);
        } else {
            System.out.printf("Angle %f just right :)\n", angle);
            drivetrain.arcadeDrive(0, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("bang bang balance terminated\n");
    }
}