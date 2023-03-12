package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.subsystems.Claw;
import frc.robot.constants.ClawConstants;

public class CloseClaw extends CommandBase {
    private final Claw claw;
    private final double time;
    private double initialTime;

    public static final double CLAW_SPEED = -0.8; 

    /**
     * @param time the time the claw should close for in seconds
     */
    public CloseClaw(Claw claw, double time) {
        this.claw = claw;
        this.time = time;
        addRequirements(this.claw);

        // In the subsystem, we assume the claw is open. When initializing the claw, we should make sure it is closed.
    }

    public CloseClaw(Claw claw) {
        this.claw = claw;
        this.time = ClawConstants.CLOSE_TIME;
        addRequirements(this.claw);

        // In the subsystem, we assume the claw is open. When initializing the claw, we should make sure it is closed.
    }

    @Override
    public void initialize() {
        claw.startClosing();

        initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        if (!(Math.abs(Timer.getFPGATimestamp() - initialTime) >= time)) {
            claw.setSpeed(CLAW_SPEED);
        }
    }

    @Override
    public void end(boolean interrupted){
        claw.stopClosing();
    }

    @Override
    public boolean isFinished() {
        return (Timer.getFPGATimestamp() - initialTime) >= time;
    }

    public double getTime() {
        return time;
    }
}
