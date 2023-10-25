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

public class Intake {
    public static Command intakeGround(Arm arm, WheelClaw claw) {
        return new PidRotate(arm, 0.9).andThen(new RawIntake(claw));
    }

    public static Command intakeSubstation(Arm arm, WheelClaw claw) {
        return new PidRotate(arm, 1.87f).andThen(new RawIntake(claw));
    }
}