package frc.robot.commands.intake;

import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.commands.intake.RawIntake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.elevator.PidExtend;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.WheelClaw;


public class RawOuttake extends CommandBase {
    private final WheelClaw intake; 

    public RawOuttake(WheelClaw intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override 
    public void execute() {
        intake.outtake();
    }

    @Override 
    public void end(boolean isFinished) {
        this.intake.setMotorSpeed(0);
    }
}
