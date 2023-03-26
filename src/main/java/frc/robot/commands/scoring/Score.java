package frc.robot.commands.scoring;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.elevator.PidExtend;
import frc.robot.commands.elevator.PidRotate;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class Score {
    public static Command scoreLow(Arm arm, Elevator elevator) {
        return new PidRotate(arm, 0);//.andThen(new PidExtend(elevator, 10));
    }

    public static Command scoreMidCube(Arm arm, Elevator elevator) {
        return new PidRotate(arm, 1.5);//.andThen(new PidExtend(elevator, 5));
    }

    public static Command scoreMidCone(Arm arm, Elevator elevator) {
        return new PidRotate(arm, 1.7);//.andThen(new PidExtend(elevator, 10));
    }
}