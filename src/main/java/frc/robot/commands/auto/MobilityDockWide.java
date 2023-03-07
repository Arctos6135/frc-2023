package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.driving.DriveForward;
import frc.robot.commands.driving.TurnLeft90;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class MobilityDockWide {
    public static Command getCommand(Drivetrain drivetrain, Elevator elevator, Arm arm, Claw claw) {
        Command sequence = new DriveForward(-0.2, 0.5, drivetrain)
            .andThen(new DriveForward(0.2, 1, drivetrain))
            .andThen(new TurnLeft90(drivetrain, 45))
            .andThen(new DriveForward(0.2, 1, drivetrain))
            .andThen(new TurnLeft90(drivetrain, -45))
            .andThen(new DriveForward(0.2, 1, drivetrain))
            .andThen(new TurnLeft90(drivetrain, -45))
            .andThen(new DriveForward(0.2, 1, drivetrain))
            .andThen(new TurnLeft90(drivetrain, 45));

        return sequence;
    }
}
