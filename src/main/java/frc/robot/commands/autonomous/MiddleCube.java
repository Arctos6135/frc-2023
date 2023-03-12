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
    private final Drivetrain drivetrain; 
    private final Arm arm; 
    private final Claw claw; 
    private final Elevator elevator; 

    public static final double openClawTime = ClawConstants.OPEN_CLAW_TIME; 
    public static final double closeClawTime = ClawConstants.OPEN_CLAW_TIME;

    public MiddleCube(Drivetrain drivetrain, Arm arm, Claw claw, Elevator elevator) {
        this.drivetrain = drivetrain; 
        this.arm = arm; 
        this.claw = claw; 
        this.elevator = elevator; 

        addCommands(
            new TimedRotate(arm, 0.25, -0.4, true),
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new AutoExtend(elevator, 1.5, true),
                    new DriveForwardEncoded(this.drivetrain, 0.5, FieldConstants.AUTO_GAME_PIECE),
                    new CloseClaw(claw, 2.0),
                    new DriveForwardEncoded(this.drivetrain, 0.5, -FieldConstants.AUTO_GAME_PIECE)
                ),
                new HoldRotate(arm, 10.0, false)
            ),
            new TimedRotate(arm, 0.85, -0.4, true),
            new ParallelCommandGroup(
                new HoldRotate(arm, 2.5, false), 
                new OpenClaw(this.claw, 2.0)
            ), 
            new TimedRotate(arm, 1.0, 0.10, false)
           
        );
    }
}
