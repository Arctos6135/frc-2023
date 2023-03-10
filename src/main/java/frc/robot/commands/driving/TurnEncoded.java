package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

/*
 * Encoder based command to rotate the robot 90 degrees to the left
 */
public class TurnEncoded extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed;
    private final double encoderDistance;

    /**
     * @param angle the (positive) number of degrees we want to turn
     * @param speed power from [-1, 1] to turn at, positive is left
     */
    public TurnEncoded(Drivetrain drivetrain, double angle, double speed) {
        this.drivetrain = drivetrain;
        this.speed = speed;
        this.encoderDistance = (360 / Math.abs(angle)) * Math.PI * DriveConstants.CHASSIS_WIDTH * 2;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.printf("Initializing turn\n");
    }

    @Override
    public void execute() {
        System.out.printf("Turning at %f with encoder distance %f and current rotation %f\n", speed, encoderDistance, this.drivetrain.getRotation());
        drivetrain.arcadeDrive(0, speed, 1);
    }

    @Override
    public boolean isFinished() {
        return this.drivetrain.getRotation() >= this.encoderDistance;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.printf("Terminating turn\n");
        drivetrain.arcadeDrive(0, 0, 0);
    }
}