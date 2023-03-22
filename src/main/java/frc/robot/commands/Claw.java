package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ClawConstants;
import frc.robot.subsystems.Claw;

public class Claw extends CommandBase {
    private final Claw claw;
    private final XboxController controller;

    public Claw(Claw claw, XboxController operatorController) {
        this.claw = claw;
        this.controller = operatorController;

        addRequirements(claw);
    }

    public void execute() {
        double openClawPressed 
    };
}