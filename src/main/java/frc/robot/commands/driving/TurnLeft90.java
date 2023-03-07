package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
/*
 * Timer based command to rotate the robot n degrees to the left
 */
public class TurnLeft90 extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed = 0.75;
    private final double leftDegrees;
    private final double time;
    private double timeLimit;

    public TurnLeft90(Drivetrain drivetrain, double leftDegrees) {
        this.drivetrain = drivetrain;
        this.leftDegrees = leftDegrees;
        //Math is probably wrong
        this.time = (leftDegrees / 360 ) // 1 for a full circle, 0.25 for 90 degrees, etc
            * (((DriveConstants.CHASSIS_WIDTH * Math.PI) / DriveConstants.WHEEL_CIRCUMFERENCE) / 2) // the number of wheel rotations for both wheels to complete a circle
            / speed; // the speed

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        timeLimit = this.time + Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(0, speed, 1);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= timeLimit;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0, 0);
    }
}