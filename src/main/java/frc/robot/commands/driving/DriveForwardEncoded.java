package frc.robot.commands.driving;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardEncoded extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed;
    private final double distance;

    /** 
     * @param speed the speed of the robot in percent [0, 1]
     * @param distance the distance the robot should drive in inches. Setting the distance negative makes the bot go backwards
    */
    public DriveForwardEncoded(Drivetrain drivetrain, double speed, double distance) {
        this.speed = Math.copySign(Math.abs(speed), distance);
        this.drivetrain = drivetrain;
        this.distance = Math.max(0, Math.abs(distance) - 14); // as long as the robot reaches full speed, it fairly consistently overshoots by ~14 inches

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetEncoders();
        drivetrain.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(speed, 0, 1);
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(drivetrain.getPosition()) > this.distance) {
            System.out.printf("finished driving %f meters\n", this.distance);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("terminating command");
        drivetrain.arcadeDrive(0, 0, 0);
    }
}