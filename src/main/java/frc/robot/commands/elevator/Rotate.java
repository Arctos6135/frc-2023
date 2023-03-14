package frc.robot.commands.elevator;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class Rotate extends CommandBase {
    private final Arm arm;
    private final XboxController operatorController; 
    private final int rotationAxis; 
    private final SlewRateLimiter rateLimiter;

    public Rotate(Arm arm, XboxController operatorController, int rotationAxis) {
        this.arm = arm; 
        this.operatorController = operatorController; 
        this.rotationAxis = rotationAxis; 
        this.rateLimiter = new SlewRateLimiter(3.0);
        
        addRequirements(arm);
    }

    @Override 
    public void execute() {
        double velocity = rateLimiter.calculate(operatorController.getRawAxis(rotationAxis)) * 0.1;
        this.arm.setAngle(this.arm.getAngle() + velocity);
    }
}