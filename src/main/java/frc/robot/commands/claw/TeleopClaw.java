package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ClawConstants;
import frc.robot.subsystems.Claw;

public class TeleopClaw extends CommandBase {
    private final Claw claw;
    private final XboxController controller;

    public TeleopClaw(Claw claw, XboxController operatorController) {
        this.claw = claw;
        this.controller = operatorController;

        addRequirements(claw);
    }

    public void execute() {
        // I dont know if .getRawButton() does what I think it does...
        if (controller.getRawButton(ClawConstants.GATHER_CLAW_BUTTON) == true) {
            this.claw.gather();
        } else if (controller.getRawButton(ClawConstants.RELEASE_CLAW_BUTTON) == true) {
            this.claw.release();
        } else {
            this.claw.stop();
        }
    };
}