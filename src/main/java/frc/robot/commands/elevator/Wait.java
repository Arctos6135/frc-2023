package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class Wait extends CommandBase {
    private double endTime;
    private final double seconds;

    public Wait(double seconds) {
        this.seconds = seconds;
    }

    @Override 
    public void initialize() {
        endTime = Timer.getFPGATimestamp() + seconds;
    }

    @Override 
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= endTime; 
    }
}
