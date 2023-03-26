package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelClaw;

public class RawIntake extends CommandBase {
    private final WheelClaw intake; 

    public RawIntake(WheelClaw intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override 
    public void execute() {
        intake.intake();
    }

    @Override 
    public void end(boolean isFinished) {
        this.intake.setMotorSpeed(0);
    }
}
