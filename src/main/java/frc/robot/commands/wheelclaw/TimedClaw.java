package frc.robot.commands.wheelclaw;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelClaw;


public class TimedClaw extends CommandBase {
    private final WheelClaw claw;
    private double duration;
    private double initialTime;
    private boolean finished = false;

    /**
     * Autonomously rotates the arm to a set position for scoring.
     * 
     * @param claw
     * @param duration in seconds.
     */
    public TimedClaw(WheelClaw claw, double duration) {
        this.claw = claw;
        this.duration = duration;

        addRequirements(claw);
    }

    @Override 
    public void initialize() {
        this.initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        if (Math.abs(Timer.getFPGATimestamp() - this.initialTime) > this.duration) {
            this.finished = true;
        } else {
            this.claw.intake();
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.claw.setMotorSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
