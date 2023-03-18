package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.CloseClaw;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.commands.elevator.HoldRotate;
import frc.robot.commands.elevator.TimedRotate;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class MiddleScoreEngage extends SequentialCommandGroup {
    public MiddleScoreEngage(Drivetrain drivetrain, Arm arm, Claw claw,
        Elevator elevator, boolean cube) {
        
        addCommands(
            new CloseClaw(claw, 0.5),
            new TimedRotate(arm, cube ? 0.75 : 0.85, -0.4, true), 
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new AutoExtend(elevator, cube ? 1.6 : 1.6, true),
                    new OpenClaw(claw, 0.5), 
                    new AutoExtend(elevator, cube ? 1.6 : 1.6, false)
                ), 
                new HoldRotate(arm, 10.0, false)
            ), 
            new DriveForwardEncoded(drivetrain, 0.5, -FieldConstants.CENTER_OF_CHARGE_STATION),
            new Engage(drivetrain)
        );
    }
}
