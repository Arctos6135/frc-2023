package frc.robot.commands.scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.commands.elevator.HoldRotate;
import frc.robot.commands.elevator.TimedRotate;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;

public class SubstationIntake extends SequentialCommandGroup {
    
    public SubstationIntake(Elevator elevator, Arm arm, Claw claw) {
        addCommands(
            new TimedRotate(arm, 0.45, -0.4, true),
                new ParallelCommandGroup(
                    new ParallelCommandGroup(
                        new AutoExtend(elevator, 0.0,true), 
                        new OpenClaw(claw, 1.4)
                    ), 
                    new HoldRotate(arm, 10.0, false)
            )
        );
    }
}
