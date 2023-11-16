package frc.robot.commands.autonomous;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.driving.DriveForwardEncoded;
import frc.robot.commands.driving.DriveTowardsCube;
import frc.robot.commands.driving.TurnEncoded;
import frc.robot.commands.scoring.Score;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.VisionSystem;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.WheelClaw;

public class BenAwesomeBestAuto extends CommandBase{
    public static Command superCubes(Drivetrain drivetrain, Arm arm, Elevator elevator, WheelClaw claw, VisionSystem visionSystem){
        return new DriveForwardEncoded(drivetrain, 0.8, -24)
        .andThen(new DriveForwardEncoded(drivetrain, 0.1, 100))
        .andThen(new DriveTowardsCube(drivetrain, visionSystem, 2.5))
        .andThen(new TurnEncoded(drivetrain, Math.PI, 0.8))
        .andThen(new DriveForwardEncoded(drivetrain, 100, 0.8))
        .andThen(Score.scoreMidCube(arm));
    }
}
