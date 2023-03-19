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

public class MiddleCube extends SequentialCommandGroup {
    public static final double openClawTime = ClawConstants.OPEN_CLAW_TIME; 
    public static final double closeClawTime = ClawConstants.OPEN_CLAW_TIME;

    /**
     * Start with cube in claw inside frame, up against grid
     * Lift arm up, release cube, drive backwards
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
                    new AutoExtend(elevator, cube ? 0.5 : 0.5, true), 
                    new OpenClaw(claw, 1.0),
                    new AutoExtend(elevator, cube ? 0.5 : 0.5, false)
                ), 
                new HoldRotate(arm, 6.0, false)
            ),
            new DriveForwardEncoded(drivetrain,0.5, -15 * 12)
        );
    }
}
