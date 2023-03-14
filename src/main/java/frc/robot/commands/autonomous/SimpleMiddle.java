package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.CloseClaw;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class SimpleMiddle extends SequentialCommandGroup {
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
    public SimpleMiddle(Drivetrain drivetrain, Arm arm, Claw claw, Elevator elevator) {
        // standard caveat about not understanding what this auto does :)
        /*
        addCommands(
            new CloseClaw(claw, 2.0), 
            new TimedRotate(arm, 1.25, -0.4, true), 
            new AutoExtend(elevator, 1.5, true), 
            new OpenClaw(claw, 2.0), 
            new AutoExtend(elevator, 1.5, false),
            new TimedRotate(arm, 1.0, 0.10, false), 
            new DriveForwardEncoded(drivetrain, 0.5, -FieldConstants.AUTO_GAME_PIECE)
        );
        */
    }
}
