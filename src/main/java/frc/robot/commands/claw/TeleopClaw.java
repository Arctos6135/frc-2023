package frc.robot.commands.claw;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ClawConstants;
import frc.robot.subsystems.Claw;

public class TeleopClaw extends CommandBase {
    private final Claw claw; 
    private int buttonOpen; 
    private int buttonClose; 

    private SlewRateLimiter limiter;
    
    private XboxController operatorController; 

    public TeleopClaw(Claw claw, XboxController operatorController, int buttonOpen, int buttonClose) {
        this.claw = claw; 
        this.buttonOpen = buttonOpen; 
        this.buttonClose = buttonClose; 
        this.operatorController = operatorController;
        limiter = new SlewRateLimiter(30);

        addRequirements(claw);
    }

    @Override
    public void execute() {
        double speed;
        if (operatorController.getRawButton(buttonOpen) && operatorController.getRawButton(buttonClose)) {
            speed = 0;
        } else if (operatorController.getRawButton(buttonOpen)) {
            speed = ClawConstants.CLAW_SPEED;
        } else if (operatorController.getRawButton(buttonClose)) {
            speed = -ClawConstants.CLAW_SPEED; 
        } else {
            speed = 0;
        }
        claw.setSpeed(limiter.calculate(speed));
    }

    @Override 
    public boolean isFinished() {
        return false; 
    }

    @Override 
    public void end(boolean interrupted) {
        claw.setSpeed(0);
    }
}
