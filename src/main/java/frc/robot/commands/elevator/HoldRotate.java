package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class HoldRotate extends CommandBase {
    private final Arm arm; 
    private double holdTime; 
    private double initialTime;
    private boolean armFlipped;  

    public static final double HOLD_SPEED = 0.5;

    public HoldRotate(Arm arm, double holdTime, boolean armFlipped) {
        this.arm = arm; 
        this.holdTime = holdTime; 
        this.armFlipped = armFlipped; 

        addRequirements(arm);
    }

    @Override 
    public void initialize() {
        this.initialTime = Timer.getFPGATimestamp(); 
        this.arm.setMotor(armFlipped ? -HOLD_SPEED : HOLD_SPEED);
    }

    @Override 
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - this.initialTime >= this.holdTime; 
    }

    @Override 
    public void end(boolean interrupted) {
        this.arm.setMotor(0);
    }
    
}
