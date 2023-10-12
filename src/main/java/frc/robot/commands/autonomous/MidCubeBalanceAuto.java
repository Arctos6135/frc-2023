package frc.robot.commands.autonomous;

import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.commands.intake.RawIntake;
import frc.robot.commands.intake.RawOuttake;
import frc.robot.commands.scoring.Score;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.driving.DriveForwardTillAngle;
import frc.robot.commands.driving.GyroCalibrate;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.Wait;
import frc.robot.commands.gyroBalance.PIDBalance;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.WheelClaw;

public class MidCubeBalanceAuto {
    public static Command midCubeBalanceAuto(Drivetrain drivetrain, Arm arm, Elevator elevator, WheelClaw claw, ShuffleboardTab drivetrainTab) {
        return new SequentialCommandGroup(
            /* mid cube
            Score.scoreMidCube(arm, elevator), 
            new RawOuttake(claw).withTimeout(0.2),
            new DriveForwardTillAngle(drivetrain, -0.2, 0.3),
             */
            /* momentum score + mobility
            new DriveForwardEncoded(drivetrain, 1, -24),
            new DriveForwardEncoded(drivetrain, 0.2, 12 * 10)
             */
            /*
            new GyroCalibrate(drivetrain),
            new Wait(2),
            new DriveForwardEncoded(drivetrain, 1, -24),
            new DriveForwardTillAngle(drivetrain, 0.18, 0.3),
            new PIDBalance(drivetrain, drivetrainTab)
            */
            /*
            new DriveForwardEncoded(drivetrain, 1, -24),
            new DriveForwardEncoded(drivetrain, 0.5, 12 * 10)
            */
            new DriveForwardEncoded(drivetrain, 0.75, -24)
        ).withTimeout(14);
    }
}