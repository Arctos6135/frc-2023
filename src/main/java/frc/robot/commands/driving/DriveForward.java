package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;

public class DriveForward extends CommandBase {
    private final Drivetrain drivetrain;

    private final double speed;
    private final double time;
    private double maxTime;

    /** 
     * @param speed the speed of the robot in percent [-1, 1]
     * @param distance the distance the robot should drive in m
    */
    public DriveForward(double speed, double time, Drivetrain drivetrain) {
        this.speed = speed;
        this.drivetrain = drivetrain;
        // s = m / (m / s);
        this.time = time;


        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.printf("speed %f, time: %f\n", this.speed, this.time);

        maxTime = this.time + Timer.getFPGATimestamp();

        System.out.printf("INITIALIZING 1234567890 %f %f %f %f\n", speed, time, maxTime, Timer.getFPGATimestamp());
    }

    @Override
    public void execute() {
        System.out.printf("max time %f, current time %f\n", this.maxTime, Timer.getFPGATimestamp()); 
        drivetrain.arcadeDrive(speed, 0, 1);
    }

    @Override
    public boolean isFinished() {
        if (Timer.getFPGATimestamp() >= maxTime) {
            System.out.println("finished\n");
            return true;
        } else
            return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0, 0);
    }
}
