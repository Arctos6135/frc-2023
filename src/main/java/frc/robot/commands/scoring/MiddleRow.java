package frc.robot.commands.scoring;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.elevator.AutoRotate;
import frc.robot.commands.elevator.HoldRotate;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;

/**
 * Scores a cube into the middle row. 
 */
public class MiddleRow extends SequentialCommandGroup {
    private final Elevator elevator; 
    private final Arm arm; 
    private final Claw claw; 

    private boolean cube; 

    public MiddleRow(Elevator elevator, Arm arm, Claw claw, boolean cube) {
        this.elevator = elevator; 
        this.arm = arm; 
        this.claw = claw; 
        this.cube = cube; 

        addCommands(
            new AutoRotate(this.arm, this.cube ? 
                ElevatorConstants.ROTATION_MIDDLE_LEVEL_CUBE : ElevatorConstants.ROTATION_MIDDLE_LEVEL_CONE), 
            new ParallelCommandGroup(
                new OpenClaw(this.claw, ClawConstants.OPEN_CLAW_TIME), 
                new HoldRotate(arm, ElevatorConstants.ARM_HOLD_TIME, false)
            ), 
            new AutoRotate(this.arm, this.cube ? 
                -ElevatorConstants.ROTATION_MIDDLE_LEVEL_CUBE : -ElevatorConstants.ROTATION_MIDDLE_LEVEL_CONE)
        );
    }
}
