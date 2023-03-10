package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.subsystems.Claw;
import frc.robot.constants.ClawConstants;

public class OpenClaw extends CommandBase {
    private final Claw claw;
    private final double time;
    private double initialTime;

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
        claw.startOpening();

        initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted){
        claw.stopOpening();
    }

    @Override
    public boolean isFinished() {
        return (Timer.getFPGATimestamp() - initialTime) >= time;
    }

    public double getTime() {
        return time;
    }
}