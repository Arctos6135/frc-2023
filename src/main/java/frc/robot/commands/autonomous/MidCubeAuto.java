package frc.robot.commands.autonomous;

import frc.robot.commands.intake.RawOuttake;
import frc.robot.commands.scoring.Score;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.WheelClaw;

public class MidCubeAuto {
    public static Command midCubeAuto(Drivetrain drivetrain, Arm arm, Elevator elevator, WheelClaw claw) {
        return Score.scoreMidCube(arm).andThen(new RawOuttake(claw).withTimeout(2)).andThen(MobilityAuto.mobility(drivetrain));
    }
}