package frc.robot.commands.driving;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoDrive extends CommandBase {
    private final Drivetrain drivetrain; 

    public AutoDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain; 

        addRequirements(drivetrain);
    }

}
