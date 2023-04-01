package frc.robot.commands.claw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.Controllers;
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
        if (controller.getRawButton(Controllers.BUMPER_INTAKE) == true) {
            this.claw.gather();
        } else if (controller.getRawButton(Controllers.BUMPER_OUTTAKE) == true) {
            this.claw.release();
        } else {
            this.claw.stop();
        }
    }

    public void end(boolean interrupted) {
        this.claw.stop();
    }
}