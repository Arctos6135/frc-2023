package frc.robot.commands.elevator;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.util.Dampener;

public class Rotate extends CommandBase {
    private final Arm arm;
    private final XboxController operatorController; 
    private final int rotationAxis; 
    private final SlewRateLimiter rateLimiter;
    private final Dampener dampener;

    public Rotate(Arm arm, XboxController operatorController, int rotationAxis) {
        this.arm = arm; 
        this.operatorController = operatorController; 
        this.rotationAxis = rotationAxis; 
        this.rateLimiter = new SlewRateLimiter(3.0);
        this.dampener = new Dampener(DriveConstants.CONTROLLER_DEADZONE, rotationAxis);
        
        addRequirements(arm);
    }

    @Override 
    public void execute() {
        double input = operatorController.getRawAxis(rotationAxis);
        double dampened = this.dampener.smoothDampen(input);
        double speed = rateLimiter.calculate(dampened) * ElevatorConstants.ARM_SPEED_FACTOR;
        this.arm.setMotor(speed);
    }
}