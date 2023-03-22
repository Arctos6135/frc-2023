package frc.robot.commands.wheelclaw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WheelClaw;

public class TeleopIntake extends CommandBase {
    private final WheelClaw wheelClaw; 

    public TeleopIntake(WheelClaw wheelClaw) {
        this.wheelClaw = wheelClaw; 
    }
}
