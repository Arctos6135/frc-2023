package frc.robot.commands.scoring;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.CloseClaw;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.commands.elevator.HoldRotate;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;

public class SubstationExit extends SequentialCommandGroup {

    public SubstationExit(Elevator elevator, Arm arm, Claw claw) {
        addCommands(
            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new CloseClaw(claw, 2),
                    new AutoExtend(elevator, 0.0, false)
                ), 
                new HoldRotate(arm, 10.0, false)
            )
            
        );
    }
}
