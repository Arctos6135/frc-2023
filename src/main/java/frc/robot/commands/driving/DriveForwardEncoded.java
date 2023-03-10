package frc.robot.commands.driving;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardEncoded extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed;
    private final double distance;

    /** 
     * @param speed the speed of the robot in percent [-1, 1]
     * @param distance the distance the robot should drive in inches. Setting distance negative does nothing, if you want to bot to reverse, make speed negative
    */
    public DriveForwardEncoded(double speed, double distance, Drivetrain drivetrain) {
        this.speed = speed;
        this.drivetrain = drivetrain;
        this.distance = distance - 14;

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
        if (Math.abs(drivetrain.getPosition()) > Math.abs(this.distance)) {
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
        //drivetrain.setIdleMode(IdleMode.kCoast);
    }
}
