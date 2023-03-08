package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
/*
 * Timer based command to rotate the robot 90 degrees to the left
 */
public class Turn extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed;
    private final double time;
    private double timeLimit;

    /**
     * @param time number of seconds to make the bot turn for
     * @param speed power from [-1, 1] to turn at, positive is left
     */
    public Turn(Drivetrain drivetrain, double time, double speed) {
        this.drivetrain = drivetrain;
        this.time = time;
        this.speed = speed;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.printf("Initializing turn\n");
        timeLimit = this.time + Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        System.out.printf("Turning at %f with end time %f and curret time %f\n", speed, timeLimit, Timer.getFPGATimestamp());
        drivetrain.arcadeDrive(0, speed, 1);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= timeLimit;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.printf("Terminating turn\n");
        drivetrain.arcadeDrive(0, 0, 0);
    }
}