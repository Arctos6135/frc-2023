package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.subsystems.Claw;

public class OpenClaw extends CommandBase {
    private final Claw claw;
    private final double time;
    private double initialTime;

    public OpenClaw(Claw claw, double time) {
        this.claw = claw;
        this.time = time;
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
        initialTime = -1.0;
    }

    @Override
    public boolean isFinished() {
        return (Timer.getFPGATimestamp() - initialTime) >= time;
    }

    public double getTime() {
        return time;
    }
}
