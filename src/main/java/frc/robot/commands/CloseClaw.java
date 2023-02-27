package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.subsystems.Claw;

public class CloseClaw extends CommandBase {
    private final Claw claw;
    private final double time;
    private double initialTime;

    public CloseClaw(Claw claw, double time) {
        this.claw = claw;
        this.time = time;
        addRequirements(this.claw);

        // In the subsystem, we assume the claw is closed. When initializing the claw, we should make sure it is closed.
    }

    @Override
    public void initialize() {
        claw.startClosing();

        initialTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted){
        claw.stopClosing();
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
