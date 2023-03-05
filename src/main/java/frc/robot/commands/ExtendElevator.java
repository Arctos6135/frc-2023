package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Elevator;
import frc.robot.util.Dampener;

public class ExtendElevator extends CommandBase {
    private final Elevator elevator;
    private final XboxController operatorController;

    private final Dampener dampener;

    public ExtendElevator(Elevator elevator, XboxController operatorController) {
        this.elevator = elevator;
        this.operatorController = operatorController;
        this.dampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);
    }

    @Override
    public void execute() {
        double power = dampener.dampen(this.operatorController.getRawAxis(ElevatorConstants.ELEVATOR_CONTROL));
        this.elevator.setMotor(power);
    }
}
