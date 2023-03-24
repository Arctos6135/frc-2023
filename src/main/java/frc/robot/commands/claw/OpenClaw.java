package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.subsystems.Claw;
import frc.robot.constants.ClawConstants;

public class OpenClaw extends CommandBase {
    private final Claw claw;
    private final double time;
    private double initialTime;
    
    public static final double CLAW_SPEED = 0.8;

    /**
     * @param time the time the claw should open for in seconds
     */
    public OpenClaw(Claw claw, double time) {
        this.claw = claw;
        this.time = time;
        addRequirements(this.claw);
    }

    public OpenClaw(Claw claw) {
        this.claw = claw;
        this.time = ClawConstants.OPEN_TIME;
        addRequirements(this.claw);
    }

    @Override
    public void initialize() {
        claw.setSpeed(CLAW_SPEED);
        DriverStation.reportWarning("Claw Started", false);
        this.initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        if (!(Math.abs(Timer.getFPGATimestamp() - initialTime) >= time)) {
            claw.setSpeed(CLAW_SPEED);
        }
    }

    @Override
    public void end(boolean interrupted){
        claw.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(Timer.getFPGATimestamp() - initialTime) >= time;
    }

    public double getTime() {
        return time;
    }
}
