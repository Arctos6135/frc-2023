package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.CloseClaw;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.commands.elevator.AutoRotate;
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
     * Drops game piece into low row. 
     * Drives to intake second game piece. 
     * Places game piece on middle row. 
     * 
     * @param drivetrain
     * @param arm
     * @param claw
     * @param elevator
     */
    public MiddleCube(Drivetrain drivetrain, Arm arm, Claw claw, Elevator elevator) {
        // I don't know what this is supposed to do, we can fix if pid arm works :)
        /*
        addCommands(
            new AutoRotate(arm, Math.PI / 2),
            new AutoExtend(elevator, 1.5, true),
            new DriveForwardEncoded(drivetrain, 0.5, FieldConstants.AUTO_GAME_PIECE),
            new CloseClaw(claw, 2.0),
            new DriveForwardEncoded(drivetrain, 0.5, -FieldConstants.AUTO_GAME_PIECE),
            new TimedRotate(arm, 0.85, -0.4, true),
            new ParallelCommandGroup(
                new HoldRotate(arm, 2.5, false), 
                new OpenClaw(claw, 2.0)
            ), 
            new TimedRotate(arm, 1.0, 0.10, false)
           
        );
        */
    }
}
