package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.CloseClaw;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.HoldRotate;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class HighCube extends SequentialCommandGroup {
    private final Drivetrain drivetrain;
    private final Arm arm;
    private final Claw claw;
    private final Elevator elevator;

    public static final double openClawTime = ClawConstants.OPEN_CLAW_TIME;
    public static final double closeClawTime = ClawConstants.OPEN_CLAW_TIME;

    public static final double armAngle = Math.PI;
    public static final double extensionTime = 0.5;

    /**
     * Starts in community, drives forward to cube to intake, drives backwards to
     * score.
     * 
     * @param drivetrain
     * @param arm
     * @param claw
     * @param elevator
     */
    public HighCube(Drivetrain drivetrain, Arm arm, Claw claw, Elevator elevator) {
        this.drivetrain = drivetrain;
        this.arm = arm;
        this.claw = claw;
        this.elevator = elevator;

        addCommands(
                new DriveForwardEncoded(this.drivetrain, 0.5, FieldConstants.AUTO_GAME_PIECE),
                new CloseClaw(this.claw, closeClawTime),
                new DriveForwardEncoded(this.drivetrain, 0.5, -FieldConstants.AUTO_GAME_PIECE),
                new PidRotate(this.arm, armAngle),
                new ParallelCommandGroup(
                        new HoldRotate(this.arm, extensionTime, true),
                        new AutoExtend(this.elevator, extensionTime, true)),
                new OpenClaw(this.claw, openClawTime),
                new AutoExtend(this.elevator, extensionTime, false));
    }
}
