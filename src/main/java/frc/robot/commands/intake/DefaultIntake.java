package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class DefaultIntake extends CommandBase {
    private final Intake intake; 
    private final XboxController driverController; 

    private int intakeButton; 
    private int outtakeButton; 

    public static double intakeSpeed = 0.5; 

    public DefaultIntake(Intake intake, XboxController driverController, 
        int intakeButton, int outtakeButton) {

        this.intake = intake; 
        this.driverController = driverController; 
        this.intakeButton = intakeButton; 
        this.outtakeButton = outtakeButton;

        addRequirements(intake);
    }

    @Override
    public void initialize() {

    }


    @Override 
    public void execute() {
        if (driverController.getRawButton(intakeButton)) {
            this.intake.setIntakeMotor(intakeSpeed);
        }

        else if (driverController.getRawButton(outtakeButton)) {
            this.intake.setIntakeMotor(-intakeSpeed);
        }

        else {
            this.intake.setIntakeMotor(0);
        }
    }

    @Override 
    public boolean isFinished() {
        return false; 
    } 

    @Override 
    public void end(boolean isFinished) {
        this.intake.setIntakeMotor(0);
    }
}
