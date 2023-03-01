package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Elevator;

public class ExtendElevator extends CommandBase {
    private final Elevator elevator;
    private final XboxController operatorController;

    public ExtendElevator(Elevator elevator, XboxController operatorController) {
        this.elevator = elevator;
        this.operatorController = operatorController;
    }

    @Override
    public void execute() {
        this.elevator.setMotor(this.operatorController.getRawAxis(ElevatorConstants.ELEVATOR_CONTROL));
    }
}
