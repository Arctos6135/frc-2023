package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ManualRotate extends CommandBase {
    private final Arm arm;

    private final XboxController operatorController; 

    private final int button; 

    public static double armSpeed = -0.4;

    public ManualRotate(Arm arm, XboxController operatorController, int button) {
        this.arm = arm; 
        this.operatorController = operatorController; 
        this.button = button; 
        
        addRequirements(arm);
    }

    @Override 
    public void execute() {
        if (operatorController.getRawButton(button)) 
            this.arm.setMotor(armSpeed);
        else 
            this.arm.setMotor(0); 
    }
}
