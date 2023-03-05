package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.TeleopDrive;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Elevator;

public class Extend extends CommandBase {
    private final Elevator elevator; 

    public final XboxController controller;
    
    private final int EXTENSION_AXIS; 

    public Extend(Elevator elevator, XboxController operatorController, int extendAxis) {
        this.elevator = elevator;
        this.controller = operatorController;
        this.EXTENSION_AXIS = extendAxis;

        addRequirements(elevator);
    }

    @Override
    public void initialize() {

    }
    
    @Override 
    public void execute() {
        double extension = TeleopDrive.applyDeadband(controller.getRawAxis(EXTENSION_AXIS),
                DriveConstants.CONTROLLER_DEADZONE);

        extension = Math.copySign(Math.pow(extension, 2), extension);

        this.elevator.setElevatorMotor(extension);
    }
}
