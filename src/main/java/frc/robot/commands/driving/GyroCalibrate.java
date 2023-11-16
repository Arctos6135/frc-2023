package frc.robot.commands.driving;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class GyroCalibrate extends CommandBase {
    private final Drivetrain drivetrain;

    /** 
     * @param speed the speed of the robot in percent [-1, 1]
     * @param angle the absolute value of the angle the robot should drive until it reaches, in radians, in the range [0, 2Pi]
    */
    public GyroCalibrate(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.calibrateGyro();
        drivetrain.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}