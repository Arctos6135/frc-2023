package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Elevator;
import frc.robot.util.Dampener;

public class TeleopExtend extends CommandBase {
    private final Elevator elevator; 

    public final XboxController controller;
    
    private final int EXTENSION_AXIS;

    private final Dampener dampener;

    public TeleopExtend(Elevator elevator, XboxController operatorController, int extendAxis) {
        this.elevator = elevator;
        this.controller = operatorController;
        this.EXTENSION_AXIS = extendAxis;
        this.dampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);

        addRequirements(elevator);
    }

    @Override
    public void initialize() {

    }
    
    @Override 
    public void execute() {
        double extension = dampener.dampen(controller.getRawAxis(EXTENSION_AXIS));

        this.elevator.setMotor(extension);
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }

    @Override 
    public void end(boolean interrupted) {
        this.elevator.stopMotor();
    }
}
