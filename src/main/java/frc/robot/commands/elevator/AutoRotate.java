package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class AutoRotate extends CommandBase {
    private final Arm arm;
    
    private double rotations; 

    public AutoRotate(Arm arm, double rotations) {
        this.arm = arm; 
        this.rotations = rotations;
        
        addRequirements(arm); 
    }

    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
        arm.setMotor(arm.getPIDController().calculate(
            arm.getEncoder().getDistance(), rotations
        ));
    }
}
