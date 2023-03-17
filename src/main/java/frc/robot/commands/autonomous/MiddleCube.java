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
import frc.robot.constants.ClawConstants;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

// THIS IS THE ACTUAL AUTO, DONT RUN OTHER RUNS
public class MiddleCube extends SequentialCommandGroup {
    public static final double openClawTime = ClawConstants.OPEN_CLAW_TIME; 
    public static final double closeClawTime = ClawConstants.OPEN_CLAW_TIME;

    /**
     * Drops game piece into low row. 
     * Drives to intake second game piece. 
     * Places game piece on middle row. 
     * 
     * @param drivetrain
     * @param arm
     * @param claw
     * @param elevator
     */
    public MiddleCube(Drivetrain drivetrain, Arm arm, Claw claw, Elevator elevator, boolean cube) {
        addCommands(
            new TimedRotate(arm, cube ? 0.75 : 0.75, -0.4, true), 
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new AutoExtend(elevator, cube ? 1.6 : 1.6, true), 
                    new CloseClaw(claw, 1.0),
                    new AutoExtend(elevator, cube ? 1.6 : 1.6, false)
                ), 
                new HoldRotate(arm, 6.0, false)
            ),
            new DriveForwardEncoded(drivetrain,0.5, 18 * 12)
        );
    }
}
