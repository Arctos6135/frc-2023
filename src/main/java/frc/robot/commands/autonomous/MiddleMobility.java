package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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

public class MiddleMobility extends SequentialCommandGroup {
    /**
     * Holds game piece at start.
     * Rotates arm up and drops the game piece on to middle row. 
     * Drives back to exit community. 
     * 
     * @param drivetrain
     * @param arm
     * @param claw
     * @param elevator
     */
    public MiddleMobility(Drivetrain drivetrain, Arm arm, Claw claw, Elevator elevator) {
        addCommands(
            new CloseClaw(claw, 0.5), 
            new TimedRotate(arm, 0.75, -0.4, true), 
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new AutoExtend(elevator, 0.5, true), 
                    new OpenClaw(claw, 0.5), 
                    new AutoExtend(elevator, 0.5, false)
                ),
                new HoldRotate(arm, 10.0, false)
            ),
            // new TimedRotate(arm, 1.0, 0.10, false), 
            new DriveForwardEncoded(drivetrain, 0.5, -FieldConstants.AUTO_GAME_PIECE)
        );
    }
}
