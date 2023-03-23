package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.TimedExtend;
import frc.robot.commands.wheelclaw.TimedClaw;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.WheelClaw;

/**
 * Starts with a preloaded cube. 
 * Moves the arm up to scoring position. 
 * Extends the arm to middle row. 
 * Outtakes the cube from the claw. 
 * Drives back outside of the community. 
 */
public class MiddleRow extends SequentialCommandGroup {
    public MiddleRow(Drivetrain drivetrain, Elevator elevator, Arm arm, WheelClaw claw) {
        addCommands(
            new TimedClaw(claw, 0.5, true), 
            new PidRotate(arm, 0), 
            new TimedExtend(elevator, 1.0, true), 
            new TimedClaw(claw, 1.0, false), 
            new DriveForwardEncoded(drivetrain, 0.5, -180)
        );
    }
}
