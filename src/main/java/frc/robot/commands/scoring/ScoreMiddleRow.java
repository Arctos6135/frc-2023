package frc.robot.commands.scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.CloseClaw;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.HoldRotate;
import frc.robot.commands.elevator.TimedRotate;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;

/**
 * Scores a game piece into the middle row. 
 */
public class ScoreMiddleRow extends SequentialCommandGroup {

    /**
     * Rotates arm up to middle row, extends arm, and releases game piece from claw. 
     * 
     * @param elevator
     * @param arm
     * @param claw
     * @param cube
     */
    public ScoreMiddleRow(Elevator elevator, Arm arm, Claw claw, boolean cube) {
        addCommands(
            new TimedRotate(arm, cube ? 0.75 : 0.75, -0.4, true), 
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new AutoExtend(elevator, cube ? 1.6 : 1.6, true), 
                    new OpenClaw(claw, 1.0),
                    new ParallelCommandGroup(
                        new AutoExtend(elevator, cube ? 1.6 : 1.6, false),
                        new CloseClaw(claw, 1.0)
                    ) 
                ), 
                new HoldRotate(arm, 6.0, false)
            )
        );
    }
}
