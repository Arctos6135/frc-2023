package frc.robot.commands.driving;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.VisionSystem;

public class TurnTowardsRefective extends CommandBase {
    private final Drivetrain drivetrain;
    private final VisionSystem vision;

    private final double targetArea;

    /** 
     * @param speed the speed of the robot in percent [0, 1]
     * @param distance the distance the robot should drive in inches. Setting the distance negative makes the bot go backwards
    */
    public TurnTowardsRefective(Drivetrain drivetrain, VisionSystem vision, double targetArea) {
        this.drivetrain = drivetrain;
        this.vision = vision;

        this.targetArea = targetArea;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetEncoders();
        drivetrain.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void execute() {
        /*
        if (vision.hasTargets()) {
            double rot = vision.getYaw() * 0.03;
            double trans = (targetArea - vision.getArea()) * 0.1;
            trans = Math.copySign(Math.min(Math.abs(trans), 0.2), trans);
            drivetrain.arcadeDrive(trans, rot);
            System.out.printf("Rotating at %f and translating at %f and getArea at %f\n", rot, trans, vision.getArea());

        } else {
            System.out.println("NO TARGET");
            drivetrain.arcadeDrive(0, 0);
        }
         */
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("terminating command");
        drivetrain.arcadeDrive(0, 0);
    }
}