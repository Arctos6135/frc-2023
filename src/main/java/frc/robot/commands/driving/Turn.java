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
     * @param degrees the number of degrees to turn the robot by. Turns left if positive, right if negative
     */
    public Turn(Drivetrain drivetrain, double degrees) {
        this.drivetrain = drivetrain;
        if (degrees > 0) {
            this.speed = DriveConstants.TURNING_SPEED;
        } else {
            this.speed = -DriveConstants.TURNING_SPEED;
        }
        this.time = 10;
        /*
            (360 / Math.abs(degrees)) // amount of a full circle we want to turn
            * (((DriveConstants.CHASSIS_WIDTH * Math.PI) / DriveConstants.WHEEL_CIRCUMFERENCE)) // the number of rotations for each wheel to complete full turn
            / (speed * DriveConstants.ROTATIONS_PER_SECOND); // rotations per second
*/
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
System.out.println("INIT\n");        timeLimit = this.time + Timer.getFPGATimestamp();
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
        System.out.println("END\n");         drivetrain.arcadeDrive(0, 0, 0);
    }
}