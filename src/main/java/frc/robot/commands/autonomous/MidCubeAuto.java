package frc.robot.commands.autonomous;

import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.commands.intake.RawIntake;
import frc.robot.commands.intake.RawOuttake;
import frc.robot.commands.scoring.Score;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.elevator.PidExtend;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.WheelClaw;

public class MidCubeAuto {
    public static Command midCubeAuto(Drivetrain drivetrain, Arm arm, Elevator elevator, WheelClaw claw) {
        return Score.scoreMidCube(arm, elevator).andThen(new RawOuttake(claw).withTimeout(5)).andThen(MobilityAuto.mobility(drivetrain));
    }
}