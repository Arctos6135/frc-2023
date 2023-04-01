package frc.robot.commands.claw;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;

public class RawRelease extends CommandBase {
    private final Claw claw;
    private double stopTime;

    public RawRelease(Claw claw) {
        this.claw = claw;

        addRequirements(claw);
    }

    @Override
    public void initialize() {
        stopTime = Timer.getFPGATimestamp() + 0.2;
    }

    @Override
    public void execute() {
        claw.release();
    }

    @Override
    public void end(boolean interrupted) {
        claw.stop();
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() >= stopTime;
    }
}