package frc.robot.commands.scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.elevator.AutoExtend;
import frc.robot.commands.elevator.PidRotate;
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
            new PidRotate(this.arm, this.cube ? 
                ElevatorConstants.ROTATION_HIGH_LEVEL_CUBE : ElevatorConstants.ROTATION_HIGH_LEVEL_CONE), 
            new AutoExtend(elevator, ElevatorConstants.ELEVATOR_EXTENSION_TIME, true),
            new ParallelCommandGroup(
                new OpenClaw(this.claw, ClawConstants.OPEN_CLAW_TIME), 
                new HoldRotate(arm, ElevatorConstants.ARM_HOLD_TIME, true)
            ), 
            new AutoExtend(this.elevator, ElevatorConstants.ELEVATOR_EXTENSION_TIME, false),
            new PidRotate(this.arm, this.cube ? 
                -ElevatorConstants.ROTATION_HIGH_LEVEL_CUBE : -ElevatorConstants.ROTATION_HIGH_LEVEL_CONE)
        );
    }
}
