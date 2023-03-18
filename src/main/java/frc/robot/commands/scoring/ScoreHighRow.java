package frc.robot.commands.scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.TimedRotate;
import frc.robot.commands.elevator.HoldRotate;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;

public class ScoreHighRow extends SequentialCommandGroup {
        private final Elevator elevator;
        private final Arm arm;
        private final Claw claw;

        private boolean cube;

        public ScoreHighRow(Elevator elevator, Arm arm, Claw claw, boolean cube) {
                this.elevator = elevator;
                this.arm = arm;
                this.claw = claw;
                this.cube = cube;

                addCommands(
                                new TimedRotate(arm, cube ? 1 : 0.75, -0.4, true),
                                new ParallelDeadlineGroup(
                                                new SequentialCommandGroup(
                                                                new AutoExtend(elevator, cube ? 0.5 : 0.5, true),
                                                                new OpenClaw(claw, 1.0),
                                                                new AutoExtend(elevator, cube ? 0.5 : 0.5, false)),
                                                new HoldRotate(arm, 6.0, false)));
        }
}
