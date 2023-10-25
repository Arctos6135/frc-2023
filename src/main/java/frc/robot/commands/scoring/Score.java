package frc.robot.commands.scoring;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.elevator.EncodedElevator;
import frc.robot.commands.elevator.PidExtend;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.commands.elevator.TimedElevator;
import frc.robot.commands.elevator.Wait;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.WheelClaw;

public class Score {
    public static Command scoreLowCube(Arm arm) {
        return new PidRotate(arm, 0.9);//.andThen(new PidExtend(elevator, 10));
    }

    public static Command scoreMidCube(Arm arm) {
        return new SequentialCommandGroup(
            new PidRotate(arm, 1.5)
        );
    }

    public static Command scoreMidCone(Arm arm, Elevator elevator) {
        return new PidRotate(arm, 0.7);//.andThen(new PidExtend(elevator, 10));
    }

    public static Command intakeDoubleSubstation(Arm arm, WheelClaw claw) {
        return new PidRotate(arm, 0.7);// .andThen(new )
    }
}