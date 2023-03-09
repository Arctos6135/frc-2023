package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Arm;
import frc.robot.util.Dampener;

public class Rotate extends CommandBase {
    private final Arm arm;
    
    public final XboxController controller;
    
    private final int ROTATION_AXIS;

    private final Dampener dampener;
    
    public Rotate(Arm arm, XboxController operatorController, int rotateAxis) {
        this.arm = arm;
        this.controller = operatorController;
        this.ROTATION_AXIS = rotateAxis;
        this.dampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, 3);

        addRequirements(arm);
    }
    
    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
        double rotation = dampener.dampen(controller.getRawAxis(ROTATION_AXIS));

        // this probably wont work!
        // this.arm.setAngle(rotation * 90 + 90);

        // this.arm.setAngle(10);
        this.arm.setMotor(rotation);
    }
}
